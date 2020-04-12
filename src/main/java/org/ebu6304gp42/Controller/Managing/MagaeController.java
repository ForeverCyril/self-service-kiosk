package org.ebu6304gp42.Controller.Managing;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
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
        FXMLLoader menuTableLoader = new FXMLLoader(getClass().getResource("/FXML/MenuTable.fxml"));
        try {
            menuTableLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        menuTab.setContent(menuTableLoader.getRoot());

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
