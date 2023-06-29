package it.polimi.myshelfie.view;


import it.polimi.myshelfie.utils.Observer;


/**
 * Represents the User Interface.
 */
public interface UI extends Observer<ViewUpdate, Object> {

    /**
     * Starts the UI.
     */
    void startUI();

    /**
     * Stops the UI
     */
    void stopUI();
}
