package org.ebu6304gp42.Controller.Shopping.cart;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Data.Order;
import org.ebu6304gp42.Data.OrderedDish;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    private ObservableList<OrderedDish> orderedDishes = FXCollections.observableArrayList();
    private double order_price = 0;
    @FXML
    private VBox orderedDishPane;
    @FXML
    private Label price;
    @FXML
    private ScrollPane scroll;

    @FXML
    private void submit(MouseEvent event){
        Order order = new Order();
        orderedDishes.iterator().forEachRemaining(order::addDish);
    }

    @FXML
    private void clear(MouseEvent event){
        orderedDishes.clear();
        orderedDishPane.getChildren().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderedDishes.addListener(this::onChanged);
    }

    private void addWidget(OrderedDish orderedDish){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/OrderedDishWidget.fxml"));
        try {
            loader.load();
            ((OrderedDishController)loader.getController()).setOrderedDish(orderedDish);
            orderedDishPane.getChildren().add(loader.getRoot());
            updatePrice(orderedDish.getTotalPrice());
        } catch (IOException e) {
            e.printStackTrace();
        }
        scroll.setVvalue(1.0);
    }

    public ObservableList<OrderedDish> getModel(){
        return orderedDishes;
    }

    private void onChanged(ListChangeListener.Change<? extends OrderedDish> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                c.getAddedSubList().iterator().forEachRemaining(this::addWidget);
            }
        }
    }

    private void updatePrice(double diff){
        order_price += diff;
        updatePrice();
    }

    private void updatePrice(){
        price.setText(String.format("%.2f", order_price));
    }
}
