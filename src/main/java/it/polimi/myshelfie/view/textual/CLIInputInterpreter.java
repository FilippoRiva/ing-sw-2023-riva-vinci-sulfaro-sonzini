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
import it.polimi.myshelfie.view.InternalViewState;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.ViewType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The CLIInputInterpreter given an input string it interprets it, updates the view, and tells the viewManager to notify its observe
 * A string is interpreted based on how the UI and the viewManager are set
 */
public class CLIInputInterpreter {
    private final TextualUI UI;
    private final ViewManager viewManager;
    private final List<String> inputParameters;

    /**
     * Creates a CLIInputInterpreter.
     *
     * @param UI the UI this interpreter should look at to interpret user input
     */
    public CLIInputInterpreter(TextualUI UI, List<String> inputParameters) {
        this.UI = UI;
        this.viewManager = ViewManager.getInstance();
        this.inputParameters = inputParameters;
    }

    /**
     * Interpret a string based on the UI and viewManager state variables.
     * @param input the string to be interpreted.
     */
    public void interpret(String input) {
        if (input.equals("")) {
            UI.clearInput();
            return;
        }
        if (input.equals("r")) {
            UI.refresh();
            return;
        }
        ViewType vtCopy;
        ModelView modelView;
        LobbyModelView lobbyModelView;
        synchronized (this) {
            vtCopy = viewManager.getViewType();
            modelView = viewManager.getModelView();
            lobbyModelView = viewManager.getLobbyModelView();
        }
        switch (vtCopy) {
            case INIT -> {
                if (input.startsWith("close")) {
                    System.exit(0);
                } else if (input.startsWith("autoplay")) {
                    try {
                        String numbers = input.replace(" ", "").substring(8);
                        if (numbers.length() != 0) {
                            viewManager.startAutoPlayer(Integer.parseInt(numbers));
                        } else {
                            viewManager.startAutoPlayer();
                        }
                    } catch (NumberFormatException e) {
                        UI.wrongInput("Room size not available");
                    }
                } else if (UI.getInternalViewState() == InternalViewState.CREATE) {
                    interpretCreate(input);
                } else if (UI.getInternalViewState() == InternalViewState.JOIN) {
                    interpretJoin(input, modelView);
                } else {
                    interpretInit(input);
                }
            }
            case LOBBY -> {
                if (UI.getInternalViewState() == InternalViewState.HELP) {
                    if (!input.equals("chat")) {
                        UI.setInternalViewState(InternalViewState.LOBBY);
                    } else {
                        UI.setInternalViewState(InternalViewState.CHAT);
                    }
                } else if (UI.getInternalViewState() == InternalViewState.CHAT) {
                    if (input.equals("lobby")) {
                        UI.setInternalViewState(InternalViewState.LOBBY);
                    } else if (input.equals("help")) {
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
                            if (viewManager.addPlayer()) {
                                UI.wrongInput();
                            }
                        }
                        case "-" -> {
                            if (viewManager.removePlayer()) {
                                UI.wrongInput();
                            }
                        }
                        case "?" -> weirdConfig();
                        case "s" -> {
                            if (lobbyModelView.getPlayers().size() == lobbyModelView.getGameConfig().getNumPlayers())
                                viewManager.startRequest();
                            else UI.wrongInput();
                        }
                        case "exit" -> viewManager.exitRequest();
                        default -> UI.wrongInput();
                    }
                }
            }
            case GAME -> {
                if (input.startsWith("autoplay")) {
                    viewManager.startAutoPlayer();
                } else if (UI.getInternalViewState() == InternalViewState.HELP) {
                    switch (input) {
                        case "game" -> UI.setInternalViewState(InternalViewState.GAME);
                        case "goals" -> UI.setInternalViewState(InternalViewState.GOALS);
                        case "chat" -> UI.setInternalViewState(InternalViewState.CHAT);
                    }
                } else if (UI.getInternalViewState() == InternalViewState.CHAT) {
                    switch (input) {
                        case "game" -> UI.setInternalViewState(InternalViewState.GAME);
                        case "goals" -> UI.setInternalViewState(InternalViewState.GOALS);
                        case "help" -> UI.setInternalViewState(InternalViewState.HELP);
                        default -> interpretMessage(input);
                    }
                } else if (UI.getInternalViewState() == InternalViewState.GOALS) {
                    switch (input) {
                        case "game" -> UI.setInternalViewState(InternalViewState.GAME);
                        case "chat" -> UI.setInternalViewState(InternalViewState.CHAT);
                        case "help" -> UI.setInternalViewState(InternalViewState.HELP);
                    }
                } else {
                    if (input.equals("n")) {
                        UI.showNextBookshelf();
                    } else if (input.equals("p")) {
                        UI.showPrevBookshelf();
                    } else if (input.equals("help")) {
                        UI.setInternalViewState(InternalViewState.HELP);
                    } else if (input.equals("chat")) {
                        UI.setInternalViewState(InternalViewState.CHAT);
                    } else if (input.charAt(0) == 'd') {
                        interpretChoice(input);
                    } else if (input.charAt(0) == 'o') {
                        interpretOrder(input);
                    } else if (input.charAt(0) == 'i') {
                        interpretInsert(input);
                    } else if (input.equals("goals")) {
                        UI.setInternalViewState(InternalViewState.GOALS);
                    } else {
                        UI.wrongInput();
                    }
                }
            }
            case WIN -> viewManager.exitRequest();
        }
        UI.clearInput();
    }

    private void interpretInit(String input) {
        int in;
        try {
            in = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            UI.wrongInput();
            return;
        }
        if (viewManager.getModelView().getLobbies().size() == 0) {
            if (in == 1) {
                inputParameters.clear();
                UI.setInternalViewState(InternalViewState.CREATE);
            } else {
                UI.wrongInput();
            }
        } else {
            if (in == 1) {
                inputParameters.clear();
                UI.setInternalViewState(InternalViewState.CREATE);
            } else if (in == 2) {
                inputParameters.clear();
                UI.setInternalViewState(InternalViewState.JOIN);
            } else {
                UI.wrongInput();
            }
        }
    }

    private void interpretJoin(String input, ModelView modelViewCopy) {
        if (input.equals("back") || input.equals("b")) {
            inputParameters.clear();
            UI.setInternalViewState(InternalViewState.INIT);
            return;
        }
        if (inputParameters.size() == 0) {
            int i;
            try {
                i = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                UI.wrongInput();
                UI.setInternalViewState(InternalViewState.INIT);
                return;
            }
            if (i > modelViewCopy.getLobbies().size()) {
                UI.wrongInput();
                UI.setInternalViewState(InternalViewState.INIT);
                return;
            }
            inputParameters.add(modelViewCopy.getLobbies().get(i - 1));
            UI.setInternalViewState(InternalViewState.JOIN);
        } else if (inputParameters.size() == 1) {
            inputParameters.add(input);
            UI.setInternalViewState(InternalViewState.JOIN);
            viewManager.joinRequest(inputParameters.get(0), inputParameters.get(1));
            ViewManager.setUsername(input);
            inputParameters.clear();
        }
    }

    private void interpretCreate(String input) {
        if (input.equals("back") || input.equals("b")) {
            inputParameters.clear();
            UI.setInternalViewState(InternalViewState.INIT);
            return;
        }
        if (inputParameters.size() == 0) {
            inputParameters.add(input);
            UI.setInternalViewState(InternalViewState.CREATE);
        } else if (inputParameters.size() == 1) {
            inputParameters.add(input);
            UI.setInternalViewState(InternalViewState.CREATE);
            viewManager.createRequest(inputParameters.get(0), inputParameters.get(1));
            ViewManager.setUsername(input);
        }
    }

    private void interpretChoice(String input) {
        if (input.length() == 1) return;
        input = input.substring(2);
        List<Position> positions = new ArrayList<>();
        List<Integer> coordinates;
        try {
            coordinates = Arrays.stream(input.split(" ")).map(Integer::parseInt).toList();
        } catch (NumberFormatException e) {
            UI.wrongInput("please enter valid coordinates");
            return;
        }
        for (int i = 0; i < coordinates.size() / 2; i++) {
            Position position = new Position(coordinates.get(i * 2), coordinates.get(i * 2 + 1));
            positions.add(position);
        }
        viewManager.drawRequest(positions);
    }

    private void interpretOrder(String input) {
        input = input.substring(1);
        if (input.equals("")) {
            viewManager.organizeRequest(viewManager.getGameModelView().getCurrentPlayer().getTakenTiles());
            return;
        }
        FullGameModelView gmv = viewManager.getGameModelView();
        List<ItemTile> takenTiles = gmv.getPlayer(ViewManager.username).getTakenTiles();
        input = input.replace(" ", "");
        if (input.length() != takenTiles.size()) {
            UI.wrongInput();
            return;
        }
        List<Integer> choices = new ArrayList<>();
        for (char c : input.toCharArray()) {
            int option = Character.digit(c, 10);
            if (option == -1) {
                UI.wrongInput("please insert valid numbers");
                return;
            }
            choices.add(option);
        }
        if (choices.stream().distinct().count() != choices.size()) {
            UI.wrongInput("please insert distinct values");
            return;
        }
        if (choices.stream().anyMatch(x -> x > (takenTiles.size() - 1))) {
            UI.wrongInput("please insert valid numbers");
            return;
        }
        viewManager.organizeRequest(choices.stream().map(takenTiles::get).toList());
    }

    private void interpretInsert(String input) {
        input = input.substring(1);
        input = input.replace(" ", "");
        int column;
        try {
            column = Integer.parseInt(input);
            if (column > 5 || column < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            UI.wrongInput("insert a valid column");
            return;
        }
        viewManager.insertRequest(column);
    }

    private void interpretMessage(String input) {
        if (input.isEmpty()) return;
        if (!input.startsWith("/")) {
            Message message = new Message(ViewManager.username, Messages.toEveryOne, input);
            viewManager.sendChatMessage(message);
            return;
        }
        input = input.substring(1);
        if (!input.contains(" ")) {
            UI.wrongInput("insert message");
            return;
        }
        String user = input.substring(0, input.indexOf(" "));
        if (!viewManager.getLobbyModelView().getPlayers().contains(user)) {
            UI.wrongInput("insert a valid username");
        }
        String msg = input.substring(user.length() + 1);
        if (msg.isEmpty()) {
            UI.wrongInput("insert message");
            return;
        }
        Message message = new Message(ViewManager.username, user, msg);
        viewManager.sendChatMessage(message);
    }

    private void weirdConfig() {
        GameConfig config;
        try {
            config = new GameConfig(6, new Dimension(3, 4), 5, 123, 9, 1, 1);
        } catch (InvalidParameterException e) {
            return;
        }
        viewManager.configRequest(config);
        UI.wrongInput(ANSI.PINK_FG + "weird config set" + ANSI.PINK_FG);
    }
}
