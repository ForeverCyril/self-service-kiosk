package org.ebu6304gp42.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.ebu6304gp42.controller.payment.RegisterController;
import org.ebu6304gp42.exception.AccountException;

import java.io.IOException;

public class UpdateAccountDialog extends Dialog<Boolean> {
    public UpdateAccountDialog(int id){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shop/Register.fxml"));
        try {
            loader.load();
            this.getDialogPane().setContent(loader.getRoot());
        } catch (IOException e){
            e.printStackTrace();
        }
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION,
            event -> {
                try {
                    ((RegisterController)loader.getController()).updateData(id);
                } catch (AccountException e) {
                    ShowAlert.error("Information Error", e.getLocalizedMessage());
                    event.consume();
                }
            }
        );
        setResultConverter(btn->{
            return btn == ButtonType.APPLY;
        });
    }
}
