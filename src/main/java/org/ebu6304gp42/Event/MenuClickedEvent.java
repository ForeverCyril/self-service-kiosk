package org.ebu6304gp42.Event;

import javafx.event.Event;
import javafx.event.EventType;
import org.ebu6304gp42.Data.Dish;

/**
 * Event which send by MenuWidget when a DishWidget Clicked
 */
public class MenuClickedEvent extends Event {
    public static final EventType<MenuClickedEvent> MENU_CLICKED_EVENT = new EventType<>("Menu Clicked");

    private Dish dish;

    public MenuClickedEvent(EventType<? extends Event> eventType, Dish dish) {
        super(eventType);
        this.dish = dish;
    }

    /**
     * Get Choose Dish
     * @return Choose Dish
     */
    public Dish getDish() {
        return dish;
    }
}
