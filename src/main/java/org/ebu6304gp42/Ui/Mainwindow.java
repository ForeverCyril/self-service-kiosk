package org.ebu6304gp42.Ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Event.MenuClickedEvent;

import java.util.ArrayList;


public class Mainwindow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Self Service Kiosk");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/pic/app.png")));

        MenuWidget root = new MenuWidget();

        ArrayList<Dish> dishes = new ArrayList<>();
        for(int i=0;i<5;i++){
            Dish dish = new Dish();
            dish.setName("Dish "+(i+1));
            dish.setDescription(String.format("This is the description for dish %d", i+1));
            dish.setPrice(0.99 + (i+1));
            dish.setPic("pic/dish/type1.png");
            dishes.add(dish);
        }

        root.load(dishes);
        root.addEventHandler(MenuClickedEvent.MENU_CLICKED_EVENT, event -> {
            System.out.println(event.getDish().getName());
        });

        Scene scene = new Scene(root,800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
