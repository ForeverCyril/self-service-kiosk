package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Event.MenuClickedEvent;

import java.util.ArrayList;

public class MenuWidget extends ScrollPane {

    private FlowPane flowPane;

    public MenuWidget(){
        flowPane = new FlowPane();
        //Setting for flowPane
        flowPane.setPadding(new Insets(6));
        flowPane.setVgap(18);
        flowPane.setHgap(18);
        flowPane.prefWidthProperty().bind(this.widthProperty().subtract(10));
        this.setContent(flowPane);
    }

    /**
     * Load Dished from ArrayList<Dish>
     * @param dishes The list of Dish you want to add to Menu
     */
    public void load(ArrayList<Dish> dishes){
        for(var dish:dishes){
            DishWidget dishWidget = new DishWidget(dish);
            flowPane.getChildren().add(dishWidget);
            // FireEvent When DishWidget was Clicked
            dishWidget.setOnMouseReleased(event -> {
                this.fireEvent(new MenuClickedEvent(MenuClickedEvent.MENU_CLICKED_EVENT, dish));
            });
        }
    }
}
