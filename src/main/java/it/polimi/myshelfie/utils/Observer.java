package it.polimi.myshelfie.utils;

public interface Observer<Event extends Enum<Event>, ModelView> {
    void update(Event evt, ModelView modelView);
}
