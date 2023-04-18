package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.events.ViewEvent;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewType;

import java.util.ArrayList;
import java.util.List;

public class CLIInputInterpreter {
    TextualUI UI;
    final ViewBuffer viewBuffer;
    String[] readStrings;
    int stringCount;
    boolean creating;
    boolean joining;
    boolean inputError;

    /**
     * creates a CLIInputInterpreter that given an input string it interprets it, updates the view, and tells the viewBuffer
     * to notify its observers.
     * how a string is interpreted depends on how the UI and the ViewBuffer are set
     * @param UI the UI this interpreter should look at to interpret user input
     * @param viewBuffer the ViewBuffer this interpreter should use to interpret user input and notify new ViewEvents
     *                   like JOIN_REQUEST, CREATE_REQUEST, CONFIG_REQUEST, ... setting the appropriate parameters of
     *                   the ViewBuffer
     */
    public CLIInputInterpreter(TextualUI UI, ViewBuffer viewBuffer){
        this.UI = UI;
        this.viewBuffer = viewBuffer;
        this.readStrings =  new String[10];
        this.stringCount = 0;
    }
    public void interpret(String input){
        if(inputError){
            UI.clearError();
            inputError = false;
        }
        ViewType vtCopy;
        ModelView modelView;
        LobbyModelView lobbyModelView;
        synchronized (this){
            vtCopy = viewBuffer.getViewType();
            modelView= viewBuffer.getModelView();
            lobbyModelView = viewBuffer.getLobbyModelView();
        }
        switch (vtCopy){
            case INIT -> {
                if(creating){
                    interpretCreate(input);
                } else if(joining){
                    interpretJoin(input, modelView);
                } else {
                    interpretInit(input);
                }
            }
            case LOBBY -> {
                if(input.equals("+")){
                    if(!addPlayer()) {
                        inputError=true;
                        UI.wrongInput();
                    }
                    else UI.printLobbyView();
                } else if(input.equals("-")) {
                    if (!removePlayer()) {
                        inputError = true;
                        UI.wrongInput();
                    } else UI.printLobbyView();
                } else if (input.equals("s")) {
                    if(lobbyModelView.getPlayers().size()==lobbyModelView.getGameConfig().getNumPlayers()){
                        startRequest();
                    } else {
                        inputError = true;
                        UI.wrongInput();
                    }
                }
                else {
                    inputError=true;
                    UI.wrongInput();
                }
            }
            case EXCEPTION -> {

            }
            case GAME -> {
                if(input.equals("n")) {
                    UI.showNextLibrary();
                } else if (input.equals("p")) {
                    UI.showPrevLibrary();
                } else if(!input.equals("") && input.charAt(0)=='c'){
                    interpretMove(input);
                } else {
                    inputError = true;
                    UI.wrongInput();
                }
            }
        }
        UI.clearInput();
    }


    private void interpretInit(String input) {
        int in;
        try{
            in = Integer.parseInt(input);
        } catch (NumberFormatException e){
            inputError=true;
            UI.wrongInput();                                         ;
            return;
        }
        if(viewBuffer.getModelView().getLobbies().size()==0) {
            if(in == 1){
                creating = true;
                stringCount = 0;
                UI.createPrint(null,null);
            } else {
                inputError=true;
                UI.wrongInput();
            }
        }
        else{
            if (in == 1){
                creating = true;
                stringCount = 0;
                UI.createPrint(null,null);
            } else if(in == 2){
                joining = true;
                stringCount = 0;
                UI.joinPrint(null,null);
            } else{
                UI.initPrint();
            }
        }
    }

    private void interpretJoin(String input, ModelView modelViewCopy) {
        if(stringCount==0){
            int i;
            try{
                i = Integer.parseInt(input);
            } catch (NumberFormatException e){
                inputError=true;
                UI.wrongInput();
                joining = false;
                UI.initPrint();
                return;
            }
            readStrings[0] = modelViewCopy.getLobbies().get(i-1);
            stringCount++;
            UI.joinPrint(readStrings[0],null);
        } else if(stringCount==1){
            readStrings[1] = input;
            UI.joinPrint(readStrings[0], input);
            joinRequest(readStrings[0],input);
            UI.setMyUsername(input);
            stringCount=0;
        }
    }

    private void interpretCreate(String input) {
        if(stringCount==0){
            readStrings[0] = input;
            stringCount++;
            UI.createPrint(input,null);
        } else if(stringCount==1){
            readStrings[1] = input;
            UI.createPrint(readStrings[0], input);
            createRequest(readStrings[0],input);
            UI.setMyUsername(input);
            stringCount=0;
        }
    }

    private void interpretMove(String input) {
        input = input.replace(" ","");
        input = input.substring(1,input.length());
        List<Position> positions = new ArrayList<>();
        boolean read = false;
        boolean minus = false;
        boolean completed = false;
        boolean end = false;
        int x=-10;
        int y=-10;
        int column = -1;
        for(int i = 0; i<input.length(); i++){
            char c = input.charAt(i);
            if(!end && Character.isDigit(c)){
                int value = minus ? -Character.digit(c, 10) : Character.digit(c, 10);
                if(read) {
                    y = value;
                    read = false;
                    completed = true;
                }
                else {
                    x = value;
                    read = true;
                }
            } else if(c=='-'){
                minus = true;
            } else if(c==':'){
                end = true;
            } else if(end){
                column = Character.digit(c,10);
                if(column==-1){
                    UI.wrongInput();
                    inputError = true;
                }
                break;
            }
            if(completed){
                completed = false;
                if(x<-4 || x>4 || y<-4 || y>4) {
                    inputError = true;
                    UI.wrongInput();
                } else {
                    positions.add(new Position(x,y));
                }
            }
        }
        moveRequest(positions,column);
    }

    private void joinRequest(String lobby, String playerName){
        synchronized (viewBuffer) {
            try {
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.JOIN_REQUEST,playerName,lobby));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createRequest(String lobby, String playerName){
        synchronized (viewBuffer) {
            try {
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.CREATE_REQUEST, playerName, lobby));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startRequest() {
        synchronized (viewBuffer){
            try {
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.START_REQUEST));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void moveRequest(List<Position> positions, int column) {
        synchronized (viewBuffer){
            Object[] args = new Object[]{positions.stream().toList()};
            try {
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.DRAW_REQUEST,positions));
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.ORGANIZE_REQUEST,new ArrayList<>()));
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.INSERT_REQUEST,column));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean removePlayer() {
        LobbyModelView lobbyModelView = viewBuffer.getLobbyModelView();
        int currentSize =  lobbyModelView.getGameConfig().getNumPlayers();
        if(currentSize==2) return false;
        GameConfig config = null;
        try {
            config = new GameConfig(currentSize-1);
        } catch (InvalidParameterException ignored) {
            return false;
        }
        synchronized (viewBuffer) {
            try {
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST,config));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private boolean addPlayer() {
        LobbyModelView lobbyModelView = viewBuffer.getLobbyModelView();
        int currentSize =  lobbyModelView.getGameConfig().getNumPlayers();
        if(currentSize==4) return false;
        GameConfig config;
        try {
            config = new GameConfig(currentSize+1);
        } catch (InvalidParameterException e) {
            return false;
        }
        synchronized (viewBuffer) {
            try {
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST,config));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
