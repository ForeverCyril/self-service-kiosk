package org.ebu6304gp42.controller.payment;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import org.ebu6304gp42.component.InputField.PhoneField;
import org.ebu6304gp42.data.Account;
import org.ebu6304gp42.data.AccountManager;
import org.ebu6304gp42.exception.account.IllegalInputException;

public class RegisterController {
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField email;
    @FXML
    private PhoneField phone;
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

    public Account getAccount() throws IllegalInputException {
        return AccountManager.getInstance().register(
                first_name.getText(),
                last_name.getText(),
                phone.getValue(),
                email.getText(),
                accept_rec.isSelected()
        );
    }

    public void updateData(int id) throws IllegalInputException {
        AccountManager.getInstance().updateInformation(id,
                first_name.getText(),
                last_name.getText(),
                phone.getValue(),
                email.getText(),
                accept_rec.isSelected()
        );
    }
}
