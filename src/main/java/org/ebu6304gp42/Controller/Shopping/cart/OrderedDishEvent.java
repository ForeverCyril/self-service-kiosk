package org.ebu6304gp42.Controller.Shopping.cart;

import javafx.event.Event;
import javafx.event.EventType;

public class OrderedDishEvent extends Event {
    public static EventType<OrderedDishEvent> ORDERED_DISH_EVENT = new EventType<>(ANY);

    public OrderedDishEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
