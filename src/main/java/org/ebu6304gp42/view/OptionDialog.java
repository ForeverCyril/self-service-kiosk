package org.ebu6304gp42.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import org.ebu6304gp42.controller.shopping.menu.OptionDialogController;
import org.ebu6304gp42.data.Dish;
import org.ebu6304gp42.data.OrderedDish;

import java.io.IOException;

public class OptionDialog extends Dialog<OrderedDish> {
    public OptionDialog(Dish dish){
        setTitle(dish.getName());
        setHeaderText(null);
        setGraphic(null);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shop/OptionDialog.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

        ((OptionDialogController)loader.getController()).setDish(dish);

        getDialogPane().setContent(loader.getRoot());
        setResultConverter(btn -> {
            if(btn == ButtonType.OK){
                return ((OptionDialogController) loader.getController()).getOrderedDish();
            }
            return null;
        });
    }
}
