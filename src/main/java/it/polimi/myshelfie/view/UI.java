package it.polimi.myshelfie.view;


import it.polimi.myshelfie.utils.Observer;

public interface UI extends Observer<ViewUpdate, Object> {
    void stopUI();
}
