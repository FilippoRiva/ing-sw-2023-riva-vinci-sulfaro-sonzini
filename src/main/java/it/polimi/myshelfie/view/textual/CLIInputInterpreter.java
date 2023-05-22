package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.chatting.Messages;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.view.events.ViewEvent;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLIInputInterpreter {
    TextualUI UI;
    final ViewBuffer viewBuffer;
    final List<String> inputParameters;

    /**
     * creates a CLIInputInterpreter that given an input string it interprets it, updates the view, and tells the viewBuffer
     * to notify its observers.
     * how a string is interpreted depends on how the UI and the ViewBuffer are set
     * @param UI the UI this interpreter should look at to interpret user input
     * @param viewBuffer the ViewBuffer this interpreter should use to interpret user input and notify new ViewEvents
     *                   like JOIN_REQUEST, CREATE_REQUEST, CONFIG_REQUEST, ... setting the appropriate parameters of
     *                   the ViewBuffer
     */
    public CLIInputInterpreter(TextualUI UI, ViewBuffer viewBuffer, List<String> inputParameters){
        this.UI = UI;
        this.viewBuffer = viewBuffer;
        this.inputParameters = inputParameters;
    }
    public void interpret(String input){
        if(input.equals("")) {
            UI.clearInput();
            return;
        }
        if(input.equals("r")) {
            UI.refresh();
            return;
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
                if(input.startsWith("autoplay")){
                   AutoPlayer autoPlayer = new AutoPlayer(viewBuffer,UI);
                    try {
                        autoPlayer.start(Integer.parseInt(input.replace(" ","").substring(8)));
                    } catch (InvalidParameterException e) {
                        UI.wrongInput("Unable to start autoplay mode");
                    } catch (NumberFormatException e){
                        UI.wrongInput("Please enter room size");
                    }
                } else
                if(UI.getInternalViewState()==InternalViewState.CREATE){
                    interpretCreate(input);
                } else if(UI.getInternalViewState()==InternalViewState.JOIN){
                    interpretJoin(input, modelView);
                } else {
                    interpretInit(input);
                }
            }
            case LOBBY -> {
                if(UI.getInternalViewState()==InternalViewState.HELP){
                    if(!input.equals("chat")){
                        UI.setInternalViewState(InternalViewState.LOBBY);
                    } else {
                        UI.setInternalViewState(InternalViewState.CHAT);
                    }
                } else if (UI.getInternalViewState()==InternalViewState.CHAT){
                    if(input.equals("lobby")){
                        UI.setInternalViewState(InternalViewState.LOBBY);
                    } else if(input.equals("help")){
                        UI.setInternalViewState(InternalViewState.HELP);
                    } else {
                        interpretMessage(input);
                    }
                } else {
                    switch (input) {
                        case "help" -> UI.setInternalViewState(InternalViewState.HELP);
                        case "chat" -> UI.setInternalViewState(InternalViewState.CHAT);
                        case "lobby" -> UI.setInternalViewState(InternalViewState.LOBBY);
                        case "+" -> {
                            if (!viewBuffer.addPlayer()) {
                                UI.wrongInput();
                            }
                        }
                        case "-" -> {
                            if (!viewBuffer.removePlayer()) {
                                UI.wrongInput();
                            }
                        }
                        case "?" -> weirdConfig();
                        case "s" -> {
                            if (lobbyModelView.getPlayers().size() == lobbyModelView.getGameConfig().getNumPlayers()) viewBuffer.startRequest();
                            else UI.wrongInput();
                        }
                        case "exit" -> viewBuffer.exitRequest();
                        default -> UI.wrongInput();
                    }
                }
            }
            case GAME -> {
                if (UI.getInternalViewState()==InternalViewState.HELP) {
                    switch (input) {
                        case "game" -> UI.setInternalViewState(InternalViewState.GAME);
                        case "goals" -> UI.setInternalViewState(InternalViewState.GOALS);
                        case "chat" -> UI.setInternalViewState(InternalViewState.CHAT);
                    }
                } else if (UI.getInternalViewState()==InternalViewState.CHAT){
                    switch (input) {
                        case "game" -> UI.setInternalViewState(InternalViewState.GAME);
                        case "goals" -> UI.setInternalViewState(InternalViewState.GOALS);
                        case "help" -> UI.setInternalViewState(InternalViewState.HELP);
                        default -> interpretMessage(input);
                    }
                } else if (UI.getInternalViewState()==InternalViewState.GOALS){
                    switch (input) {
                        case "game" -> UI.setInternalViewState(InternalViewState.GAME);
                        case "chat" -> UI.setInternalViewState(InternalViewState.CHAT);
                        case "help" -> UI.setInternalViewState(InternalViewState.HELP);
                    }
                } else {
                    if(input.equals("n")) {
                        UI.showNextLibrary();
                    } else if (input.equals("p")) {
                        UI.showPrevLibrary();
                    } else if(input.equals("help")) {
                        UI.setInternalViewState(InternalViewState.HELP);
                    } else if (input.equals("chat")){
                        UI.setInternalViewState(InternalViewState.CHAT);
                    } else if(input.charAt(0)=='d') {
                        interpretChoice(input);
                    } else if(input.charAt(0)=='o') {
                        interpretOrder(input);
                    } else if(input.charAt(0)=='i') {
                        interpretInsert(input);
                    } else if(input.equals("goals")) {
                        UI.setInternalViewState(InternalViewState.GOALS);
                    } else {
                        UI.wrongInput();
                    }
                }
            }
            case WIN -> {
                if(input.equals("exit")){
                    UI.setInternalViewState(InternalViewState.INIT);
                    viewBuffer.exitRequest();
                } else {
                    UI.wrongInput("pls stop playing this game");
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
            UI.wrongInput();
            return;
        }
        if(viewBuffer.getModelView().getLobbies().size()==0) {
            if(in == 1){
                inputParameters.clear();
                UI.setInternalViewState(InternalViewState.CREATE);
            } else {
                UI.wrongInput();
            }
        }
        else{
            if (in == 1){
                inputParameters.clear();
                UI.setInternalViewState(InternalViewState.CREATE);
            } else if(in == 2){
                inputParameters.clear();
                UI.setInternalViewState(InternalViewState.JOIN);
            } else{
                UI.wrongInput();
            }
        }
    }

    private void interpretJoin(String input, ModelView modelViewCopy) {
        if(input.equals("back")||input.equals("b")){
            inputParameters.clear();
            UI.setInternalViewState(InternalViewState.INIT);
            return;
        }
        if(inputParameters.size()==0){
            int i;
            try{
                i = Integer.parseInt(input);
            } catch (NumberFormatException e){
                UI.wrongInput();
                UI.setInternalViewState(InternalViewState.INIT);
                return;
            }
            if(i>modelViewCopy.getLobbies().size()){
                UI.wrongInput();
                UI.setInternalViewState(InternalViewState.INIT);
                return;
            }
            inputParameters.add(modelViewCopy.getLobbies().get(i-1));
            UI.setInternalViewState(InternalViewState.JOIN);
        } else if(inputParameters.size()==1){
            inputParameters.add(input);
            UI.setInternalViewState(InternalViewState.JOIN);
            viewBuffer.joinRequest(inputParameters.get(0),inputParameters.get(1));
            UI.setMyUsername(input);
            inputParameters.clear();
        }
    }

    private void interpretCreate(String input) {
        if(input.equals("back")||input.equals("b")){
            inputParameters.clear();
            UI.setInternalViewState(InternalViewState.INIT);
            return;
        }
        if(inputParameters.size()==0){
            inputParameters.add(input);
            UI.setInternalViewState(InternalViewState.CREATE);
        } else if(inputParameters.size()==1){
            inputParameters.add(input);
            UI.setInternalViewState(InternalViewState.CREATE);
            viewBuffer.createRequest(inputParameters.get(0),inputParameters.get(1));
            UI.setMyUsername(input);
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
        viewBuffer.drawRequest(positions);
    }

    private void interpretOrder(String input) {
        if(input.equals("")){
            viewBuffer.organizeRequest(new ArrayList<>());
            return;
        }
        FullGameModelView gmv = viewBuffer.getGameModelView();
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
        viewBuffer.organizeRequest(choices.stream().map(takenTiles::get).toList());
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
        viewBuffer.insertRequest(column);
    }

    private void interpretMessage(String input){
        if(!input.startsWith("/")){
            Message message = new Message(UI.myUsername, Messages.toEveryOne,input);
            viewBuffer.sendChatMessage(message);
            return;
        }
        input = input.substring(1);
        if(!input.contains(" ")) {
            UI.wrongInput("insert message");
            return;
        }
        String user = input.substring(0,input.indexOf(" "));
        if(!viewBuffer.getLobbyModelView().getPlayers().contains(user)){
            UI.wrongInput("insert a valid username");
        }
        String msg = input.substring(user.length()+1);
        Message message = new Message(UI.myUsername, user, msg);
        viewBuffer.sendChatMessage(message);
    }

    private void weirdConfig(){
        GameConfig config;
        try {
            config = new GameConfig(6,new Dimension(3,4),5,123,9,1,1);
        } catch (InvalidParameterException e) {
            return;
        }
        synchronized (viewBuffer) {
            try {
                viewBuffer.sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST,config));
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            }
        }
        UI.wrongInput(ANSI.PINK_FG+"weird config set"+ANSI.PINK_FG);
    }
}
