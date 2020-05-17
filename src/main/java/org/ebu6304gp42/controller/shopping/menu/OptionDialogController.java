package org.ebu6304gp42.controller.shopping.menu;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.ebu6304gp42.data.Dish;
import org.ebu6304gp42.data.DishOption;
import org.ebu6304gp42.data.OrderedDish;

import java.util.ArrayList;
import java.util.LinkedList;

public class OptionDialogController{
    private Dish dish;
    @FXML
    private ImageView image;
    @FXML
    private Label dish_name;
    @FXML
    private ScrollPane opt_area;
    @FXML
    private TextArea note;

    @FXML
    private Spinner<Integer> amount;

    private OptionWidget optionWidget;

    public void setDish(Dish dish){
        this.dish = dish;
        dish.setImageTo(image);
        dish_name.setText(dish.getName());
        optionWidget = new OptionWidget(dish);
        opt_area.setContent(optionWidget);
    }

    public OrderedDish getOrderedDish(){
        OrderedDish orderedDish = new OrderedDish(dish);
        orderedDish.setAmount(amount.getValue());
        orderedDish.setOptions(optionWidget.getOptions());
        orderedDish.setNote(note.getText());
        return orderedDish;
    }

    static class OptionWidget extends GridPane {
        private final LinkedList<OptionGroup> optionGroups = new LinkedList<>();

        public OptionWidget(Dish dish){
            setHgap(14);
            setVgap(18);
            for(var option:dish.getOptions()){
                OptionGroup group = new OptionGroup(option);
                addRow(getRowCount(), group.getLabel(),group.getOptions());
                optionGroups.add(group);
            }
        }

        public ArrayList<OrderedDish.SelectedOption> getOptions(){
            ArrayList<OrderedDish.SelectedOption> options = new ArrayList<>();
            for(var group:optionGroups){
                options.add(group.getSelectedOption());
            }
            return options;
        }

        static class OptionGroup{
            private final Label name;
            private final ToggleGroup group;
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
}
