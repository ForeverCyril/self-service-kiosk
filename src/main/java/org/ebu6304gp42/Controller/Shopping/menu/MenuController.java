package org.ebu6304gp42.Controller.Shopping.menu;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import org.ebu6304gp42.Data.Dish;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable{
    private ObservableList<Dish> dishes = FXCollections.observableArrayList();

    @FXML
    FlowPane dishPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dishes.addListener((ListChangeListener.Change<? extends Dish> c) -> {
            while (c.next()){
                if(c.wasAdded()){
                    for(Dish dish:c.getAddedSubList()){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DishWidget.fxml"));
                        try {
                            loader.load();
                            ((DishWidgetController)loader.getController()).setDish(dish);
                            dishPane.getChildren().add(loader.getRoot());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        });
    }

    public ObservableList<Dish> getModel(){
        return dishes;
    }

    public void clear(){
        dishes.clear();
        dishPane.getChildren().clear();
    }
}
