package org.ebu6304gp42.Controller.Payment;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import org.ebu6304gp42.Data.Account;
import org.ebu6304gp42.Data.AccountManager;

public class RegisterController {
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private CheckBox accept;
    @FXML
    private CheckBox accept_rec;

    @FXML
    private void showUserAgreement(MouseEvent event){
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/res/agreement.html").toString());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setContent(webView);
        alert.showAndWait();
    }

    public ReadOnlyBooleanProperty getAcceptProperty(){
        return accept.selectedProperty();
    }

    public Account getAccount(){
        return AccountManager.getInstance().register(
                first_name.getText(),
                last_name.getText(),
                phone.getText(),
                email.getText(),
                accept_rec.isSelected()
        );
    }


    public boolean validData(){
        if(!AccountManager.validateName(first_name.getText())){
            System.out.println("First Name Error");
            return false;
        }
        if(!AccountManager.validateName(last_name.getText())){
            System.out.println("Last Name Error");
            return false;
        }
        if(email.getText().isBlank() && phone.getText().isBlank()){
            System.out.println("at least email and phone");
            return false;
        }
        if(!email.getText().isBlank() && !AccountManager.validateEmail(email.getText())){
            System.out.println("Email error");
            return false;
        }
        if(!phone.getText().isBlank() && !AccountManager.validateMobilePhone(phone.getText())){
            System.out.println("Phone error");
            return false;
        }
        return true;
    }
}
