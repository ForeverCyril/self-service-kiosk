package org.ebu6304gp42.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ebu6304gp42.Controller.Shopping.EnterManageEvent;
import org.ebu6304gp42.Controller.Shopping.ShopController;
import org.ebu6304gp42.Data.DishBank;
import org.ebu6304gp42.Controller.Shopping.menu.MenuController;
import org.ebu6304gp42.Controller.Shopping.menu.DishClickedEvent;

import java.io.IOException;

public class Main_fxml extends Application {
    private ShopController shopController;

    @Override
    public void start(Stage shoppingStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Shop.fxml"));
        loader.load();
        Scene scene = new Scene(loader.getRoot());
        shopController = loader.getController();
        shoppingStage.setScene(scene);
        shoppingStage.show();

        scene.addEventHandler(EnterManageEvent.ENTER_MANAGE_EVENT, event -> {
            shoppingStage.hide();
            FXMLLoader manage = new FXMLLoader(getClass().getResource("/FXML/Manage.fxml"));
            try {
                manage.load();
                Stage manageStage = new Stage();
                manageStage.setScene(new Scene(manage.getRoot()));
                manageStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
            shopController.refresh();
            shoppingStage.show();
        });
    }
}
