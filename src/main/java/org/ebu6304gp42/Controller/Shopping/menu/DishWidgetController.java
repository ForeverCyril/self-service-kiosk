package org.ebu6304gp42.Controller.Shopping.menu;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javafx.scene.image.ImageView;
import org.ebu6304gp42.Data.Dish;


public class DishWidgetController {
    private Dish dish;

    @FXML
    private Node root;
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label desc;
    @FXML
    private Label recommend;

    @FXML
    private void mouseClicked(MouseEvent event){
        root.fireEvent(new DishClickedEvent(DishClickedEvent.DISH_CLICKED_EVENT, dish));
    }

    public void setDish(Dish dish){
        this.dish = dish;
        name.setText(dish.getName());
        price.setText(String.format("£ %.2f", dish.getPrice()));
        desc.setText(dish.getDescription());
        recommend.setText(dish.getRecomend());
    }
}
