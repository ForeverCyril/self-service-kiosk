package org.ebu6304gp42.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.ebu6304gp42.Controller.Payment.RegisterController;
import org.ebu6304gp42.Data.Account;

import java.io.IOException;

public class RegisterDialog extends Dialog<Account> {
    private final RegisterController controller;
    public RegisterDialog(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Register.fxml"));
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
        applyBtn.setOnAction(event -> {
            if(!controller.validData()){
                System.out.println("Data Error");
                event.consume();
            }
            System.out.println(controller.validData());
        });
        setResultConverter(btn->{
            if(btn == ButtonType.APPLY){
                Account acc = controller.getAccount();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(String.format("Account ID: %08d", acc.getId()));
                alert.showAndWait();
                return acc;
            }
            return null;
        });
    }
}
