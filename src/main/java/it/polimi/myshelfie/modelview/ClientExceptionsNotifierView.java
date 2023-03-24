package it.polimi.myshelfie.modelview;


import it.polimi.myshelfie.model.interfaces.ClientExceptionsNotifier;

public class ClientExceptionsNotifierView {
    //TODO Observer/Listener implementation
    ClientExceptionsNotifier cen;
    public ClientExceptionsNotifierView(ClientExceptionsNotifier cen){
       this.cen = cen;
    }
}
