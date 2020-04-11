package org.ebu6304gp42.Controller.Shopping.cart;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.ebu6304gp42.Data.OrderedDish;

public class OrderedDishController {
    private OrderedDish orderedDish;

    @FXML
    private Node root;
    @FXML
    private Label name_label;
    @FXML
    private Label price_label;
    @FXML
    private Label num_label;
    @FXML
    private Label opt_label;

    public OrderedDish getOrderedDish() {
        return orderedDish;
    }


    public void setOrderedDish(OrderedDish orderedDish) {
        this.orderedDish = orderedDish;
        name_label.setText(String.valueOf(orderedDish.getName()));
        price_label.setText(String.valueOf(orderedDish.getPrice()));
        num_label.setText(String.valueOf(orderedDish.getAmount()));
        StringBuilder opt_data = new StringBuilder();
        for(var option: orderedDish.getOptions()){
            opt_data.append(option.toString());
        }
        opt_label.setText(opt_data.toString());
    }
}
