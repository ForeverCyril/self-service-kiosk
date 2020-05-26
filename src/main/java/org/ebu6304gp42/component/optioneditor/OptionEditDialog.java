package org.ebu6304gp42.component.optioneditor;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.data.DishOption;
import org.ebu6304gp42.view.ShowAlert;

/**
 * A dialog to edit dish option, it will return a {@link DishOption}
 */
class OptionEditDialog extends Dialog<DishOption> {
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
            OptionEditWidget opt_widget = new OptionEditWidget(opt);
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
                if(option.getName() == null || option.getName().isBlank()){
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
                    dishOption.addOption(((OptionEditWidget)opt).getName(), ((OptionEditWidget)opt).getPrice());
                }
                return dishOption;
            }
        });
    }

    private void addOption(){
        OptionEditWidget opt_widget = new OptionEditWidget();
        opt_widget.setOnDeletedRequest(event -> {
            opt_area.getChildren().remove(event.getSource());
        });
        opt_area.getChildren().add(opt_widget);
        opt_scroll_pane.setVvalue(1);
    }

}
