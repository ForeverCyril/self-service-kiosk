package org.ebu6304gp42.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.ebu6304gp42.controller.managing.menuTable.DishStaticController;
import org.ebu6304gp42.data.Dish;

import java.io.IOException;

public class DishStaticDialog extends Dialog<ButtonType> {
    public static ButtonType EditButtonType = new ButtonType("Edit", ButtonBar.ButtonData.YES);
    public DishStaticDialog(Dish dish){
        setTitle(dish.getName());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manage/DishStatic.fxml"));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }getDialogPane().getButtonTypes().addAll(EditButtonType, ButtonType.CLOSE);
        ((DishStaticController)loader.getController()).setDish(dish);
        getDialogPane().setContent(loader.getRoot());
        setResultConverter(btn->{return btn;});
    }
}
