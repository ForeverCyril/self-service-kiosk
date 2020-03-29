package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Data.DishOption;
import org.ebu6304gp42.Data.OrderedDish;
import org.ebu6304gp42.Data.SelectedOption;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OptionDialog extends Dialog<OrderedDish> {
    public OptionDialog(Dish dish){
        setTitle("Totoro Ramen");
        setHeaderText(null);
        ImageView dish_image = new ImageView(new Image(getClass().getResourceAsStream(PathConfig.DEFAULT_PIC)));

        VBox graphic = new VBox();
        graphic.setPrefWidth(60);
        graphic.setPadding(new Insets(0,16,0,0));
        dish_image.fitWidthProperty().bind(graphic.widthProperty().subtract(16));
        dish_image.preserveRatioProperty().setValue(true);
        Label name = new Label(dish.getName());
        name.setFont(Font.font(null, FontWeight.BOLD, 16));
        name.prefWidthProperty().bind(graphic.widthProperty().subtract(16));
        name.setAlignment(Pos.CENTER);
        graphic.getChildren().addAll(dish_image, name);
        setGraphic(graphic);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        OptionWidget widget = new OptionWidget(dish);
        getDialogPane().setContent(widget);
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
        setPrefWidth(400);
    }

    public ArrayList<SelectedOption> getOptions(){
        ArrayList<SelectedOption> options = new ArrayList<>();
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
                RadioButton button = new RadioButton(option_content);
                button.setFont(Font.font(16));
                button.setToggleGroup(group);
                if(first){
                    button.setSelected(true);
                    first=false;
                }
                options_box.getChildren().add(button);
            }
        }

        public SelectedOption getSelectedOption(){
            return new SelectedOption(
                    name.getText(),
                    ((RadioButton)group.getSelectedToggle()).getText()
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
