package org.ebu6304gp42.Controller.Shopping;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.ebu6304gp42.Config.GeneraConfig;
import org.ebu6304gp42.Controller.Shopping.cart.CartController;
import org.ebu6304gp42.Controller.Shopping.menu.DishClickedEvent;
import org.ebu6304gp42.Controller.Shopping.menu.MenuController;
import org.ebu6304gp42.Data.DishManager;
import org.ebu6304gp42.View.OptionDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShopController implements Initializable {

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
        FXMLLoader menu_loader = new FXMLLoader(getClass().getResource("/FXML/Menu.fxml"));
        FXMLLoader cart_loader = new FXMLLoader(getClass().getResource("/FXML/Cart.fxml"));
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
    }

    @FXML
    public void enterManage(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
            root.fireEvent(new EnterManageEvent(EnterManageEvent.ENTER_MANAGE_EVENT));
        }
    }

    public void refresh(){
        menuController.clear();
        menuController.getModel().addAll(DishManager.getInstance().getDish());
    }
}
