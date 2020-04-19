package org.ebu6304gp42.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Controller.Shopping.menu.OptionDialogController;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Data.DishOption;
import org.ebu6304gp42.Data.OrderedDish;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class OptionDialog extends Dialog<OrderedDish> {
    public OptionDialog(Dish dish){
        setTitle(dish.getName());
        setHeaderText(null);
        setGraphic(null);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/OptionDialog.fxml"));
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
