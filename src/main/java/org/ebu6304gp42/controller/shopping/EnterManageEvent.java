package org.ebu6304gp42.controller.shopping;

import javafx.event.Event;
import javafx.event.EventType;

public class EnterManageEvent extends Event {
    public static final EventType<EnterManageEvent> ENTER_MANAGE_EVENT = new EventType<>("Enter Manage");

    public EnterManageEvent(EventType<? extends Event> eventType){
        super(eventType);
    }
}
