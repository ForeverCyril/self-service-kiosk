package org.ebu6304gp42.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.ebu6304gp42.config.GeneralConfig;
import org.ebu6304gp42.controller.managing.MagaeController;
import org.ebu6304gp42.controller.shopping.EnterManageEvent;
import org.ebu6304gp42.controller.shopping.ShopController;
import org.ebu6304gp42.data.AccountManager;
import org.ebu6304gp42.data.DishManager;

import java.io.IOException;
import java.util.Arrays;

public class MainWindow extends Application {
    private ShopController shopController;
    private Parent shop;
    private Stage shopStage;
    @Override
    public void start(Stage primaryStage){
        initShoppingInterface();

        shopStage = primaryStage;
        Scene scene = new Scene(shop);
        shopStage.setScene(scene);
        shopStage.setTitle(GeneralConfig.STORE_NAME);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shop/Shop.fxml"));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manage/Manage.fxml"));
                loader.load();
                Stage manageStage = new Stage();
                manageStage.initOwner(shopStage);
                manageStage.setScene(new Scene(loader.getRoot()));
                manageStage.setOnCloseRequest(closeEvent->{
                    if(((MagaeController)loader.getController()).dataChanged()){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Dish Changed!");
                        alert.setContentText("Are you want to exit with out saving?");
                        alert.getButtonTypes().clear();
                        alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.APPLY, ButtonType.YES);
                        ((Button)alert.getDialogPane().lookupButton(ButtonType.APPLY)).setText("Save");
                        var res= alert.showAndWait();
                        if(res.isPresent()){
                            if(res.get() == ButtonType.APPLY){
                                ((MagaeController)loader.getController()).saveMenu();
                            } else if (res.get() == ButtonType.CANCEL){
                                    closeEvent.consume();
                            }
                        } else {
                            closeEvent.consume();
                        }
                    }
                });

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
