package it.polimi.myshelfie.view;

import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.modelviews.*;
import it.polimi.myshelfie.network.events.ViewEvent;

public class TextualUI extends View{
    // TODO this class is just an EXAMPLE.
    ViewType viewType;
    ModelView modelView;
    LobbyModelView lobbyModelView;
    GameModelView gameModelView;
    PlayerChatModelView chatModelView;
    ClientExceptionsNotifierView exceptionsView;
    boolean creating;
    boolean joining;
    String[] readStrings;
    int stringCount;
    public TextualUI(){
        clearScreen();
        creating = false;
        joining = false;
        stringCount = 0;
        readStrings = new String[10];
    }

    public Object[] getUpdateParameters(){
        return parameters;
    }

    @Override
    public synchronized void setModelView(ModelView mv) {
        this.modelView = mv;
        new Thread(()-> initPrint(false)).start();
    }

    @Override
    public synchronized void setExceptionView(ClientExceptionsNotifierView exceptionsView) {
        this.exceptionsView = exceptionsView;
        new Thread(this::printException).start();
    }

    @Override
    public synchronized void setChatView(PlayerChatModelView chatView) {
        this.chatModelView = chatView;
        new Thread(this::printChatView).start();
    }

    @Override
    public synchronized void setLobbyModelView(LobbyModelView lv) {
        this.lobbyModelView = lv;
        new Thread(()->{printLobbyView(false);}).start();
    }

    @Override
    public synchronized void setGameModelView(GameModelView obj) {
        this.gameModelView = obj;
        new Thread(this::printGameModelView).start();
    }

    @Override
    public synchronized void setView(ViewType viewType) {
        this.viewType = viewType;
        new Thread(()->{
            switch (viewType){
                case INIT -> {
                    initPrint(false);
                }
                case EXCEPTION -> {
                    exception();
                }
                case LOBBY -> {
                    lobby();
                }
                case GAME -> {
                    game();
                }
                case WIN -> {
                    win();
                }
            }
        }).start();
    }

    public void input(String input){
        ViewType vtCopy;
        ModelView modelViewCopy;
        synchronized (this){
            vtCopy = viewType;
            modelViewCopy = new ModelView(modelView);
        }
        switch (vtCopy){
            case INIT -> {
                if(creating){
                    if(stringCount==0){
                        readStrings[0] = input;
                        stringCount++;
                        createPrint(input,null);
                    } else if(stringCount==1){
                        readStrings[1] = input;
                        stringCount++;
                        createPrint(readStrings[0],input);
                    }
                    return;
                }
                if(joining){
                    if(stringCount==0){
                        int i;
                        try{
                            i = Integer.parseInt(input);
                        } catch (NumberFormatException e){
                            createPrint(null,null);
                            return;
                        }
                        readStrings[0] = modelViewCopy.getLobbies().get(i-1);
                        stringCount++;
                        joinPrint(input,null);
                    } else if(stringCount==1){
                        readStrings[1] = input;
                        stringCount++;
                        joinPrint(readStrings[0],input);
                    }
                    return;
                }
                int in;
                try{
                    in = Integer.parseInt(input);
                } catch (NumberFormatException e){
                    initPrint(true);                                         ;
                    return;
                }
                if(modelView.getLobbies().size()==0) {
                    if(in == 1){
                        creating = true;
                        stringCount = 0;
                        createPrint(null,null);
                    } else {
                        initPrint(true);
                    }
                }
                else{
                    if (in == 1){
                        joining = true;
                        stringCount = 0;
                        joinPrint(null,null);
                    } else if(in == 2){
                        creating = true;
                        stringCount = 0;
                        createPrint(null,null);
                    } else{
                        initPrint(false);
                    }
                }
            }
            case LOBBY -> {
                if(input.equals("+")){
                    addPlayer();
                } else if(input.equals("-")){
                    removePlayer();
                } else {
                    printLobbyView(false);
                }
            }
        }
    }

    private void removePlayer() {

    }

    private void addPlayer() {

    }

