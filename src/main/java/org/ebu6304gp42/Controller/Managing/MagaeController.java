package org.ebu6304gp42.Controller.Managing;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Ui.EditDishDialog;
import org.ebu6304gp42.Ui.MenuTable;
import org.ebu6304gp42.Ui.Spacer;

import java.net.URL;
import java.util.ResourceBundle;


public class MagaeController implements Initializable {
    private Tab menuTab;
    private Tab dataTab;
    private Tab homeTab;

    @FXML
    private TabPane tabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //init home tab
        homeTab = new Tab("Home");
        setCloseEventForTab(homeTab);
        tabPane.getTabs().add(homeTab);

        //Init menu tab
        menuTab = new Tab("Menu");
        MenuTable meanuTable = new MenuTable();
        VBox vBox = new VBox();
        VBox.setVgrow(meanuTable, Priority.ALWAYS);
        vBox.getChildren().add(meanuTable);

        Button addBtn = new Button("Add");
        addBtn.setOnMouseClicked(e -> {
            var dish = (new EditDishDialog(null)).showAndWait();
            dish.ifPresent(meanuTable::addDish);
        });

        Button saveBtn = new Button("Save");
        saveBtn.setOnMouseClicked(e -> {
            meanuTable.save();
        });
        HBox btns = new HBox();
        btns.setSpacing(6);
        btns.getChildren().addAll(Spacer.HSpacer(), addBtn, saveBtn);
        vBox.getChildren().add(btns);
        vBox.setPadding(new Insets(6));
        vBox.setSpacing(6);
        menuTab.setContent(vBox);
        setCloseEventForTab(menuTab);

        //init data tab
        dataTab = new Tab("Data");
        setCloseEventForTab(dataTab);
    }

    @FXML
    private void switchToMenu(MouseEvent event){
        switchToTab(menuTab);
    }

    @FXML
    private void switchToData(MouseEvent event){
        switchToTab(dataTab);
    }

    private void switchToTab(Tab tab){
        if(!tabPane.getTabs().contains(tab)){
            tabPane.getTabs().add(tab);
        }
        tabPane.getSelectionModel().select(tab);
    }

    private void setCloseEventForTab(Tab tab){
        tab.setOnClosed(event -> {
            if(tabPane.getTabs().isEmpty()){
                tabPane.getTabs().add(homeTab);
                tabPane.getSelectionModel().select(homeTab);
            }
        });
    }
}
