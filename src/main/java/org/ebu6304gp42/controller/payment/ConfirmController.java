package org.ebu6304gp42.controller.payment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.controller.shopping.cart.OrderedDishController;
import org.ebu6304gp42.data.Account;
import org.ebu6304gp42.data.AccountManager;
import org.ebu6304gp42.data.Order;
import org.ebu6304gp42.data.OrderedDish;
import org.ebu6304gp42.exception.AccountException;
import org.ebu6304gp42.view.RegisterDialog;
import org.ebu6304gp42.view.ShowAlert;
import org.ebu6304gp42.view.UpdateAccountDialog;

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

        try {
            var acc = AccountManager.getInstance().seek(id);
            setAccount(acc);
            acc_not_login.setVisible(false);
            acc_login.toFront();
            acc_login.setVisible(true);
        } catch (AccountException e) {
            ShowAlert.error("Account Error", e.getMessage());
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

    @FXML
    private void onEditInformation(MouseEvent event){
        System.out.println("Clicked!");
        var res = (new UpdateAccountDialog(loginAccount.getId())).showAndWait();
        res.ifPresent(result->{
            if (result){
                setAccount(loginAccount);
            }
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

    /**
     * Set Order
     * @param order order
     */
    public void setOrder(Order order){
        order.getDish().iterator().forEachRemaining(this::addOrderedDish);
        price.setText(String.format("%.2f", order.getPrice()));
    }

    /**
     * Get Login Account
     * @return return Login Account
     */
    public Account getLoginAccount(){
        return loginAccount;
    }

    /**
     * Return check status of use stamp
     * @return whether use stamp
     */
    public boolean isUseStamp(){
        return useStamp.isSelected();
    }

    private void addOrderedDish(OrderedDish dish){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shop/OrderedDishWidget.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        ((OrderedDishController)loader.getController()).setOrderedDish(dish);
        order_area.getChildren().add(loader.getRoot());
    }

    /**
     * Get Order Type
     * @return order type
     */
    public String getType(){
        return typeGroup.getSelectedToggle().getUserData().toString();
    }
}
