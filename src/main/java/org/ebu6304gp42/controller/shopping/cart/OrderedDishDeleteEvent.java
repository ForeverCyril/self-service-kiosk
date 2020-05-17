package org.ebu6304gp42.controller.shopping.cart;

import javafx.event.Event;
import javafx.event.EventType;
import org.ebu6304gp42.data.OrderedDish;

public class OrderedDishDeleteEvent extends OrderedDishEvent{
    public static final EventType<OrderedDishDeleteEvent> DISH_DELETE_EVENT = new EventType<>(
            OrderedDishEvent.ORDERED_DISH_EVENT
            , "Delete Request");

    private OrderedDish orderedDish;
    public OrderedDishDeleteEvent(EventType<? extends Event> eventType, OrderedDish orderedDish) {
        super(eventType);
        this.orderedDish = orderedDish;
    }

    public OrderedDish getOrderedDish(){
        return orderedDish;
    }
}
