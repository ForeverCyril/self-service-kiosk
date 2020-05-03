package org.ebu6304gp42.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.ebu6304gp42.Controller.Managing.menuTable.DishStaticController;
import org.ebu6304gp42.Data.Dish;

import java.io.IOException;

public class DishStaticDialog extends Dialog<ButtonType> {
    public static ButtonType EditButtonType = new ButtonType("Edit", ButtonBar.ButtonData.YES);
    public DishStaticDialog(Dish dish){
        setTitle(dish.getName());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Manage/DishStatic.fxml"));
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
