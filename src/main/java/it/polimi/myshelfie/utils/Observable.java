package it.polimi.myshelfie.utils;

import java.util.Vector;

public class Observable<Event extends Enum<Event>, Representation>{
    private boolean changed = false;
    // Vector, unlike Arraylists, are synchronized
    private final Vector<Observer<Event, Representation>> obs;
    public Observable() {
        obs = new Vector<>();
    }
    public synchronized void addObserver(Observer<Event, Representation> o){
        if(o == null) throw new NullPointerException();
        if(!obs.contains(o)) obs.addElement(o);
    }

    public synchronized void deleteObserver(Observer<Event, Representation> o) {
        obs.remove(o);
    }

    public void notifyObservers(){
        notifyObservers(null, null);
    }

    public void notifyObservers(Event event, Representation representation){
        Object[] observersCopy;
        synchronized (this){
            if(!changed) return;
            observersCopy = obs.toArray();
            clearChanged();
        }
        for(Object o : observersCopy){
            ((Observer<Event, Representation>) o).update(event, representation);
        }
    }

    public synchronized void deleteObservers() { obs.removeAllElements();}
    public synchronized void setChanged() { changed = true;}
    public synchronized void clearChanged() { changed = false;}
    public synchronized boolean hasChanged() { return changed;}
}
