package org.ebu6304gp42.controller.shopping.cart;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.ebu6304gp42.data.OrderedDish;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderedDishController implements Initializable {
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
        price_label.setText(String.format("%.2f",orderedDish.getPrice()));
        num_label.setText(String.valueOf(orderedDish.getAmount()));
        StringBuilder opt_data = new StringBuilder();
        for(var option: orderedDish.getOptions()){
            opt_data.append(option.toString());
        }
        opt_label.setText(opt_data.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu menu = new ContextMenu();
        MenuItem del_item = new MenuItem("Delete");
        del_item.setOnAction(event -> {
            root.fireEvent(new OrderedDishDeleteEvent(OrderedDishDeleteEvent.DISH_DELETE_EVENT, orderedDish));
        });

        menu.getItems().addAll(del_item);
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            // Show menu when right click or click + ctrl
            if(event.getButton() == MouseButton.SECONDARY || event.isControlDown()){
                menu.show(root, event.getScreenX(), event.getScreenY());
            } else {
                menu.hide();
            }
        });
    }
}
