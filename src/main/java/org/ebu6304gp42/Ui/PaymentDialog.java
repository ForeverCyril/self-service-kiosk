package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PaymentDialog extends Dialog<Boolean> {
    public PaymentDialog() {
        super();
        VBox paySuccessful_vBox = new VBox();
        paySuccessful_vBox.setPadding(new Insets(10,10,10,10));
        paySuccessful_vBox.setSpacing(20);
        paySuccessful_vBox.setPrefHeight(100);
        paySuccessful_vBox.setPrefWidth(200);

        Label paySuccessful_label = new Label("支付成功");
        paySuccessful_label.setFont(Font.font(32));

        paySuccessful_vBox.getChildren().add(paySuccessful_label);

        //设置dialog的确定取消按钮
        getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        getDialogPane().setContent(paySuccessful_vBox);
        getDialogPane().setPadding(new Insets(6));

        setResultConverter(btn -> btn == ButtonType.OK);
    }
}
