package it.polimi.myshelfie;

import it.polimi.myshelfie.controller.ControllerImpl;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.model.ModelImpl;
import it.polimi.myshelfie.model.interfaces.Model;
import it.polimi.myshelfie.network.Server;
import it.polimi.myshelfie.network.ServerRMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ServerApp {
    public static void main (String[] args){
        ControllerImpl controller = new ControllerImpl();
        ModelImpl model = new ModelImpl();
        controller.setModel(model);
        new Thread(()->startRMI(controller)).start();
        new Thread(()->startSocket(controller,model)).start();
    }
    public static void startRMI(Controller controller){
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e){
            try {
                registry = LocateRegistry.getRegistry();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            Server serverRMI = new ServerRMI(controller);
            registry.rebind("server",serverRMI);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void startSocket(Controller controller, Model model){
        //TODO
    }
}
