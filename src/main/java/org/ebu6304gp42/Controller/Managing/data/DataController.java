package org.ebu6304gp42.Controller.Managing.data;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.Data.OrderManager;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DataController implements Initializable {
    @FXML
    private StackPane chart_area;
    @FXML
    private VBox orderTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OrderManager.getInstance().load();
        loadFXML(chart_area, "/fxml/Manage/TimeChart.fxml");
        loadFXML(chart_area, "/fxml/Manage/EatWayChart.fxml");
        loadFXML(orderTable, "/fxml/Manage/OrderTable.fxml");

        for(var node:chart_area.getChildren()){
            node.setVisible(false);
        }
        //show the last one (index=0)
        chart_area.getChildren().get(0).setVisible(true);
        chart_area.setOnMouseClicked(event -> {
            // show next when click
            var children = chart_area.getChildren();
            children.get(0).setVisible(false);
            children.get(0).toFront();
            children.get(0).setVisible(true);
        });
    }

    private void loadFXML(Node root, String file){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        loader.setRoot(root);
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
