package org.ebu6304gp42.controller.shopping.menu;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.ebu6304gp42.data.Dish;


public class DishWidgetController {
    private Dish dish;

    @FXML
    private Node root;
    @FXML
    private ImageView image;
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label desc;
    @FXML
    private HBox rec_area;

    @FXML
    private void mouseClicked(MouseEvent event){
        if(dish.isAvailable()){
            root.fireEvent(new DishClickedEvent(DishClickedEvent.DISH_CLICKED_EVENT, dish));
        }
    }

    public void setDish(Dish dish){
        this.dish = dish;
        dish.setImageTo(image, true);
        name.setText(dish.getName());
        price.setText(String.format("£ %.2f", dish.getPrice()));
        desc.setText(dish.getDescription());

        var recommend_list = dish.getRecommend();
        if (recommend_list !=null){
            for(var rec:recommend_list){
                var icon = getRecommendIcon(rec);
                if (icon != null){
                    rec_area.getChildren().add(icon);
                }
            }
        }
    }

    public static ImageView getRecommendIcon(String recommend){
        switch (recommend){
            case "hot": return getHotIcon();
            case "new": return getNewIcon();
            default: return null;
        }
    }

    public static ImageView getHotIcon(){
        Image image = new Image(DishWidgetController.class.getResourceAsStream("/res/pic/hot.png"));
        return new ImageView(image);
    }
    public static ImageView getNewIcon(){
        Image image = new Image(DishWidgetController.class.getResourceAsStream("/res/pic/new.png"));
        return new ImageView(image);
    }
}
