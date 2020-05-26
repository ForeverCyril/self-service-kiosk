package org.ebu6304gp42.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.ebu6304gp42.controller.payment.RegisterController;
import org.ebu6304gp42.data.Account;
import org.ebu6304gp42.data.AccountManager;
import org.ebu6304gp42.exception.AccountException;

import java.io.IOException;

public class UpdateAccountDialog extends Dialog<Boolean> {
    public UpdateAccountDialog(int id){
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shop/Register.fxml"));
        try {
            loader.load();
            ((RegisterController)loader.getController())
                    .setAccount(AccountManager.getInstance().seek(id));
            this.getDialogPane().setContent(loader.getRoot());
        } catch (IOException e){
            e.printStackTrace();
        } catch (AccountException e){
            ShowAlert.error("Information Error", e.getLocalizedMessage());
            getDialogPane().lookupButton(ButtonType.APPLY).setDisable(true);
        }
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
