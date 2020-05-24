package org.ebu6304gp42.component.selectable;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * A Control that show a clickable option, used in {@link org.ebu6304gp42.view.DishStaticDialog}. It show data like: name: op1(num) op2(num)...
 */
public class OptionStaticItem extends SelectableItem{
    HBox content = new HBox();
    public OptionStaticItem(String name){
        super();
        getChildren().add(content);

        setUserData(name);
        Label nameLabel = new Label(name+ ": ");
        nameLabel.setFont(Font.font(null, FontWeight.BOLD, 14));
        content.getChildren().add(nameLabel);
        content.setSpacing(6);
    }


    public void addContent(String data){
        content.getChildren().add(new Label(data));
    }
}
