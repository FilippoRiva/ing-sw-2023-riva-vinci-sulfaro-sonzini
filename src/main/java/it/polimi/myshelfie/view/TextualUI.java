package it.polimi.myshelfie.view;

import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.events.ViewEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextualUI extends View{
    //TODO use select view method instead of forcing the view flow via methods calls
    ModelView modelView;
    LobbyModelView lobbyModelView;
    GameModelView gameModelView;
    PlayerChatModelView chatModelView;
    ClientExceptionsNotifierView exceptionsView;
    BufferedReader in;
    public TextualUI(){
        new Thread(()->{
            try {
                inputRead();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void inputRead() throws IOException {
        print("Welcome to My Shelfie");
        in = new BufferedReader(new InputStreamReader(System.in));
        print("join|create lobby");
        boolean ok = false;
        while(!ok) {
            String input = getInput();
            switch (input) {
                case "join" -> join();
                case "create" -> create();
                default -> printError("insert valid choice");
            }
        }
    }

    private void printError(String message) {
        System.out.println("! "+message);
    }

    private String getInput() throws IOException {
        System.out.print("> ");
        return in.readLine();
    }

    private void print(String message){
        System.out.println("--- "+message);
    }

    public void printModelView(){
        int idx = 1;
        for(String lobby : modelView.getLobbies()){
            print(idx+". "+lobby);
            idx++;
        }
    }

    public Object[] getUpdateParameters(){
        return parameters;
    }

    @Override
    public void setModelView(ModelView mv) {
        this.modelView = mv;
        printModelView();
    }

    @Override
    public void setLobbyModelView(LobbyModelView lv) {
        this.lobbyModelView = lv;
        printLobbyView();
    }

    @Override
    public void setView(ViewType viewType) {

    }

    private void printLobbyView() {
        print("Name : "+lobbyModelView.getName());
        print("Owner : "+lobbyModelView.getRoomOwner());
        int idx = 1;
        for(String player : lobbyModelView.getPlayers()){
            print(idx+". "+player);
            idx++;
        }
        print("Max players : "+lobbyModelView.getGameConfig().getNumPlayers());
    }

    public void join() throws IOException {
        printModelView();
        print("Choose a lobby number");
        int lobbyNumber = Integer.parseInt(getInput());
        String lobbyName = modelView.getLobbies().get(lobbyNumber-1);
        print("Set username");
        String username = getInput();
        parameters = new Object[]{username,lobbyName};
        setChanged();
        notifyObservers(ViewEvent.JOIN_REQUEST);
    }
    public void create() throws IOException {
        print("Set new lobby name");
        String lobbyName = getInput();
        print("Set player name");
        String name = getInput();
        parameters = new Object[]{name,lobbyName};
        setChanged();
        notifyObservers(ViewEvent.CREATE_REQUEST);
    }
}