package org.ebu6304gp42.Controller.Managing.accountTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.ebu6304gp42.Data.Account;
import org.ebu6304gp42.Data.AccountManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountTableController implements Initializable  {
    private ObservableList<Account> data;

    @FXML
    private TableView<Account> AccountTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList(AccountManager.getInstance().getAccount());
        AccountTable.setItems(data);
    }
}
