package org.ebu6304gp42.Controller.Payment;

import com.google.gson.internal.$Gson$Types;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Controller.Shopping.cart.OrderedDishController;
import org.ebu6304gp42.Data.Account;
import org.ebu6304gp42.Data.AccountManager;
import org.ebu6304gp42.Data.Order;
import org.ebu6304gp42.Data.OrderedDish;
import org.ebu6304gp42.View.RegisterDialog;

import java.io.IOException;

public class ConfirmController {
    @FXML
    private VBox order_area;
    @FXML
    private StackPane acc_area;
    @FXML
    private Label price;
    @FXML
    private TextField userID;
    @FXML
    private ToggleButton useStamp;
    @FXML
    private Label user_name;
    @FXML
    private Label stamp_amount;
    @FXML
    private Node acc_not_login;
    @FXML
    private Node acc_login;
    @FXML
    private ToggleGroup typeGroup;


    private Account loginAccount;

    @FXML
    private void onLogin(MouseEvent event){
        int id;
        try {
            id = Integer.parseInt(userID.getText());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User ID input Error");
            alert.showAndWait();
            return;
        }
        var acc = AccountManager.getInstance().seek(id);
        if(acc == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User ID Error");
            alert.showAndWait();
        } else {
            setAccount(acc);
            acc_not_login.setVisible(false);
            acc_login.toFront();
            acc_login.setVisible(true);
        }
    }
    @FXML
    private void onLogout(MouseEvent event){
        acc_login.setVisible(false);
        useStamp.setDisable(true);
        acc_not_login.toFront();
        acc_not_login.setVisible(true);
        loginAccount = null;
    }

    @FXML
    private void onRegister(MouseEvent event){
        var res = (new RegisterDialog()).showAndWait();
        res.ifPresent(account -> {
            setAccount(account);
            acc_not_login.setVisible(false);
            acc_login.toFront();
            acc_login.setVisible(true);
        });
    }

    private void setAccount(Account account){
        loginAccount = account;
        user_name.setText(String.format("Welcome %s", account.getName()));
        stamp_amount.setText(String.format("Now you have %d/10 stamp.", account.getCount()));

        if(loginAccount.getCount() >=10){
            useStamp.setDisable(false);
        }
    }

    public void setOrder(Order order){
        order.getDish().iterator().forEachRemaining(this::addOrderedDish);
        price.setText(String.format("%.2f", order.getPrice()));
    }

    public Account getLoginAccount(){
        return loginAccount;
    }

    public boolean isUseStamp(){
        return useStamp.isSelected();
    }

    private void addOrderedDish(OrderedDish dish){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/OrderedDishWidget.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        ((OrderedDishController)loader.getController()).setOrderedDish(dish);
        order_area.getChildren().add(loader.getRoot());
    }

    public String getType(){
        return typeGroup.getSelectedToggle().getUserData().toString();
    }
}
