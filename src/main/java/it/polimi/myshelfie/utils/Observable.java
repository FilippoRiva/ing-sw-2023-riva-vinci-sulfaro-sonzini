package it.polimi.myshelfie.utils;

import java.util.Vector;

public class Observable<Event extends Enum<Event>>{
    private boolean changed = false;
    // Vector, unlike Arraylists, are synchronized
    private final Vector<Observer<? extends Observable<Event>,Event>> obs;
    public Observable() {
        obs = new Vector<>();
    }
    public synchronized void addObserver(Observer<? extends Observable<Event>,Event> o){
        if(o == null) throw new NullPointerException();
        if(!obs.contains(o)) obs.addElement(o);
    }

    public synchronized void deleteObserver(Observer<? extends Observable<Event>,Event> o) {
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
            ((Observer<Observable<Event>,Event>) o).update(this,evt);
        }
    }

    public synchronized void deleteObservers() { obs.removeAllElements();}
    public synchronized void setChanged() { changed = true;}
    public synchronized void clearChanged() { changed = false;}
    public synchronized boolean hasChanged() { return changed;}
}
