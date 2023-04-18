package it.polimi.myshelfie;

import it.polimi.myshelfie.network.*;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.textual.CLIInputReader;
import it.polimi.myshelfie.view.textual.TextualUI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientApp {
    static String host = "localhost";
    static int registryPort = 1099;
    public static void main(String[] args){
        ViewBuffer viewBuffer = new ViewBuffer();
        TextualUI view = new TextualUI(viewBuffer);
        viewBuffer.addUI(view);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        new Thread(()->new CLIInputReader(in,view,viewBuffer)).start();
        try {
            startRMIConnection(viewBuffer);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void startRMIConnection(ViewBuffer viewBuffer) throws RemoteException, NotBoundException {
        Registry registry;
        registry = LocateRegistry.getRegistry(host,registryPort);
        Server server = (Server) registry.lookup("server");
        server.connect(new ClientRMI(viewBuffer));
    }
}
