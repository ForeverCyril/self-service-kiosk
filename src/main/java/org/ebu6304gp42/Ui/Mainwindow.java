package org.ebu6304gp42.Ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.ebu6304gp42.Config.GeneraConfig;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Data.DishOption;
import org.ebu6304gp42.Data.OrderedDish;
import org.ebu6304gp42.Event.MenuClickedEvent;

import java.util.ArrayList;

/**
 * Main window of this Application
 */
public class Mainwindow extends Application {
    ShopWidget cart = new ShopWidget();
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Self Service Kiosk");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/pic/app.png")));

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5));
        // Top Area
        BorderPane topArea = new BorderPane();
        Label title = new Label(GeneraConfig.STORE_NAME);
        title.setFont(Font.font(null, FontWeight.BOLD, 36));
        topArea.setCenter(title);

        Label mangerEnter = new Label("Manage");
        mangerEnter.setFont(Font.font(14));
        mangerEnter.setUnderline(true);
        mangerEnter.setCursor(Cursor.HAND);
        mangerEnter.setAlignment(Pos.BOTTOM_RIGHT);
        mangerEnter.setTextFill(Color.GRAY);
        BorderPane.setAlignment(mangerEnter, Pos.BOTTOM_CENTER);
        mangerEnter.setOnMouseClicked(event -> {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setHeaderText(null);
//            alert.setContentText("Manager Function is under development.");
//            alert.initOwner(primaryStage);
//            alert.showAndWait();
            Group root2 = new Group();
            Scene scene = new Scene(root2,600, 300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            primaryStage.close();
        });
        topArea.setRight(mangerEnter);

        root.setTop(topArea);

        //Buy Area
        HBox buyArea = new HBox();
        buyArea.setSpacing(6);
        buyArea.prefWidthProperty().bind(root.widthProperty());
        MenuWidget menu = new MenuWidget();
        ArrayList<Dish> dishes = new ArrayList<>();
        for(int i=0;i<5;i++){
            Dish dish = new Dish();
            dish.setName("Dish "+(i+1));
            dish.setDescription(String.format("This is the description for dish %d", i+1));
            dish.setPrice(0.99 + (i+1));
            dish.setPic("pic/dish/ramen.jfif");
            for(int j=0; j<=i%3; j++){
                DishOption option = new DishOption("Op"+j);
                for(int k=0;k<3;k++)
                    option.addOption("Sel"+k);
                dish.addOption(option);
            }
            dishes.add(dish);
        }
        menu.load(dishes);
        menu.addEventHandler(MenuClickedEvent.MENU_CLICKED_EVENT, event -> {
            OptionDialog dialog = new OptionDialog(event.getDish());
            var res = dialog.showAndWait();
            res.ifPresent(orderedDish -> cart.addDish(orderedDish));
        });
        HBox.setHgrow(menu, Priority.ALWAYS);
        buyArea.getChildren().add(menu);


        cart.prefHeightProperty().bind(buyArea.heightProperty());
        cart.setPrefWidth(250);
        buyArea.getChildren().add(cart);

        root.setCenter(buyArea);

        HBox statusBar = new HBox();
        statusBar.getChildren().add(new Label("Status Bar"));

        root.setBottom(statusBar);

        Scene scene = new Scene(root,900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
