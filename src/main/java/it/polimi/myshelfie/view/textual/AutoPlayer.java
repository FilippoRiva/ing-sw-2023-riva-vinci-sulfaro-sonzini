package it.polimi.myshelfie.view.textual;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.network.NetworkMessage;
import it.polimi.myshelfie.view.events.ViewEvent;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.ViewUpdate;

import java.util.*;

public class AutoPlayer implements Observer<ViewUpdate, NetworkMessage> {
    private final ViewBuffer viewBuffer;
    private final TextualUI UI;
    private boolean owner;
    private boolean finished;
    private String username;
    private int numPlayers;

    public AutoPlayer(ViewBuffer viewBuffer,TextualUI UI){
        this.viewBuffer = viewBuffer;
        this.UI = UI;
        finished = false;
        viewBuffer.addUI(this);
    }

    public synchronized void start(int numPlayers) throws InvalidParameterException {
        this.numPlayers = numPlayers;
        ModelView modelView = viewBuffer.getModelView();
        if(!modelView.getLobbies().contains("test")){
            username="bot"+System.currentTimeMillis();
            viewBuffer.sendMessage(new NetworkMessage(ViewEvent.CREATE_REQUEST,username,"test"));
            UI.setMyUsername(username);
            owner = true;
        } else {
            username = "bot"+System.currentTimeMillis();
            UI.setMyUsername(username);
            viewBuffer.sendMessage(new NetworkMessage(ViewEvent.JOIN_REQUEST, username, "test"));
            owner = false;
        }
    }


    @Override
    public synchronized void update(ViewUpdate evt, NetworkMessage unused) {
        if(finished) return;
        if(evt==null) return;
        ViewType vt = viewBuffer.getViewType();
        try {
            switch (evt) {
                case LOBBY_UPDATE -> {
                    if (vt != ViewType.LOBBY) return;
                    LobbyModelView lmv = viewBuffer.getLobbyModelView();
                    if (lmv == null) return;
                    if (owner && lmv.getPlayers().size() == lmv.getGameConfig().getNumPlayers())
                        viewBuffer.sendMessage(new NetworkMessage(ViewEvent.START_REQUEST));
                }
                case GAME_UPDATE -> {
                    if (vt != ViewType.GAME) return;
                    FullGameModelView gmv = viewBuffer.getGameModelView();
                    if (!gmv.getCurrentPlayerName().equals(username)) return;
                    switch (gmv.getCurrentState()) {
                        case DRAW, LAST_DRAW -> draw(gmv);
                        case ORGANIZE, LAST_ORGANIZE -> organize(gmv);
                        case INSERT, LAST_INSERT -> insert(gmv);
                        case WIN -> finished = true;
                    }
                }
                case VIEW_TYPE_UPDATE -> {
                    if (Objects.requireNonNull(vt) == ViewType.LOBBY) {
                        if (owner) {
                            viewBuffer.sendMessage(new NetworkMessage(ViewEvent.CONFIG_REQUEST, new GameConfig(numPlayers)));
                        }
                    }
                }
            }
        } catch (InvalidParameterException e){
            UI.wrongInput("Auto player failed");
        }
    }

    private synchronized void draw(FullGameModelView gmv) throws InvalidParameterException {
        boolean finish = false;
        for(int y = 4;y>=-4;y--){
            for(int x = -4; x<=4;x++){
                ItemTile tile = gmv.getTile(x,y);
                if(tile!=null && tile.getType()!= ItemTileType.EMPTY){
                    List<Position> positions = new ArrayList<>();
                    positions.add(new Position(x,y));
                    viewBuffer.sendMessage(new NetworkMessage(ViewEvent.DRAW_REQUEST,positions));
                    finish = true;
                    break;
                }
            }
            if(finish) break;
        }
    }

    private synchronized void organize(FullGameModelView gmv) throws InvalidParameterException {
        List<ItemTile> takenTiles = gmv.getPlayer(username).getTakenTiles();
        viewBuffer.sendMessage(new NetworkMessage(ViewEvent.ORGANIZE_REQUEST,takenTiles));
    }

    private synchronized void insert(FullGameModelView gmv) throws InvalidParameterException {
        List<ItemTile> firstLine = Arrays.stream(gmv.getPlayer(username).getBookshelf()[0]).toList();
        List<Integer> columns = firstLine.stream().map(x -> isValidColumn(x, firstLine.indexOf(x))).filter(x->x!=-1).toList();
        Random rand = new Random();
        int chosenColumn = columns.get(rand.nextInt(columns.size()));
        viewBuffer.sendMessage(new NetworkMessage(ViewEvent.INSERT_REQUEST,chosenColumn));
    }

    private int isValidColumn(ItemTile tile,int column){
        if(tile.getType()==ItemTileType.EMPTY) return column;
        return -1;
    }
}
