package org.ebu6304gp42.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import org.ebu6304gp42.Controller.Shopping.menu.OptionDialogController;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Data.OrderedDish;

import java.io.IOException;

public class OptionDialog extends Dialog<OrderedDish> {
    public OptionDialog(Dish dish){
        setTitle(dish.getName());
        setHeaderText(null);
        setGraphic(null);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Shop/OptionDialog.fxml"));
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
