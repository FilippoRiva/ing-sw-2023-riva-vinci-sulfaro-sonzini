package it.polimi.myshelfie.utils;

public interface Observer<Event extends Enum<Event>> {
    void update(Event evt);
}
