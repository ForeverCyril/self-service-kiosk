package org.ebu6304gp42.controller.shopping;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.ebu6304gp42.config.GeneraConfig;
import org.ebu6304gp42.controller.shopping.cart.CartController;
import org.ebu6304gp42.controller.shopping.menu.DishClickedEvent;
import org.ebu6304gp42.controller.shopping.menu.MenuController;
import org.ebu6304gp42.data.DishManager;
import org.ebu6304gp42.data.DishStatistic;
import org.ebu6304gp42.view.OptionDialog;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    @FXML
    private Label time;
    @FXML
    private Node root;
    @FXML
    private Node menu;
    @FXML
    private Node cart;
    @FXML
    private Label title;

    private CartController cartController;
    private MenuController menuController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(GeneraConfig.STORE_NAME);
        FXMLLoader menu_loader = new FXMLLoader(getClass().getResource("/fxml/shop/Menu.fxml"));
        FXMLLoader cart_loader = new FXMLLoader(getClass().getResource("/fxml/shop/Cart.fxml"));
        try {
            menu_loader.setRoot(menu);
            cart_loader.setRoot(cart);
            menu_loader.load();
            cart_loader.load();

            menuController = menu_loader.getController();
            cartController = cart_loader.getController();
        } catch (IOException e){
            e.printStackTrace();
        }
        refresh();
        menu.addEventHandler(DishClickedEvent.DISH_CLICKED_EVENT, event -> {
            var res = (new OptionDialog(event.getDish())).showAndWait();
            res.ifPresent(orderedDish -> {
                cartController.getModel().add(orderedDish);
            });
        });
        // show time in status bar
        Timeline time_count = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            time.setText((new Date()).toString());
        }));
        time_count.setCycleCount(Timeline.INDEFINITE);
        time_count.play();
    }

    @FXML
    public void enterManage(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
            root.fireEvent(new EnterManageEvent(EnterManageEvent.ENTER_MANAGE_EVENT));
        }
    }

    public void refresh(){
        menuController.clear();
        DishStatistic.statisticDish();
        menuController.getModel().addAll(DishManager.getInstance().getDish());
    }
}
