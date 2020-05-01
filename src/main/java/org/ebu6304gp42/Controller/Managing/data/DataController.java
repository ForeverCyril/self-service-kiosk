package org.ebu6304gp42.Controller.Managing.data;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Data.OrderManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DataController implements Initializable {
    @FXML
    private VBox timeChart;
    @FXML
    private VBox methodChart;
    @FXML
    private VBox orderTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OrderManager.getInstance().load();
        loadFXML(timeChart, "/fxml/Manage/TimeChart.fxml");
        loadFXML(methodChart, "/fxml/Manage/EatWayChart.fxml");
        loadFXML(orderTable, "/fxml/Manage/OrderTable.fxml");
    }

    private FXMLLoader loadFXML(Node root, String file){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        loader.setRoot(root);
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        return loader;
    }
}
