package org.ebu6304gp42.component.simpleToggle;

import javafx.beans.property.BooleanProperty;

/**
 * Simple Toggle Interface. Use with {@link SimpleToggleGroup}
 */
public interface SimpleToggle{
    /**
     * Get selected property
     * @return selected property
     */
    public BooleanProperty selectedProperty();

    /**
     * Get selected status
     * @return selected status
     */
    public boolean isSelected();

    /**
     * Set Selected Status
     * @param value status
     */
    public void setSelect(boolean value);

    /**
     * Set User Data
     * @param o user data
     */
    public void setUserData(Object o);

    /**
     * Get User Data
     * @return user data
     */
    public Object getUserData();
}
