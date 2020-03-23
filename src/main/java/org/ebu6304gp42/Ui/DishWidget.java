package org.ebu6304gp42.Ui;

import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Data.Dish;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Widget To Display a Dish
 */
public class DishWidget extends HBox {
    final static InputStream default_pic = DishWidget.class.getResourceAsStream(PathConfig.DEFAULT_PIC);
    final static double HEIGHT = 120;

    private Dish dish;

    public DishWidget(Dish dish){
        this.dish = dish;
        ImageView image_view;

        this.setHeight(HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        // Create Image for Dish
        Image image;
        try {
            image = new Image(new FileInputStream(dish.getPic()));
        } catch (IOException e){
            //if no pic found or other IOException, use default pic
            System.err.println(String.format("No picture found for %s, use default image.", dish.getName()));
            image = new Image(default_pic);
        }
        image_view = new ImageView(image);
        image_view.preserveRatioProperty().setValue(false);
        // Bind the height and width of image to the Height of Dishwidget
        image_view.fitWidthProperty().bind(this.heightProperty());
        image_view.fitHeightProperty().bind(this.heightProperty());

        // Label Display Dish Name
        Label name = new Label(dish.getName());
        name.setFont(Font.font(null, FontWeight.BOLD, 14));
        name.setTextFill(Color.BLACK);

        // Label Display Dish Price
        Label price = new Label(String.format("%.2f $", dish.getPrice()));
        price.setFont(Font.font(null,FontWeight.BOLD, 14));
        price.setTextFill(Color.RED);

        // Label Display Dish Description
        Label description = new Label(dish.getDescription());
        description.setTextFill(Color.GRAY);
        description.setFont(Font.font(null,11));
        description.setMaxHeight(HEIGHT);
        description.setAlignment(Pos.TOP_LEFT);
        description.setWrapText(true);
        VBox.setVgrow(description, Priority.ALWAYS); // Make it auto fill empty space of imformation_box

        // A layout contain the information of dish
        VBox imformation_box = new VBox();
        imformation_box.prefHeightProperty().bind(this.heightProperty());
        imformation_box.setFillWidth(true);
        imformation_box.setPadding(new Insets(5));
        imformation_box.setSpacing(8);
        imformation_box.setMaxWidth(HEIGHT*1.6);
        imformation_box.getChildren().addAll(name,price, description);

        // Add image and imformation_box
        this.getChildren().addAll(image_view, imformation_box);

        //Set Drop Shadow and it effect when mouse entering
        DropShadow shadow = new DropShadow();
        shadow.setBlurType(BlurType.GAUSSIAN);
        shadow.setColor(Color.GRAY);
        shadow.setSpread(0.1);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        this.setEffect(shadow);
        this.setOnMouseEntered(event -> {shadow.setSpread(0.5);});
        this.setOnMouseExited(event -> {shadow.setSpread(0.1);});
    }

    public Dish getDish() {
        return dish;
    }
}
