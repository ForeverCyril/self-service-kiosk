package org.ebu6304gp42.View;


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

    public ConfirmDialog(Order order){
        setTitle("Confirm Order");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Confirm.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        ConfirmController controller = (ConfirmController)loader.getController();
        controller.setOrder(order);
        getDialogPane().setContent(loader.getRoot());

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button ok_btn = (Button) getDialogPane().lookupButton(ButtonType.OK);
        ok_btn.setOnAction(event -> {
            Account account = controller.getLoginAccount();
            if(account != null){
                if(controller.isUseStamp()){
                    account.useCount();
                } else {
                    account.addCount();
                }
            }
            OrderManager.getInstance().addOrder(order);
        });
        setResultConverter(btn -> {
            return btn != ButtonType.CANCEL;
        });
    }
}
