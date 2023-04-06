package it.polimi.myshelfie.utils;

import java.util.Vector;

public class Observable<Event extends Enum<Event>>{
    private boolean changed = false;
    // Vector, unlike Arraylists, are synchronized
    private final Vector<Observer<Event>> obs;
    public Observable() {
        obs = new Vector<>();
    }
    public synchronized void addObserver(Observer<Event> o){
        if(o == null) throw new NullPointerException();
        if(!obs.contains(o)) obs.addElement(o);
    }

    public synchronized void deleteObserver(Observer<Event> o) {
        obs.remove(o);
    }

    public void notifyObservers(){
        notifyObservers(null);
    }

    public void notifyObservers(Event evt){
        Object[] observersCopy;
        synchronized (this){
            if(!changed) return;
            observersCopy = obs.toArray();
            clearChanged();
        }
        for(Object o : observersCopy){
            ((Observer<Event>) o).update(evt);
        }
    }

    public synchronized void deleteObservers() { obs.removeAllElements();}
    public synchronized void setChanged() { changed = true;}
    public synchronized void clearChanged() { changed = false;}
    public synchronized boolean hasChanged() { return changed;}
}
