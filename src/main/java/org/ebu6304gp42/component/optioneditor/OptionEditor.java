package org.ebu6304gp42.component.optioneditor;

import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.ebu6304gp42.component.selectable.OptionEditItem;
import org.ebu6304gp42.component.selectable.SelectableItem;
import org.ebu6304gp42.component.simpleToggle.SimpleToggle;
import org.ebu6304gp42.component.simpleToggle.SimpleToggleGroup;
import org.ebu6304gp42.data.DishOption;

import java.util.ArrayList;

/**
 * An option editor widget which can edit the dish option
 */
public class OptionEditor extends ScrollPane {
    VBox root = new VBox();
    SimpleToggleGroup group = new SimpleToggleGroup();

    public OptionEditor(ArrayList<DishOption> options){
        setContent(root);
        VBox.setVgrow(this, Priority.ALWAYS);
        root.setSpacing(6);
        for(var opt:options){
            var item = new OptionEditItem(opt);
            item.setToggleGroup(group);
            root.getChildren().add(item);
            item.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    var res = (new OptionEditDialog(item.getDishOption())).showAndWait();
                    res.ifPresent(item::update);
                }
            });
        }
        for(var item:root.getChildren()){
            System.out.println(item);
        }
    }

    /**
     * Get the options
     * @return dish options
     */
    public ArrayList<DishOption> getOptions(){
        ArrayList<DishOption> list = new ArrayList<>();
        for(var node:root.getChildren()){
            DishOption option = ((OptionEditItem)node).getDishOption();
            list.add(option);
        }
        return list;
    }

    /**
     * Show a dialog to add a new dish.
     */
    public void add(){
        var res = (new OptionEditDialog()).showAndWait();
        res.ifPresent(dishOption -> {
            OptionEditItem item = new OptionEditItem(dishOption);
            item.setToggleGroup(group);
            root.getChildren().add(item);
        });
    }

    /**
     * remove the selected option
     */
    public void remove(){
        SimpleToggle selected_item = group.getSelectedToggle();
        if(selected_item!=null){
            root.getChildren().remove((SelectableItem)selected_item);
        }
    }
}
