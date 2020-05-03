package org.ebu6304gp42.Component;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Component.InputField.CurrencyField;
import org.ebu6304gp42.Component.Selectable.OptionEditItem;
import org.ebu6304gp42.Component.Selectable.SelectableItem;
import org.ebu6304gp42.Component.SimpleToggle.SimpleToggle;
import org.ebu6304gp42.Component.SimpleToggle.SimpleToggleGroup;
import org.ebu6304gp42.Data.DishOption;
import org.ebu6304gp42.View.ShowAlert;

import java.util.ArrayList;

public class OptionEditor extends ScrollPane {
    VBox root = new VBox();
    SimpleToggleGroup group = new SimpleToggleGroup();

    public OptionEditor(ArrayList<DishOption> options){
        setContent(root);
        VBox.setVgrow(this, Priority.ALWAYS);
        root.setSpacing(6);
        for(var opt:options){
            var item = new OptionEditItem(opt);
            item.setToggleGroup(group);
            root.getChildren().add(item);
            item.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    var res = (new OptionEditDialog(item.getDishOption())).showAndWait();
                    res.ifPresent(item::update);
                }
            });
        }
        for(var item:root.getChildren()){
            System.out.println(item);
        }
    }

    public ArrayList<DishOption> getOptions(){
        ArrayList<DishOption> list = new ArrayList<>();
        for(var node:root.getChildren()){
            DishOption option = ((OptionEditItem)node).getDishOption();
            list.add(option);
        }
        return list;
    }

    public void add(){
        var res = (new OptionEditDialog()).showAndWait();
        res.ifPresent(dishOption -> {
            OptionEditItem item = new OptionEditItem(dishOption);
            item.setToggleGroup(group);
            root.getChildren().add(item);
        });
    }

    public void remove(){
        SimpleToggle selected_item = group.getSelectedToggle();
        if(selected_item!=null){
            root.getChildren().remove((SelectableItem)selected_item);
        }
    }

    static class OptionEditDialog extends Dialog<DishOption> {
        TextField nameInput =new TextField();
        VBox opt_area = new VBox();
        ScrollPane opt_scroll_pane = new ScrollPane();

        public OptionEditDialog(){
            initUI();
        }

        public OptionEditDialog(DishOption dishOption){
            initUI();
            nameInput.setText(dishOption.getName());
            for (var opt:dishOption.getOptions()){
                OptionEditDialog.OptionEditWidget opt_widget = new OptionEditDialog.OptionEditWidget(opt);
                opt_widget.setOnDeletedRequest(event -> {
                    opt_area.getChildren().remove(event.getSource());
                });
                opt_area.getChildren().add(opt_widget);
            }
        }

        private void initUI(){
            setTitle("Edit Dish Option");
            setGraphic(null);
            setHeaderText(null);
            getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
            HBox name = new HBox();
            HBox.setHgrow(nameInput, Priority.ALWAYS);
            name.getChildren().addAll(new Label("Name: "), nameInput);
            VBox root = new VBox();
            Button addBtn = new Button("Add");
            addBtn.setOnMouseClicked(event -> {addOption();});
            VBox.setVgrow(opt_scroll_pane, Priority.ALWAYS);
            opt_area.setSpacing(8);
            opt_area.setPadding(new Insets(6));
            opt_scroll_pane.setContent(opt_area);
            opt_scroll_pane.setMinSize(480, 200);
            root.getChildren().addAll(name, opt_scroll_pane, addBtn);
            getDialogPane().setContent(root);
            getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION, event->{
                if (nameInput.getText() == null || nameInput.getText().isBlank()){
                    ShowAlert.error("Input Invalid", "Option Name Can Not Be Empty");
                    event.consume();
                    return;
                }
                for(var opt:opt_area.getChildren()){
                    OptionEditWidget option = (OptionEditWidget)opt;
                    if(option.opt_name.getText() == null || option.opt_name.getText().isBlank()){
                        ShowAlert.error("Input Invalid", "Option Name Can Not Be Empty");
                        event.consume();
                        return;
                    }
                }
            });
            setResultConverter(btn->{
                if(btn == ButtonType.CANCEL){
                    return null;
                } else {
                    DishOption dishOption = new DishOption(nameInput.getText());
                    for(var opt:opt_area.getChildren()){
                        dishOption.addOption(((OptionEditDialog.OptionEditWidget)opt).getName(), ((OptionEditDialog.OptionEditWidget)opt).getPrice());
                    }
                    return dishOption;
                }
            });
        }

        private void addOption(){
            OptionEditDialog.OptionEditWidget opt_widget = new OptionEditDialog.OptionEditWidget();
            opt_widget.setOnDeletedRequest(event -> {
                opt_area.getChildren().remove(event.getSource());
            });
            opt_area.getChildren().add(opt_widget);
            opt_scroll_pane.setVvalue(1);
        }

        static class OptionEditWidget extends HBox{
            private TextField opt_name = new TextField();
            private CurrencyField price = new CurrencyField();

            public OptionEditWidget(){
                intiUi();
            }

            public OptionEditWidget(DishOption.Option opt){
                intiUi();
                opt_name.setText(opt.option);
                price.setText(String.valueOf(opt.price));
            }

            private void intiUi(){
                //Input
                price.setText("0.00");
                getChildren().addAll(new Label("Option: "), opt_name, new Label("Price: "), price);
                //Delete
                Button del_btn = new Button("Del");
                getChildren().add(del_btn);
                del_btn.setOnMouseClicked(event -> {
                    fireEvent(new OptionEditDialog.OptionEditWidget.DeleteRequestEvent(OptionEditDialog.OptionEditWidget.DeleteRequestEvent.DELETE_REQUEST_EVENT, this));
                });

            }

            public String getName(){return opt_name.getText();}
            public double getPrice(){return price.getDoubleValue();}

            public void setOnDeletedRequest(EventHandler<OptionEditDialog.OptionEditWidget.DeleteRequestEvent> handler){
                this.addEventHandler(OptionEditDialog.OptionEditWidget.DeleteRequestEvent.DELETE_REQUEST_EVENT, handler);
            }

            static class DeleteRequestEvent extends Event {
                public static final EventType<OptionEditDialog.OptionEditWidget.DeleteRequestEvent> DELETE_REQUEST_EVENT = new EventType<>("Delete Request");
                private OptionEditDialog.OptionEditWidget source;

                public DeleteRequestEvent(EventType<? extends Event> eventType, OptionEditDialog.OptionEditWidget source) {
                    super(eventType);
                    this.source = source;
                }

                public OptionEditDialog.OptionEditWidget getSource(){return source;}
            }
        }
    }
}
