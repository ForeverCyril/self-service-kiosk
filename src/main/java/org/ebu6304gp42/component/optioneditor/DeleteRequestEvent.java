package org.ebu6304gp42.component.optioneditor;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event send by OptionEditWidget, means the widget need delete.
 */
class DeleteRequestEvent extends Event {
    public static final EventType<DeleteRequestEvent> DELETE_REQUEST_EVENT = new EventType<>("Delete Request");
    private final OptionEditWidget source;

    public DeleteRequestEvent(EventType<? extends Event> eventType, OptionEditWidget source) {
        super(eventType);
        this.source = source;
    }

    public OptionEditWidget getSource(){return source;}
}
