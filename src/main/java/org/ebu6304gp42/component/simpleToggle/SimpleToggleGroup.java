package org.ebu6304gp42.component.simpleToggle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Simple ToggleGroup, use with {@link SimpleToggle}
 */
public class SimpleToggleGroup {
    ObservableList<SimpleToggle> toggles = FXCollections.observableArrayList();
    ObjectProperty<SimpleToggle> selected = new SimpleObjectProperty<>(null);

    public SimpleToggleGroup(){
        selected.addListener((observable, oldValue, newValue) -> {
            if(newValue == oldValue){
                return;
            }
            if(oldValue != null){
                oldValue.setSelect(false);
            }
            if(newValue != null){
                newValue.setSelect(true);
            }
        });
    }

    /**
     * Selected a {@link SimpleToggle}
     * @param simpleToggle Toggle Need To Select
     */
    public void setSelectedToggle(SimpleToggle simpleToggle) {
        this.selected.set(simpleToggle);
    }

    /**
     * Unselected selected toggle
     */
    public void clearSelectedToggle(){this.selected.setValue(null);}

    /**
     * Unselected toggle, if it's selected
     * @param toggle toggle need unselected
     */
    public void clearSelectedToggle(SimpleToggle toggle){
        if(selected.get() == toggle){
            clearSelectedToggle();
        }
    }

    /**
     * Get Selected Toggle
     * @return Selected toggle
     */
    public SimpleToggle getSelectedToggle(){
        return selected.get();
    }

    /**
     * Get SelectedToggle Property;
     * @return selected toggle property
     */
    public ObjectProperty<SimpleToggle> selectedToggleObjectProperty(){return selected;}


}
