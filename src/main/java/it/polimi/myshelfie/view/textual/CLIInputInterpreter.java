package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.GameModelView;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.network.events.ViewEvent;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLIInputInterpreter {
    TextualUI UI;
    final ViewBuffer viewBuffer;
    String[] readStrings;
    int stringCount;
    boolean helping;
    boolean creating;
    boolean joining;
    boolean chatting;
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
            case GAME -> {
                if (helping) {
                    UI.goToGame();
                    helping = false;
                } else if (chatting){
                    if(input.equals("game")){
                        UI.goToGame();
                        chatting = false;
                    }
                    //TODO
                } else {
                    if(input.equals("")){
                        return;
                    }
                    else if(input.equals("n")) {
                        UI.showNextLibrary();
                    } else if (input.equals("p")) {
                        UI.showPrevLibrary();
                    } else if(input.equals("help")) {
                        UI.goToHelp();
                        helping = true;
                    } else if (input.equals("chat")){
                        UI.goToChat();
                        chatting = true;
                    } else if(input.charAt(0)=='d') {
                        interpretChoice(input);
                    } else if(input.charAt(0)=='o') {
                        interpretOrder(input);
                    } else if(input.charAt(0)=='i') {
                        interpretInsert(input);
                    } else {
                        inputError = true;
                        UI.wrongInput();
                    }
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

    private void interpretChoice(String input) {
        if(input.length()==1) return;
        input = input.substring(2);
        List<Position> positions = new ArrayList<>();
        List<Integer> coordinates;
        try{
            coordinates = Arrays.stream(input.split(" ")).map(Integer::parseInt).toList();
        } catch (NumberFormatException e){
            UI.wrongInput("please enter valid coordinates");
            return;
        }
        for(int i = 0; i<coordinates.size()/2;i++){
            Position position = new Position(coordinates.get(i*2),coordinates.get(i*2+1));
            positions.add(position);
        }
        drawRequest(positions);
    }

    private void interpretOrder(String input) {
        if(input.equals("")){
            organizeRequest(new ArrayList<>());
            return;
        }
        GameModelView gmv = viewBuffer.getGameModelView();
        List<ItemTile> takenTiles = gmv.getPlayer(UI.myUsername).getTakenTiles();
        input = input.substring(1);
        input = input.replace(" ","");
        if (input.length()!=takenTiles.size()){
            UI.wrongInput();
            return;
        }
        List<Integer> choices = new ArrayList<>();
        for(char c : input.toCharArray()){
            int option = Character.digit(c,10);
            if(option==-1) {
                UI.wrongInput("please insert valid numbers");
                return;
            }
            choices.add(option);
        }
        if(choices.stream().distinct().count() != choices.size()){
            UI.wrongInput("please insert distinct values");
            return;
        }
        if(choices.stream().anyMatch(x -> x>(takenTiles.size()-1))){
            UI.wrongInput("please insert valid numbers");
            return;
        }
        organizeRequest(choices.stream().map(takenTiles::get).toList());
    }

    private void interpretInsert(String input) {
        input = input.substring(1);
        input = input.replace(" ","");
        int column;
        try {
            column = Integer.parseInt(input);
            if(column > 5 || column < 0) throw new NumberFormatException();
        } catch (NumberFormatException e){
            UI.wrongInput("insert a valid column");
            return;
        }
        insertRequest(column);
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

    private void drawRequest(List<Position> positions) {
        synchronized (viewBuffer){
            try {
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.DRAW_REQUEST,positions));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void organizeRequest(List<ItemTile> orderedTiles){
        synchronized (viewBuffer){
            try{
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.ORGANIZE_REQUEST,orderedTiles));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void insertRequest(int column){
        synchronized (viewBuffer){
            try{
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