    private void initPrint(boolean fail){
        if(viewType!= ViewType.INIT) return;
        clearScreen();
        print("My Shelfie");
        boolean ok = false;
        if(fail)printError("insert a valid option");
        if(modelView.getLobbies().size()!=0) {
            print("1. join");
            print("2. create");
        } else {
            print("1. create");
        }
    }

    private void exception() {
        clearScreen();
        printException();
    }

    private void lobby() {
        clearScreen();
        printLobbyView(false);
    }

    private void game() {
        clearScreen();
        printGameModelView();
    }


    private void win() {
        clearScreen();
        printWin();
    }

    public void joinPrint(String chosenLobby, String username){
        clearScreen();
        if(chosenLobby!= null && username!= null){
            print("joining "+chosenLobby+ "as "+username);
            joinRequest(chosenLobby,username);
            return;
        }
        if(chosenLobby==null){
            print("Choose a lobby number"+" :");
            printLobbies();
        } else {
            print("Choose an username for "+chosenLobby+" :");
        }
    }

    private void joinRequest(String lobby, String playerName){
        parameters = new Object[]{playerName,lobby};
        setChanged();
        notifyObservers(ViewEvent.JOIN_REQUEST);
    }

    public void createPrint(String lobby, String playerName){
        clearScreen();
        if(lobby!= null && playerName!= null){
            print("creating "+lobby+ "as "+playerName);
            createRequest(lobby,playerName);
            return;
        }
        if(lobby==null){
            print("Choose a lobby name :");
        } else {
            print("Choose an username for joining "+lobby+" :");
        }
    }

    private void createRequest(String lobby, String playerName){
        parameters = new Object[]{playerName,lobby};
        setChanged();
        notifyObservers(ViewEvent.CREATE_REQUEST);
    }

    public void printLobbies(){
        if(viewType!= ViewType.INIT || modelView==null ) return;
        printLine(10);
        print("Lobbies : ");
        if(modelView.getLobbies().size()==0){
            print("none");
            return;
        }
        int idx = 1;
        for(String lobby : modelView.getLobbies()){
            print(idx+". "+lobby);
            idx++;
        }
    }

    public void printException() {
        if(viewType!= ViewType.EXCEPTION || exceptionsView==null) return;
        clearScreen();
        print(exceptionsView.getLastException().getMessage());
    }
    private void printChatView(){
        if(chatModelView==null) return;
        printMessage(chatModelView.getLastMessage());
    }

    private void printMessage(Message lastMessage) {
        System.out.println(lastMessage.getSender()+" : "+lastMessage.getMessage());
    }

    private void printLobbyView(boolean wrongInput) {
        if(viewType!= ViewType.LOBBY || lobbyModelView==null) return;
        clearScreen();
        print("Name : "+lobbyModelView.getName());
        print("Owner : "+lobbyModelView.getRoomOwner());
        int idx = 1;
        for(String player : lobbyModelView.getPlayers()){
            print(idx+". "+player);
            idx++;
        }
        print("Max players : "+lobbyModelView.getGameConfig().getNumPlayers());
    }

    private void printGameModelView() {
        if(viewType != ViewType.GAME || gameModelView==null) return;
        clearScreen();
        printLivingRoom();
    }
    private void printLivingRoom() {
        ItemTile[][] livingRoom = gameModelView.getLivingRoom();
        printLine(livingRoom.length);
        for(int y = livingRoom.length-1; y >= 0; y--){
            for(int x = 0; x<livingRoom.length; x++){
                printTile(livingRoom[x][y]);
            }
            System.out.print("|\n");
            printLine(livingRoom.length);
        }
    }
    private void printLine(int length){
        for(int i = 0; i<length; i++) System.out.print("-");
        System.out.print("\n");
    }
    private void printTile(ItemTile tile){
        if(tile == null){
            System.out.println("|  ");
        } else {
            System.out.println("|"+tile.getType().name().charAt(0));
        }
    }
    private void printWin() {
    }


    private void print(String message){
        System.out.println("--- "+message);
    }

    private void printError(String message) {
        System.out.println("! "+message);
    }

    private void clearScreen(){
        //Used just for IDE development(cause intellij doesn't support ansi escape sequences
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        //ansi escape sequence for clearing terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}