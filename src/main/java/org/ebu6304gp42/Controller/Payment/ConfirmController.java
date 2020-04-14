package org.ebu6304gp42.Controller.Payment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Controller.Shopping.cart.OrderedDishController;
import org.ebu6304gp42.Data.Order;
import org.ebu6304gp42.Data.OrderedDish;

import java.io.IOException;

public class ConfirmController {
    @FXML
    private VBox order_area;
    @FXML
    private StackPane acc_area;
    @FXML
    private Label price;
    @FXML
    private TextField userID;
    @FXML
    private ToggleButton useStamp;
    @FXML
    private Label user_name;
    @FXML
    private Label stamp_amount;
    @FXML
    private Node acc_not_login;
    @FXML
    private Node acc_login;

    @FXML
    private void onLogin(MouseEvent event){
        acc_not_login.setVisible(false);
        acc_login.toFront();
        acc_login.setVisible(true);
    }
    @FXML
    private void onLogout(MouseEvent event){
        acc_login.setVisible(false);
        acc_not_login.toFront();
        acc_not_login.setVisible(true);
    }


    public void setOrder(Order order){
        order.getDish().iterator().forEachRemaining(this::addOrderedDish);
        price.setText(String.format("%.2f", order.getPrice()));
    }

    private void addOrderedDish(OrderedDish dish){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/OrderedDishWidget.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        ((OrderedDishController)loader.getController()).setOrderedDish(dish);
        order_area.getChildren().add(loader.getRoot());
    }
}
