package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Event.MenuClickedEvent;

import java.io.*;
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

    /**
     * Widget To Display a Dish
     */
    static class DishWidget extends HBox {
        final static InputStream default_pic = DishWidget.class.getResourceAsStream(PathConfig.getDefaultPic());
        final static double HEIGHT = 120;

        private Dish dish;

        public DishWidget(Dish dish){
            this.dish = dish;
            ImageView image_view;

            this.setHeight(HEIGHT);
            this.setPrefWidth(HEIGHT*2.5);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

            // Create Image for Dish
            Image image;
            try {
                if(dish.getPic() == null || dish.getPic().isBlank()){
                    throw new FileNotFoundException();
                }
                image = new Image(new FileInputStream(dish.getPic()));
            } catch (IOException e){
                //if no pic found or other IOException, use default pic
                System.err.println(String.format("No picture found for %s(pic file:%s) , use default image.", dish.getName(), dish.getPic()));
                image = new Image(default_pic);
            }
            image_view = new ImageView(image);
            image_view.preserveRatioProperty().setValue(false);
            // Bind the height and width of image to the Height of Dishwidget
            image_view.fitWidthProperty().bind(this.heightProperty());
            image_view.fitHeightProperty().bind(this.heightProperty());

            // Label Display Dish Name
            Label name = new Label(dish.getName());
            name.setFont(Font.font(null, FontWeight.BOLD, 14));
            name.setTextFill(Color.BLACK);

            // Label Display Dish Price
            Label price = new Label(String.format("%.2f $", dish.getPrice()));
            price.setFont(Font.font(null,FontWeight.BOLD, 14));
            price.setTextFill(Color.RED);

            // Label Display Dish Description
            Label description = new Label(dish.getDescription());
            description.setTextFill(Color.GRAY);
            description.setFont(Font.font(null,11));
            description.setMaxHeight(HEIGHT);
            description.setAlignment(Pos.TOP_LEFT);
            description.setWrapText(true); // Make it auto fill empty space of imformation_box

            // A layout contain the information of dish
            VBox imformation_box = new VBox();
            imformation_box.prefHeightProperty().bind(this.heightProperty());
            imformation_box.setFillWidth(true);
            imformation_box.setPadding(new Insets(5));
            imformation_box.setSpacing(8);
            imformation_box.setMaxWidth(HEIGHT*1.6);
            imformation_box.getChildren().addAll(name,price,description);

            // Add image and imformation_box
            this.getChildren().addAll(image_view, imformation_box);

            //Set Drop Shadow and it effect when mouse entering
            DropShadow shadow = new DropShadow();
            shadow.setBlurType(BlurType.GAUSSIAN);
            shadow.setColor(Color.GRAY);
            shadow.setSpread(0.1);
            shadow.setOffsetX(2);
            shadow.setOffsetY(2);
            this.setEffect(shadow);
            this.setOnMouseEntered(event -> {shadow.setSpread(0.5);});
            this.setOnMouseExited(event -> {shadow.setSpread(0.1);});
        }

        public Dish getDish() {
            return dish;
        }
    }
}


