package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.ebu6304gp42.Data.Account;
import org.ebu6304gp42.Data.AccountManager;
import org.ebu6304gp42.Data.Order;
import org.ebu6304gp42.Ui.AccoutDialog.*;

public class ConfirmDialog extends Dialog<Boolean> {
    AccountManager accountManager = new AccountManager();
    Order order = new Order();
    Account account = new Account();

    public ConfirmDialog(Order order) {
        setTitle("Order information");
        setHeaderText(null);

        VBox order_vBox = new VBox();
        order_vBox.setPadding(new Insets(10,10,10,10));
        order_vBox.setSpacing(20);

        Label order_label = new Label("Cart");
        order_label.setFont(Font.font(32));

        //订单里详细信息的排列格式
        VBox container = new VBox();
        //存放订单
        ScrollPane order_area = new ScrollPane();
        VBox.setVgrow(order_area, Priority.ALWAYS);
        order_area.prefWidthProperty().bind(container.widthProperty().multiply(2));
        order_area.prefHeightProperty().bind(this.heightProperty());
        order_area.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        order_area.setPadding(new Insets(6));
        container.setSpacing(6);
        container.prefWidthProperty().bind(order_area.widthProperty());
        order_area.setContent(container);

        for(var orderedDish:order.getDish()){
            //container.getChildren().add(new CartWidget.OrderedDishWidget(orderedDish));
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);
        //grid.prefHeightProperty().bind(this.heightProperty());
        grid.prefWidthProperty().bind(this.widthProperty());
        grid.setVgap(18);
        grid.setHgap(10);

        Label label = new Label("User ID : ");
        label.setFont(Font.font(18));
        GridPane.setConstraints(label, 0, 0);
        grid.getChildren().add(label);

        final TextField accNum = new TextField();
        accNum.setPromptText("Enter your account number.");
        GridPane.setConstraints(accNum, 1, 0);
        grid.getChildren().add(accNum);

        Button confirm = new Button("CONFIRM");
        GridPane.setConstraints(confirm, 1, 1);
        confirm.prefWidthProperty().bind(order_area.widthProperty().multiply(0.4));
        grid.getChildren().add(confirm);
        confirm.setOnMouseClicked(event -> {
        });

        Button register_btn = new Button("REGISTER");
        GridPane.setConstraints(register_btn, 0, 1);
        register_btn.prefWidthProperty().bind(order_area.widthProperty().multiply(0.4));
        grid.getChildren().add(register_btn);
        register_btn.setOnMouseClicked(event -> {
            RegisterDialog dialog = new RegisterDialog();
            var res = dialog.showAndWait();
        });


        Button pay = new Button("Pay");
        pay.prefWidthProperty().bind(order_area.widthProperty().multiply(0.4));
        pay.setOnMouseClicked(event -> {
            // EatDialog eatDialog = new EatDialog(order,account);
            //var res = eatDialog.showAndWait();
        });


        Button cancel = new Button("Cancel");
        cancel.prefWidthProperty().bind(order_area.widthProperty().multiply(0.4));
        grid.addRow(2, pay, cancel);

        //添加各种元素进入vBox
        order_vBox.getChildren().addAll(order_label,order_area,grid);


        //设置dialog的确定取消按钮
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        getDialogPane().setContent(order_vBox);
        getDialogPane().setPadding(new Insets(6));

        setResultConverter(btn -> {
            if(btn == ButtonType.CANCEL){
                return false;
            } else {
                return null;
            }
        });
    }


}
