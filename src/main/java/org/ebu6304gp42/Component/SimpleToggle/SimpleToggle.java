package org.ebu6304gp42.Component.SimpleToggle;

import javafx.beans.property.BooleanProperty;

public interface SimpleToggle{
    public BooleanProperty selectedProperty();
    public boolean isSelected();
    public void setSelect(boolean value);
    public void setUserData(Object o);
    public Object getUserData();
}
