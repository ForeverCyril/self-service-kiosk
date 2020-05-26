package org.ebu6304gp42.controller.payment;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import org.ebu6304gp42.component.inputField.PhoneField;
import org.ebu6304gp42.data.Account;
import org.ebu6304gp42.data.AccountManager;
import org.ebu6304gp42.exception.AccountException;

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

    /**
     * Check status of accept user agreement.
     * @return check status of accept user agreement.
     */
    public ReadOnlyBooleanProperty getAcceptProperty(){
        return accept.selectedProperty();
    }

    /**
     * Registered Account
     * @return new Account
     * @throws AccountException input information error
     */
    public Account getAccount() throws AccountException {
        return AccountManager.getInstance().register(
                first_name.getText(),
                last_name.getText(),
                phone.getValue(),
                email.getText(),
                accept_rec.isSelected()
        );
    }

    /**
     * Update information for account with id.
     * @param id account id
     * @throws AccountException input error
     */
    public void updateData(int id) throws AccountException {
        AccountManager.getInstance().updateInformation(id,
                first_name.getText(),
                last_name.getText(),
                phone.getValue(),
                email.getText(),
                accept_rec.isSelected()
        );
    }

    /**
     * Set Account Information, used for modify information.
     * @param account account
     */
    public void setAccount(Account account){
        first_name.setText(account.getFirst_name());
        last_name.setText(account.getLast_name());
        phone.setText(account.getPhone());
        email.setText(account.getEmail());
        accept.setSelected(true);
        accept.setDisable(true);
        accept_rec.setSelected(account.isAccept_rec());
    }
}
