package it.polimi.myshelfie.utils;


/**
 * Observer interface used for Observer/Observable pattern.
 *
 * @param <Event>     the event that has occurred.
 * @param <ModelView> the representation of the observable.
 */
public interface Observer<Event extends Enum<Event>, ModelView> {

    /**
     * Updates the model view after an event has occurred.
     *
     * @param evt       the event occurred.
     * @param modelView the model view to update.
     */
    void update(Event evt, ModelView modelView);
}
