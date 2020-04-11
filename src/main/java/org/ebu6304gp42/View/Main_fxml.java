package org.ebu6304gp42.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ebu6304gp42.Data.DishBank;
import org.ebu6304gp42.Controller.Shopping.menu.MenuController;
import org.ebu6304gp42.Controller.Shopping.menu.DishClickedEvent;

public class Main_fxml extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Shop.fxml"));
        loader.load();

        primaryStage.setScene(new Scene(loader.getRoot()));
        /*DishBank bank = new DishBank();
        bank.load();
        primaryStage.addEventHandler(DishClickedEvent.DISH_CLICKED_EVENT, event -> {
            System.out.println(event.getDish().getName());
        });
        ((MenuController) loader.getController()).getModel().addAll(bank.getDish());*/
        primaryStage.show();
    }
}
