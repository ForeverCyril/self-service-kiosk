package org.ebu6304gp42.View;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.ebu6304gp42.Controller.Payment.ConfirmController;
import org.ebu6304gp42.Data.Order;

import java.io.IOException;

public class ConfirmDialog extends Dialog<Boolean> {
    private Order order;

    public ConfirmDialog(Order order){
        setTitle("Confirm Order");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Confirm.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        ((ConfirmController)loader.getController()).setOrder(order);
        getDialogPane().setContent(loader.getRoot());

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        setResultConverter(btn -> {
            return btn != ButtonType.CANCEL;
        });
    }
}
