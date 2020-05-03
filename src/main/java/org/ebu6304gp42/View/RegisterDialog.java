package org.ebu6304gp42.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.ebu6304gp42.Controller.Payment.RegisterController;
import org.ebu6304gp42.Data.Account;
import org.ebu6304gp42.Exception.Account.IllegalInputException;

import java.io.IOException;

public class RegisterDialog extends Dialog<Account> {
    private final RegisterController controller;
    private Account result = null;
    public RegisterDialog(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Shop/Register.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        controller = loader.getController();
        getDialogPane().setContent(loader.getRoot());
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        Button applyBtn = (Button)getDialogPane().lookupButton(ButtonType.APPLY);
        applyBtn.disableProperty().bind(controller.getAcceptProperty().not());
        applyBtn.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                result = controller.getAccount();
            } catch (IllegalInputException e){
                ShowAlert.error("Information Error", e.getReason());
                result = null;
                event.consume();
            }
        });
        setResultConverter(btn->{
            return result;
        });
    }
}
