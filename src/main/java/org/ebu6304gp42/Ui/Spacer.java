package org.ebu6304gp42.Ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * This will make a space to fill the empty area in Box
 */
public class Spacer extends Region {
    enum Type{Vertical, Horizon}
    public Spacer(Type type){
        super();
        switch (type){
            case Horizon:
                HBox.setHgrow(this, Priority.ALWAYS);break;
            case Vertical:
                VBox.setVgrow(this, Priority.ALWAYS);break;
        }
    }

    public static Spacer HSpacer(){
        return new Spacer(Type.Horizon);
    }
    static Spacer VSpacer(){
        return new Spacer(Type.Vertical);
    }
}
