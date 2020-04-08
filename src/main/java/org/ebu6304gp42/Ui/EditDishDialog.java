package org.ebu6304gp42.Ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Data.Dish;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditDishDialog extends Dialog<Dish> {
    public EditDishDialog(Dish dish){
        setTitle("Add/Modify the dish");
        setHeaderText(null);

        if(dish == null){
            dish = new Dish();
        }

        HBox root = new HBox();
        //Left Area

        //Dish Image
        Image image;
        try {
            if(dish.getPic() == null || dish.getPic().isBlank()){
                throw new FileNotFoundException();
            }
            image = new Image(new FileInputStream(dish.getPic()));
        } catch (IOException e){
            //if no pic found or other IOException, use default pic
            System.err.println(String.format("No picture found for %s(pic file:%s) , use default image.", dish.getName(), dish.getPic()));
            image = new Image(getClass().getResourceAsStream(PathConfig.getDefaultPic()));
        }
        ImageView dish_image = new ImageView(image);
        dish_image.preserveRatioProperty().setValue(true);
        // Basic Information
        TextField nameInput = new TextField(dish.getName());
        TextField priceInput = new TextField(String.valueOf(dish.getPrice()));
        TextField remainInput = new TextField(String.valueOf(dish.getRemain()));
        ToggleGroup statusGroup = new ToggleGroup();
        ToggleButton status_enable = new ToggleButton("enable");
        status_enable.setUserData(true);
        status_enable.setToggleGroup(statusGroup);
        ToggleButton status_disable = new ToggleButton("disable");
        status_disable.setUserData(false);
        status_disable.setToggleGroup(statusGroup);
        HBox statusInput = new HBox();
        if(dish.getStatus()){
            status_enable.setSelected(true);
        } else {
            status_disable.setSelected(true);
        }
        statusInput.getChildren().addAll(status_enable,status_disable);
        // Basic Information Layout
        GridPane basic_info = new GridPane();
        basic_info.setVgap(6);
        basic_info.setHgap(8);
        basic_info.addRow(0, new Label("Name:"), nameInput);
        basic_info.addRow(1, new Label("Price:"), priceInput);
        basic_info.addRow(2, new Label("Remain:"), remainInput);
        basic_info.addRow(3, new Label("Status:"), statusInput);

        VBox leftArea = new VBox();
        leftArea.setSpacing(16);
        leftArea.setPadding(new Insets(6));
        leftArea.getChildren().addAll(dish_image, basic_info);

        dish_image.fitWidthProperty().bind(leftArea.widthProperty().subtract(12));

        //Right Area
        VBox rightArea = new VBox();
        rightArea.prefWidthProperty().setValue(250);
        TextArea description = new TextArea(dish.getDescription());

        rightArea.getChildren().add(description);


        root.getChildren().addAll(leftArea, Spacer.HSpacer(), rightArea);
        getDialogPane().setContent(root);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(btn->{
            if(btn == ButtonType.CANCEL){
                return null;
            }
            Dish finalDish = new Dish();
            finalDish.setName(nameInput.getText());
            finalDish.setPrice(Double.parseDouble(priceInput.getText()));
            finalDish.setRemain(Integer.parseInt(remainInput.getText()));
            finalDish.setStatus((Boolean) statusGroup.getSelectedToggle().getUserData());
            finalDish.setDescription(description.getText());
            return finalDish;
        });
    }
}
