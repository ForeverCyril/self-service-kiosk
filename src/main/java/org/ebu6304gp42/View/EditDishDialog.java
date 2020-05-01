package org.ebu6304gp42.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import org.ebu6304gp42.Controller.Managing.menuTable.EditDishController;
import org.ebu6304gp42.Data.Dish;

import java.io.IOException;

public class EditDishDialog extends Dialog<Dish> {
    public EditDishDialog(Dish dish){
        setTitle("Add/Modify the dish");
        setHeaderText(null);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Manage/EditDish.fxml"));
        try {
            loader.load();
            getDialogPane().setContent(loader.getRoot());
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

        EditDishController controller = loader.getController();
        controller.setDish(dish!=null?dish:(new Dish()));


        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            if(!controller.valid())
                event.consume();
        });
        setResultConverter(btn->{
            if(btn == ButtonType.CANCEL){
                return null;
            }
            return controller.getDish();
        });
    }

}

