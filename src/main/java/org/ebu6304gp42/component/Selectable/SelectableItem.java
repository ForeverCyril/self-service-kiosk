package org.ebu6304gp42.component.Selectable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.ebu6304gp42.component.SimpleToggle.SimpleToggle;
import org.ebu6304gp42.component.SimpleToggle.SimpleToggleGroup;

public class SelectableItem extends Pane implements SimpleToggle{
    private static final PseudoClass PSEUDO_CLASS_SELECTED =
            PseudoClass.getPseudoClass("selected");

    BooleanProperty selected = new SimpleBooleanProperty(false);
    Object userData;

    public SelectableItem(){
        getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        getStyleClass().add("selectable-item");
        setPrefWidth(USE_COMPUTED_SIZE);
        setPrefHeight(USE_COMPUTED_SIZE);
        selected.addListener((observable, oldValue, newValue) -> {
            if(newValue == oldValue){
                return;
            }
            if(newValue){
                getToggleGroup().setSelectedToggle(this);
            } else {
                getToggleGroup().clearSelectedToggle(this);
            }
            pseudoClassStateChanged(PSEUDO_CLASS_SELECTED, newValue);
        });
        addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            setSelect(!isSelected());
        });
    }

    @Override
    public BooleanProperty selectedProperty() {
        return selected;
    }

    @Override
    public boolean isSelected() {
        return selected.get();
    }

    @Override
    public void setSelect(boolean value) {
        selected.setValue(value);
    }
    @Override
    public void setUserData(Object data){
        userData = data;
    }
    @Override
    public Object getUserData(){
        return userData;
    }

    private SimpleToggleGroup group;
    public void setToggleGroup(SimpleToggleGroup group){
        this.group = group;
    }
    public SimpleToggleGroup getToggleGroup(){
        return group;
    }
}
