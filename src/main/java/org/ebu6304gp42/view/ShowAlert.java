package org.ebu6304gp42.view;

import javafx.scene.control.Alert;

public class ShowAlert {
    static public void error(String title, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
