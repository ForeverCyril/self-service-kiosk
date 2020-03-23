package org.ebu6304gp42.Ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.ebu6304gp42.Data.Dish;


public class Mainwindow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane root = new FlowPane();
        Scene scene = new Scene(root,400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        Dish dish = new Dish();
        dish.setName("Test Name");
        dish.setDescription("Tiosfj jdflas jfdsk ghdfhj fhda kafopjf fjid fa nffa;jf");
        dish.setPic("pic/dish/type1.pngd");
        dish.setPrice(12.9887);
        DishWidget dishWidget = new DishWidget(dish);
        dishWidget.setLayoutX(10);
        dishWidget.setLayoutY(10);
        root.getChildren().add(dishWidget);
    }
}
