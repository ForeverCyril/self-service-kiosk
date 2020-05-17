package org.ebu6304gp42.controller.managing.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.ebu6304gp42.data.Order;
import org.ebu6304gp42.data.OrderManager;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderTableController implements Initializable {
    private ObservableList<Order> data;

    @FXML
    private TableView<Order> OrderTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList(OrderManager.getInstance().getOrders());
        OrderTable.setItems(data);
    }
}
