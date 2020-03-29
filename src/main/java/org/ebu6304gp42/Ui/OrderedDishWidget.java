package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.ebu6304gp42.Data.OrderedDish;

/**
 * @author Liu Yingying
 * @author Dong Bo
 */
public class OrderedDishWidget extends VBox {
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
