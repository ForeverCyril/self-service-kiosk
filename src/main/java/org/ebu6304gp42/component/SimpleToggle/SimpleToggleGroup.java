package org.ebu6304gp42.component.SimpleToggle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public void setSelectedToggle(SimpleToggle selected) {
        this.selected.set(selected);
    }
    public void clearSelectedToggle(){this.selected.setValue(null);}
    public void clearSelectedToggle(SimpleToggle toggle){
        if(selected.get() == toggle){
            clearSelectedToggle();
        }
    }
    public SimpleToggle getSelectedToggle(){
        return selected.get();
    }
    public ObjectProperty<SimpleToggle> selectedToggleObjectProperty(){return selected;}


}
