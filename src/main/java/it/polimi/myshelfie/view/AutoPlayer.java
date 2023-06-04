package it.polimi.myshelfie.view;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.ModelView;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.textual.TextualUI;

import java.util.*;

public class AutoPlayer implements Observer<ViewUpdate, Object> {
    private final ViewManager viewManager;
    private final TextualUI UI;
    private boolean owner;
    private boolean finished;
    private String username;
    private int numPlayers;

    public AutoPlayer(ViewManager viewManager, TextualUI UI){
        this.viewManager = viewManager;
        this.UI = UI;
        finished = false;
        viewManager.addUI(this);
    }

    public synchronized void preGameStart(int numPlayers) throws InvalidParameterException {
        this.numPlayers = numPlayers;
        ModelView modelView = viewManager.getModelView();
        if(!modelView.getLobbies().contains("test")){
            username="bot"+String.valueOf(System.currentTimeMillis()).substring(8);
            ViewManager.setUsername(username);
            viewManager.createRequest("test",username);
            owner = true;
        } else {
            username = "bot"+String.valueOf(System.currentTimeMillis()).substring(8);
            ViewManager.setUsername(username);
            viewManager.joinRequest("test",username);
            owner = false;
        }
    }

    public synchronized void postGameStart(String username){
        this.username = username;
    }


    @Override
    public synchronized void update(ViewUpdate evt, Object unused) {
        if(finished) {
            ViewManager.getInstance().removeUI(this);
            return;
        }
        ViewType vt = viewManager.getViewType();
        try {
            switch (evt) {
                case LOBBY_UPDATE -> {
                    if (vt != ViewType.LOBBY) return;
                    LobbyModelView lmv = viewManager.getLobbyModelView();
                    if (lmv == null) return;
                    if (owner && lmv.getPlayers().size() == lmv.getGameConfig().getNumPlayers())
                        viewManager.startRequest();
                }
                case GAME_UPDATE -> {
                    if (vt != ViewType.GAME) return;
                    FullGameModelView gmv = viewManager.getGameModelView();
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
                            viewManager.configRequest(new GameConfig(numPlayers));
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
                    viewManager.drawRequest(positions);
                    finish = true;
                    break;
                }
            }
            if(finish) break;
        }
    }

    private synchronized void organize(FullGameModelView gmv) throws InvalidParameterException {
        List<ItemTile> takenTiles = gmv.getPlayer(username).getTakenTiles();
        viewManager.organizeRequest(takenTiles);
    }

    private synchronized void insert(FullGameModelView gmv) throws InvalidParameterException {
        List<ItemTile> firstLine = Arrays.stream(gmv.getPlayer(username).getBookshelf()[0]).toList();
        List<Integer> columns = firstLine.stream().map(x -> isValidColumn(x, firstLine.indexOf(x))).filter(x->x!=-1).toList();
        Random rand = new Random();
        int chosenColumn = columns.get(rand.nextInt(columns.size()));
        viewManager.insertRequest(chosenColumn);
    }

    private int isValidColumn(ItemTile tile,int column){
        if(tile.getType()==ItemTileType.EMPTY) return column;
        return -1;
    }
}
