package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Data.DishOption;
import org.ebu6304gp42.Data.OrderedDish;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class OptionDialog extends Dialog<OrderedDish> {
    public OptionDialog(Dish dish){
        setTitle(dish.getName());
        setHeaderText(null);

        VBox graphic = new VBox();
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
        dish_image.setFitWidth(120);
        dish_image.preserveRatioProperty().setValue(true);
        Label name = new Label(dish.getName());
        name.prefWidthProperty().bind(dish_image.fitWidthProperty());
        name.setFont(Font.font(null, FontWeight.BOLD, 16));
        name.setAlignment(Pos.CENTER);
        graphic.getChildren().addAll(dish_image, name);


        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        OptionWidget widget = new OptionWidget(dish);

        HBox hBox = new HBox();
        //hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        hBox.setSpacing(16);
        hBox.getChildren().addAll(graphic, widget);
        HBox.setHgrow(widget, Priority.ALWAYS);
        getDialogPane().setContent(hBox);
        getDialogPane().setPadding(new Insets(6));
        setResultConverter(btn -> {
            if(btn == ButtonType.OK){
                OrderedDish orderedDish = new OrderedDish(dish.getName());
                orderedDish.setOptions(widget.getOptions());
                orderedDish.setAmount(1);
                orderedDish.setPrice(dish.getPrice());
                return orderedDish;
            }
            return null;
        });
    }
}

class OptionWidget extends ScrollPane {
    private LinkedList<OptionGroup> optionGroups = new LinkedList<>();

    public OptionWidget(Dish dish){
        GridPane option_area = new GridPane();
        option_area.setHgap(14);
        option_area.setVgap(18);
        for(var option:dish.getOptions()){
            OptionGroup group = new OptionGroup(option);
            option_area.addRow(option_area.getRowCount(), group.getLabel(),group.getOptions());
            optionGroups.add(group);
        }

        setContent(option_area);
        setPrefHeight(300);
        setPrefWidth(500);
    }

    public ArrayList<OrderedDish.SelectedOption> getOptions(){
        ArrayList<OrderedDish.SelectedOption> options = new ArrayList<>();
        for(var group:optionGroups){
            options.add(group.getSelectedOption());
        }
        return options;
    }

    static class OptionGroup{
        private Label name;
        private ToggleGroup group;
        HBox options_box;

        OptionGroup(DishOption option){
            name = new Label(option.getName());
            name.setFont(Font.font(null, FontWeight.BOLD, 16));
            group = new ToggleGroup();
            options_box = new HBox();
            options_box.setSpacing(8);
            boolean first = true;
            for(var option_content:option.getOptions()){
                RadioButton button = new RadioButton(option_content.toString());
                button.setUserData(option_content.price);
                button.setFont(Font.font(16));
                button.setToggleGroup(group);
                if(first){
                    button.setSelected(true);
                    first=false;
                }
                options_box.getChildren().add(button);
            }
        }

        public OrderedDish.SelectedOption getSelectedOption(){
            var sel = (RadioButton)group.getSelectedToggle();
            return new OrderedDish.SelectedOption(
                    name.getText(),
                    ((RadioButton)group.getSelectedToggle()).getText(),
                    (Double) sel.getUserData()
            );
        }

        public Label getLabel() {
            return name;
        }

        public HBox getOptions() {
            return options_box;
        }
    }
}
