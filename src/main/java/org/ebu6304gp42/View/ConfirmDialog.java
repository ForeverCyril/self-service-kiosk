package org.ebu6304gp42.View;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.ebu6304gp42.Controller.Payment.ConfirmController;
import org.ebu6304gp42.Data.Account;
import org.ebu6304gp42.Data.Order;
import org.ebu6304gp42.Data.OrderManager;

import java.io.IOException;

public class ConfirmDialog extends Dialog<Boolean> {
    private Order order;
    private ConfirmController controller;

    public ConfirmDialog(Order order){
        this.order = order;
        setTitle("Confirm Order");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Confirm.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        controller = (ConfirmController)loader.getController();
        controller.setOrder(order);
        getDialogPane().setContent(loader.getRoot());

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button ok_btn = (Button) getDialogPane().lookupButton(ButtonType.OK);
        ok_btn.addEventFilter(ActionEvent.ACTION, onPayment); // Handle the button action before dialog;
        setResultConverter(btn -> {
            return btn != ButtonType.CANCEL;
        });
    }

    /**
     * Handle Ok Button Click Event before Dialog Close;
     */
    EventHandler<ActionEvent> onPayment = event -> {
        System.out.println("Payment");
        Account account = controller.getLoginAccount();
        if(account == null){
            var res = showPayment(order.getPrice());
            if(!res){ event.consume();}
        } else {
            if(controller.isUseStamp()){
                var res = showPayment(0);
                if(!res){ event.consume();}
                else {account.useCount();}
            } else {
                var res = showPayment(order.getPrice());
                if(!res){ event.consume();} else {
                    account.addCount();
                }
            }
        }
        OrderManager.getInstance().addOrder(order);
    };

    private boolean showPayment(double price){
        var res = (new PaymentDialog(price)).showAndWait();
        return res.orElse(false);
    }
}
