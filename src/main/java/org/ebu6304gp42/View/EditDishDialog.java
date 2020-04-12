package org.ebu6304gp42.View;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Data.DishOption;
import org.ebu6304gp42.Ui.Spacer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class EditDishDialog extends Dialog<Dish> {
    public EditDishDialog(Dish dish){
        setTitle("Add/Modify the dish");
        setHeaderText(null);

        if(dish == null){
            dish = new Dish();
        }

        HBox root = new HBox();
        //Left Area

        //Dish Image
        Image image;
        try {
            if(dish.getPic() == null || dish.getPic().isBlank()){
                throw new FileNotFoundException();
            }
            image = new Image(new FileInputStream(dish.getPic()));
        } catch (IOException e){
            //if no pic found or other IOException, use default pic
            System.err.println(String.format("No picture found for %s(pic file:%s) , use default image.", dish.getName(), dish.getPic()));
            image = new Image(getClass().getResourceAsStream(PathConfig.getDefaultPic()));
        }
        ImageView dish_image = new ImageView(image);
        dish_image.setFitWidth(180);
        dish_image.setFitHeight(180);
        dish_image.preserveRatioProperty().setValue(true);
        // Basic Information
        TextField nameInput = new TextField(dish.getName());
        TextField priceInput = new TextField(String.valueOf(dish.getPrice()));
        TextField remainInput = new TextField(String.valueOf(dish.getRemain()));
        ToggleGroup statusGroup = new ToggleGroup();
        ToggleButton status_enable = new ToggleButton("enable");
        status_enable.setUserData(true);
        status_enable.setToggleGroup(statusGroup);
        ToggleButton status_disable = new ToggleButton("disable");
        status_disable.setUserData(false);
        status_disable.setToggleGroup(statusGroup);
        HBox statusInput = new HBox();
        if(dish.getStatus()){
            status_enable.setSelected(true);
        } else {
            status_disable.setSelected(true);
        }
        statusInput.getChildren().addAll(status_enable,status_disable);
        // Basic Information Layout
        GridPane basic_info = new GridPane();
        basic_info.setVgap(6);
        basic_info.setHgap(8);
        basic_info.addRow(0, new Label("Name:"), nameInput);
        basic_info.addRow(1, new Label("Price:"), priceInput);
        basic_info.addRow(2, new Label("Remain:"), remainInput);
        basic_info.addRow(3, new Label("Status:"), statusInput);

        VBox leftArea = new VBox();
        HBox image_area = new HBox();
        //Keep Image center of left area
        image_area.setAlignment(Pos.CENTER);
        image_area.getChildren().add(dish_image);
        leftArea.setSpacing(32);
        leftArea.setPadding(new Insets(6));
        leftArea.getChildren().addAll(image_area, basic_info);

        //Right Area
        //Description
        Label desc_label = new Label("Description:");
        desc_label.setFont(Font.font(null, FontWeight.BOLD, 14));
        TextArea description = new TextArea(dish.getDescription());
        description.setPrefHeight(200);
        //Options
        OptionArea optionArea = new OptionArea(dish.getOptions());
        VBox.setVgrow(optionArea, Priority.ALWAYS);

        HBox btns = new HBox();
        Button addBtn = new Button("Add");
        addBtn.setOnMouseClicked(event -> {optionArea.add();});
        Button removeBtn = new Button("Remove");
        removeBtn.setOnMouseClicked(event -> {optionArea.remove();});
        optionArea.setPrefSize(500, 300);
        btns.setSpacing(8);
        btns.getChildren().addAll(addBtn, removeBtn);

        VBox rightArea = new VBox();
        rightArea.prefWidthProperty().setValue(400);
        rightArea.getChildren().addAll(desc_label ,description, optionArea, btns);

        root.setSpacing(18);
        root.getChildren().addAll(leftArea, Spacer.HSpacer(), rightArea);
        getDialogPane().setContent(root);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(btn->{
            if(btn == ButtonType.CANCEL){
                return null;
            }
            Dish finalDish = new Dish();
            finalDish.setName(nameInput.getText());
            finalDish.setPrice(Double.parseDouble(priceInput.getText()));
            finalDish.setRemain(Integer.parseInt(remainInput.getText()));
            finalDish.setStatus((Boolean) statusGroup.getSelectedToggle().getUserData());
            finalDish.setDescription(description.getText());
            finalDish.resetOptions(optionArea.getOptions());
            System.out.println(optionArea.getOptions().get(0).getName());
            return finalDish;
        });
    }

    static class OptionArea extends ScrollPane{
        VBox root = new VBox();
        OptionWidget selected = null;
        public OptionArea(){
            root.setSpacing(8);
            setContent(root);
        }

        public OptionArea(ArrayList<DishOption> options){
            setContent(root);
            root.setSpacing(6);
            for(var opt:options){
                var optionWidget = new OptionWidget(opt);
                root.getChildren().addAll(optionWidget);
                optionWidget.setOnMouseClicked(event -> {
                    if(event.getClickCount() == 2){
                        var res = (new OptionEditDialog(optionWidget.getDishOption())).showAndWait();
                        res.ifPresent(optionWidget::update);
                    } else {
                        selected = optionWidget;
                    }
                });
            }
        }

        public ArrayList<DishOption> getOptions(){
            ArrayList<DishOption> list = new ArrayList<>();
            for(var node:root.getChildren()){
                list.add(((OptionWidget)node).getDishOption());
            }
            return list;
        }

        public void add(){
            var res = (new OptionEditDialog()).showAndWait();
            res.ifPresent(dishOption -> {
                root.getChildren().add(new OptionWidget(dishOption));
            });
        }

        public void remove(){
            if(selected == null) return;
            root.getChildren().remove(selected);
            selected = null;
        }

        static class OptionWidget extends HBox{
            private DishOption dishOption;

            public OptionWidget(DishOption dishOption){
                setSpacing(6);
                this.dishOption = dishOption;
                update(dishOption);
            }

            public void update(DishOption dishOption){
                this.dishOption = dishOption;
                this.getChildren().clear();
                Label name = new Label(dishOption.getName() + ":");
                name.setFont(Font.font(null, FontWeight.BOLD, 14));

                this.getChildren().add(name);
                for(var option:dishOption.getOptions()){
                    Label opt = new Label(
                            option.option + ((option.price>0)?String.format("(%.2f)", option.price):"")
                    );
                    opt.setFont(Font.font(14));
                    this.getChildren().add(opt);
                }
            }

            public DishOption getDishOption() {
                return dishOption;
            }
        }

        static class OptionEditDialog extends Dialog<DishOption>{
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

            static class OptionEditWidget extends HBox{
                private TextField opt_name = new TextField();
                private TextField price = new TextField();

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
                    getChildren().addAll(new Label("Option: "), opt_name, new Label("Price: "), price);
                    //Delete
                    Button del_btn = new Button("Del");
                    getChildren().add(del_btn);
                    del_btn.setOnMouseClicked(event -> {
                        fireEvent(new DeleteRequestEvent(DeleteRequestEvent.DELETE_REQUEST_EVENT, this));
                    });

                }

                public String getName(){return opt_name.getText();}
                public double getPrice(){return Double.parseDouble(price.getText());}

                public void setOnDeletedRequest(EventHandler<DeleteRequestEvent> handler){
                    this.addEventHandler(OptionEditWidget.DeleteRequestEvent.DELETE_REQUEST_EVENT, handler);
                }

                static class DeleteRequestEvent extends Event{
                    public static final EventType<DeleteRequestEvent> DELETE_REQUEST_EVENT = new EventType<>("Delete Request");
                    private OptionEditWidget source;

                    public DeleteRequestEvent(EventType<? extends Event> eventType, OptionEditWidget source) {
                        super(eventType);
                        this.source = source;
                    }

                    public OptionEditWidget getSource(){return source;}
                }
            }
        }
    }
}

