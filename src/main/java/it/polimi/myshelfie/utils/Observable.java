package it.polimi.myshelfie.utils;

import java.util.ArrayList;
import java.util.Vector;


/**
 * Observable Class used for Observer/Observable pattern.
 * It has a boolean attribute "changed" to indicate when changes have occurred, and a vector of observers.
 *
 * @param <Event>          the events' Enum used to represent events.
 * @param <Representation> the representation used for the observable.
 */
public class Observable<Event extends Enum<Event>, Representation> {
    // Vector, unlike Arraylists, are synchronized
    private final Vector<Observer<Event, Representation>> obs;
    private boolean changed = false;

    /**
     * Constructor.
     */
    public Observable() {
        obs = new Vector<>();
    }

    /**
     * Adds an Observer to the vector obs.
     *
     * @param o the observer to add.
     */
    public synchronized void addObserver(Observer<Event, Representation> o) {
        if (o == null) throw new NullPointerException();
        if (!obs.contains(o)) obs.addElement(o);
    }


    /**
     * Removes the Observer form the vector obs.
     *
     * @param o the observer to delete.
     */
    public synchronized void deleteObserver(Observer<Event, Representation> o) {
        obs.remove(o);
    }

    /**
     * Notifies an event to the observers.
     *
     * @param representation the rep of the observable.
     */
    public void notifyObservers(Representation representation) {
        notifyObservers(null, representation);
    }

    /**
     * Notifies an event.
     *
     * @param event          the event that occurred.
     * @param representation the rep of the observable.
     */
    public void notifyObservers(Event event, Representation representation) {
        Object[] observersCopy;
        synchronized (this) {
            if (!changed) return;
            observersCopy = obs.toArray();
            clearChanged();
        }
        for (Object o : observersCopy) {
            ((Observer<Event, Representation>) o).update(event, representation);
        }
    }

    /**
     * Returns the list of observers.
     * @return the list of observers.
     */
    public synchronized ArrayList<Observer<Event, Representation>> getObservers() {
        return new ArrayList<>(obs);
    }

    /**
     * Removes all the observers in the vector obs.
     */
    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    /**
     * Sets the attribute "changed" to true.
     */
    public synchronized void setChanged() {
        changed = true;
    }

    /**
     * Sets the attribute "changed" to false.
     */
    public synchronized void clearChanged() {
        changed = false;
    }
}
