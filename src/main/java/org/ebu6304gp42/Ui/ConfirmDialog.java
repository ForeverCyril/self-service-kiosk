package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.ebu6304gp42.Data.Order;

public class ConfirmDialog extends Dialog<Boolean> {
    public ConfirmDialog(Order order) {
        setTitle("Order information");
        setHeaderText(null);

        VBox order_vBox = new VBox();
        order_vBox.setPadding(new Insets(10,10,10,10));
        order_vBox.setSpacing(20);

        Label order_label = new Label("订单详情");
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
            container.getChildren().add(new CartWidget.OrderedDishWidget(orderedDish));
        }

        Label diningOptions_label = new Label("请选择就餐地点");
        diningOptions_label.setFont(Font.font(24));

        GridPane dining_options = new GridPane();
        dining_options.setAlignment(Pos.CENTER);
        dining_options.minWidthProperty().bind(container.widthProperty());
        dining_options.prefWidthProperty().bind(container.widthProperty());
        Button eatIn_btn = new Button("Eat in");
        Button takeAway_btn = new Button("Take away");
        eatIn_btn.prefWidthProperty().bind(container.widthProperty().multiply(0.4));
        takeAway_btn.prefWidthProperty().bind(container.widthProperty().multiply(0.4));
        dining_options.addRow(0, eatIn_btn, takeAway_btn);
        dining_options.setHgap(18);
        dining_options.setVgap(6);
        //添加各种元素进入vBox
        order_vBox.getChildren().addAll(order_label,order_area,diningOptions_label,dining_options);

        //设置dialog的确定取消按钮
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        getDialogPane().setContent(order_vBox);
        getDialogPane().setPadding(new Insets(6));

        setResultConverter(btn -> {
            if(btn == ButtonType.CANCEL){
                return false;
            } else {
                PaymentDialog paymentDialog = new PaymentDialog();
                var res = paymentDialog.showAndWait();
                return res.isPresent() && res.get();
            }
        });
        }
}
