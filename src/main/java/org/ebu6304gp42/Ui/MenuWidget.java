package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Event.MenuClickedEvent;

import java.util.ArrayList;

public class MenuWidget extends ScrollPane {

    private FlowPane flowPane;

    public MenuWidget(){
        //disable horizon scrollbar
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setPadding(new Insets(6));
        flowPane = new FlowPane();
        //Setting for flowPane
        flowPane.setVgap(16);
        flowPane.setHgap(16);
        // bind the flowpane width to scrollPane
        flowPane.prefWidthProperty().bind(this.widthProperty());
        this.setContent(flowPane);
    }

    /**
     * Load Dished
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
