package org.ebu6304gp42.controller.payment;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class PaymentController {
    @FXML
    private Label priceTag;
    @FXML
    private Node payment;
    @FXML
    private Node result;
    @FXML
    private StackPane root;

    private final BooleanProperty paidProperty = new SimpleBooleanProperty(false);

    @FXML
    private void pay(MouseEvent event){
        payment.setVisible(false);
        result.setVisible(true);
        result.toFront();
        paidProperty.setValue(true);
    }

    public void setPrice(double price){
        priceTag.setText(String.format("You need pay %.2f £", price));
    }

    public BooleanProperty getPaidProperty(){
        return paidProperty;
    }
}
