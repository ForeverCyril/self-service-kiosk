package org.ebu6304gp42.controller.managing;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.ebu6304gp42.controller.managing.menuTable.MenuTableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MagaeController implements Initializable {
    private Tab menuTab;
    private Tab dataTab;
    private Tab homeTab;
    private Tab accTab;

    @FXML
    private TabPane tabPane;

    private MenuTableController menuTableController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //init home tab
        homeTab = new Tab("Home");
        var l = new Label("Welcome!");
        l.setFont(Font.font(null, FontWeight.BOLD, 20));
        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.getChildren().addAll(l);
        homeTab.setContent(v);
        setCloseEventForTab(homeTab);
        tabPane.getTabs().add(homeTab);

        //Init menu tab
        menuTab = new Tab("Menu");
        FXMLLoader menuTableLoader = loadFXML("/fxml/manage/MenuTable.fxml");
        menuTableController = menuTableLoader.getController();
        menuTab.setContent(menuTableLoader.getRoot());

        //init data tab
        dataTab = new Tab("Data");
        setCloseEventForTab(dataTab);
        var dataLoader = loadFXML("/fxml/manage/Data.fxml");
        dataTab.setContent(dataLoader.getRoot());

        // init account tab;
        accTab = new Tab("Account");
        setCloseEventForTab(accTab);
        var accTableLoader = loadFXML("/fxml/manage/AccountTable.fxml");
        accTab.setContent(accTableLoader.getRoot());
    }

    @FXML
    private void switchToMenu(MouseEvent event){
        switchToTab(menuTab);
    }

    @FXML
    private void switchToData(MouseEvent event){
        switchToTab(dataTab);
    }

    @FXML
    private void switchToAcc(MouseEvent event) {switchToTab(accTab);}

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

    private FXMLLoader loadFXML(String file){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        return loader;
    }

    public boolean dataChanged(){
        return menuTableController.changed();
    }
    public void saveMenu(){
        menuTableController.save();
    }
}
