package org.ebu6304gp42.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.ebu6304gp42.Config.GeneraConfig;
import org.ebu6304gp42.Controller.Shopping.EnterManageEvent;
import org.ebu6304gp42.Controller.Shopping.ShopController;
import org.ebu6304gp42.Data.AccountManager;
import org.ebu6304gp42.Data.DishManager;
import org.ebu6304gp42.Data.Order;
import org.ebu6304gp42.Data.OrderManager;

import java.io.IOException;
import java.util.Arrays;

public class Main extends Application {
    private ShopController shopController;
    private Parent shop;
    private Stage shopStage;
    @Override
    public void start(Stage primaryStage){
        initShoppingInterface();

        shopStage = primaryStage;
        Scene scene = new Scene(shop);
        shopStage.setScene(scene);
        shopStage.setTitle(GeneraConfig.STORE_NAME);
        shopStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/pic/app.png")));

        shopStage.show();

        shopStage.setOnCloseRequest(event -> {
            AccountManager.getInstance().save();
            DishManager.getInstance().save();
        });

        scene.addEventHandler(EnterManageEvent.ENTER_MANAGE_EVENT, new enterManageHandler());
    }
    /**
     * init shop interface
     */
    private void initShoppingInterface(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Shop.fxml"));
            loader.load();
            shop = loader.getRoot();
            shopController = loader.getController();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(e.getLocalizedMessage());
            alert.setContentText(Arrays.toString(e.getStackTrace()));
            alert.showAndWait();
            System.exit(-1);
        }
    }

    /**
     * handler enter manage request, show mangae window and hide shop windows.
     * After that it will refresh menu.
     */
    class enterManageHandler implements EventHandler<EnterManageEvent>{

        @Override
        public void handle(EnterManageEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Manage.fxml"));
                loader.load();
                Stage manageStage = new Stage();
                manageStage.initOwner(shopStage);
                manageStage.setScene(new Scene(loader.getRoot()));
                shopStage.hide();
                manageStage.showAndWait();
                shopStage.show();
                shopController.refresh();
            } catch (IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(e.getLocalizedMessage());
                alert.setContentText(Arrays.toString(e.getStackTrace()));
                alert.showAndWait();
                System.exit(-1);
            }
        }
    }
}
