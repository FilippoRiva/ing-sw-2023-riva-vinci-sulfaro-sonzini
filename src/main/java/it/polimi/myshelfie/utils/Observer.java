package it.polimi.myshelfie.utils;

public interface Observer<Subject extends Observable<Event>, Event extends Enum<Event>> {
    void update(Subject o, Event evt);
}
