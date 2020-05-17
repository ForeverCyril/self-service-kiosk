package org.ebu6304gp42.component.Selectable;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.ebu6304gp42.data.DishOption;


public class OptionEditItem extends SelectableItem{
    HBox content = new HBox();
    public OptionEditItem(DishOption opt){
        super();
        content.setAlignment(Pos.CENTER_LEFT);
        setUserData(opt);
        update(opt);
        getChildren().add(content);
    }

    public void update(DishOption opt){
        setUserData(opt);
        content.getChildren().clear();

        Label name = new Label(opt.getName() + ": ");
        name.setFont(Font.font(null, FontWeight.BOLD, 14));
        content.getChildren().add(name);
        for(var option:opt.getOptions()){
            Label optLabel = new Label(
                    option.option + ((option.price>0)?String.format("(%.2f)", option.price):"")
            );
            optLabel.setFont(Font.font(14));
            content.getChildren().add(optLabel);
        }
    }

    public DishOption getDishOption(){
        return (DishOption) getUserData();
    }
}
