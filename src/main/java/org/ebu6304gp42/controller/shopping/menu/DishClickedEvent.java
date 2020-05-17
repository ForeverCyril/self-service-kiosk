package org.ebu6304gp42.controller.shopping.menu;

import javafx.event.Event;
import javafx.event.EventType;
import org.ebu6304gp42.data.Dish;

public class DishClickedEvent extends Event {
    public static final EventType<DishClickedEvent> DISH_CLICKED_EVENT = new EventType<>("Dish Clicked");

    private Dish dish;

    public DishClickedEvent(EventType<? extends Event> eventType, Dish dish) {
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
