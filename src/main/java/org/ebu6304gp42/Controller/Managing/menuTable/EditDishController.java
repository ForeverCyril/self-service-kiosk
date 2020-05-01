package org.ebu6304gp42.Controller.Managing.menuTable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.ebu6304gp42.Component.InputField.CurrencyField;
import org.ebu6304gp42.Config.PathConfig;
import org.ebu6304gp42.Data.Dish;
import org.ebu6304gp42.Component.OptionEditor;
import org.ebu6304gp42.View.ShowAlert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class EditDishController implements Initializable {
    @FXML
    private ImageView image;
    @FXML
    private TextField name;
    @FXML
    private CurrencyField price;
    @FXML
    private Spinner<Integer> remain;
    @FXML
    private ToggleGroup statusGroup;
    @FXML
    private ToggleButton enableBtn;
    @FXML
    private ToggleButton disableBtn;
    @FXML
    private TextArea desc;
    @FXML
    private VBox optionArea;
    private OptionEditor optionEditor;
    private String pic_name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Dish Picture");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")
            );
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File file = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
            if (file != null){
                System.out.println(file.toPath().toString());
                File newFile = new File(PathConfig.getPicDir() + file.getName());
                try {
                    Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(newFile.exists());
                pic_name = newFile.toPath().toString();
                Dish.setImageTo(image, newFile.toPath().toString());
            }
        });
    }

    @FXML
    private void addOption(ActionEvent event){
        optionEditor.add();
    }
    @FXML
    private void delOption(ActionEvent event){
        optionEditor.remove();
    }

    public void setDish(Dish dish){
        pic_name = dish.getPic();
        dish.setImageTo(image);
        name.setText(dish.getName());
        price.setText(String.valueOf(dish.getPrice()));
        remain.getValueFactory().setValue(dish.getRemain());
        if(dish.getStatus()){
            enableBtn.setSelected(true);
        } else {
            disableBtn.setSelected(true);
        }
        desc.setText(dish.getDescription());
        optionEditor = new OptionEditor(dish.getOptions());
        optionArea.getChildren().add(0,optionEditor);
    }
    public boolean valid(){
        if (name.getText() == null || name.getText().isBlank()){
            ShowAlert.error("Input Error", "Dish Name Can Not Be Empty");
            return false;
        }
        return true;
    }
    public Dish getDish(){
        Dish dish = new Dish();
        dish.setName(name.getText());
        dish.setPic(pic_name);
        dish.setStatus(enableBtn.isSelected());
        dish.setPrice(price.getValue()!=null?price.getValue().doubleValue():0.0);
        dish.setRemain(remain.getValue());
        dish.setDescription(desc.getText());
        dish.resetOptions(optionEditor.getOptions());
        return dish;
    }
}
