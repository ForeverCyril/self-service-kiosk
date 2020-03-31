package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.ebu6304gp42.Data.OrderedDish;

public class ShopWidget extends VBox {

    private VBox cart = new VBox();;
    private Label price_num = new Label();
    private double total_price = 0;

    public ShopWidget(){
        Label title = new Label("Shopping Cart");
        title.setFont(Font.font(null, FontWeight.BOLD, 12));
        this.getChildren().add(title);

        ScrollPane cart_area = new ScrollPane();
        VBox.setVgrow(cart_area, Priority.ALWAYS);
        cart_area.prefWidthProperty().bind(this.widthProperty());
        cart_area.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cart_area.setPadding(new Insets(6));
        cart.setSpacing(6);
        cart.prefWidthProperty().bind(cart_area.widthProperty());
        cart_area.setContent(cart);

        HBox price_area = new HBox();
        Label price_label = new Label("Price: ");
        Label price_unit = new Label(" $");
        Font price_font = Font.font(null, FontWeight.BOLD, 14);
        price_label.setFont(price_font);
        price_unit.setFont(price_font);
        price_num.setFont(price_font);

        price_area.getChildren().addAll(price_label, price_num, price_unit);

        GridPane button_area = new GridPane();
        button_area.setAlignment(Pos.CENTER);
        button_area.minWidthProperty().bind(this.widthProperty());
        button_area.prefWidthProperty().bind(this.widthProperty());
        Button clear_btn = new Button("Clear");
        clear_btn.setOnMouseClicked(event -> {
            cart.getChildren().clear();
            resetPrice();
        });
        Button submit_btn = new Button("Submit");
        submit_btn.prefWidthProperty().bind(this.widthProperty().multiply(0.4));
        clear_btn.prefWidthProperty().bind(this.widthProperty().multiply(0.4));
        button_area.addRow(0, clear_btn, submit_btn);
        button_area.setHgap(18);
        button_area.setVgap(6);

        this.getChildren().addAll(cart_area,price_area,button_area);
        this.setSpacing(6);
    }

    public void addDish(OrderedDish orderedDish){
        OrderedDishWidget widget = new OrderedDishWidget(orderedDish);
        cart.getChildren().add(widget);
        addPrice(orderedDish.getTotalPrice());
    }

    private void resetPrice(){
        total_price = 0;
        this.price_num.setText(String.format("%.2f", total_price));
    }

    private void addPrice(double price){
        total_price += price;
        price_num.setText(String.format("%.2f", total_price));
    }

    private void subPrice(double price){
        total_price -= price;
        price_num.setText(String.format("%.2f", total_price));
    }
}

/**
 * @author Liu Yingying
 * @author Dong Bo
 */
class OrderedDishWidget extends VBox {
    private OrderedDish orderedDish;
    final static double WIDTH = 120;

    public OrderedDishWidget(OrderedDish orderedDish) {
        this.setPadding(new Insets(3));
        this.orderedDish = orderedDish;
        this.setPrefHeight(80);
        //this.setWidth();
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));


        // Label Display Dish Name
        Label name = new Label(orderedDish.getName());
        name.setFont(Font.font(null, FontWeight.BOLD, 14));
        name.setTextFill(Color.BLACK);

        // Label Display Dish Price
        Label price = new Label(String.format("%.2f $", orderedDish.getPrice()));
        price.setFont(Font.font(null,FontWeight.BOLD, 14));
        price.setTextFill(Color.WHITE);

        HBox imformation_box = new HBox();
        imformation_box.setFillHeight(true);
        imformation_box.setPadding(new Insets(5));
        imformation_box.setSpacing(8);
        imformation_box.getChildren().addAll(name,price);

        StringBuilder data = new StringBuilder("Option: ");
        for(var option:orderedDish.getOptions()){
            data.append(option.getName()).append(": ").append(option.getSelected_option()).append("; ");
        }
        Label description = new Label(data.toString());
        description.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(description, Priority.ALWAYS);
        description.setAlignment(Pos.BOTTOM_LEFT);
        description.setWrapText(true);
        description.setFont(Font.font(null,FontWeight.BOLD, 10));
        description.setTextFill(Color.GRAY);

        this.getChildren().addAll(imformation_box, description );
        DropShadow shadow = new DropShadow();
        shadow.setBlurType(BlurType.GAUSSIAN);
        shadow.setColor(Color.GRAY);
        shadow.setSpread(0.1);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        this.setEffect(shadow);
    }


}
