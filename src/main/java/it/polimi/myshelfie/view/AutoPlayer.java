package it.polimi.myshelfie.view;

import it.polimi.myshelfie.GameConfig;
import it.polimi.myshelfie.model.ItemTile;
import it.polimi.myshelfie.model.ItemTileType;
import it.polimi.myshelfie.model.Position;
import it.polimi.myshelfie.model.exceptions.InvalidParameterException;
import it.polimi.myshelfie.model.modelviews.FullGameModelView;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.ModelView;

import java.util.*;

public class AutoPlayer implements UI{
    private final ViewManager viewManager;
    private boolean owner;
    private String username;
    private int numPlayers;
    private boolean joined;
    private boolean finished;

    public AutoPlayer(){
        this.viewManager = ViewManager.getInstance();
        viewManager.addUI(this);
        joined = false;
        finished = false;
    }

    public synchronized void preGameStart(int numPlayers) throws InvalidParameterException {
        this.numPlayers = numPlayers;
        ModelView modelView = viewManager.getModelView();
        if(!modelView.getLobbies().contains("test")){
            username="bot"+String.valueOf(System.currentTimeMillis()).substring(8);
            ViewManager.setUsername(username);
            owner = true;
            this.update(ViewUpdate.VIEW_TYPE_UPDATE,null);
        } else if(modelView.getMaxSize("test")==numPlayers){
            username = "bot"+String.valueOf(System.currentTimeMillis()).substring(8);
            ViewManager.setUsername(username);
            owner = false;
            this.update(ViewUpdate.MODEL_UPDATE,null);
        } else {
            throw new InvalidParameterException("no test lobby with "+numPlayers+" size");
        }
    }

    public synchronized void postGameStart(){
        this.username = ViewManager.getUsername();
    }

    @Override
    public synchronized void update(ViewUpdate evt, Object unused) {
        if(finished) return;
        ViewType vt = viewManager.getViewType();
        try {
            switch (evt) {
                case LOBBY_UPDATE -> {
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
                        case WIN -> {
                            ViewManager.getInstance().removeUI(this);
                            finished = true;
                        }
                    }
                }
                case VIEW_TYPE_UPDATE -> {
                    if ( vt == ViewType.LOBBY) {
                        if (owner) {
                            viewManager.configRequest(new GameConfig(numPlayers));
                        }
                    } else if (vt == ViewType.INIT){
                        if(owner) {
                            if(!joined && !ViewManager.getInstance().getModelView().getLobbies().contains("test")) {
                                ViewManager.getInstance().createRequest("test",username);
                                joined = true;
                            }
                        }
                    } else if(vt == ViewType.GAME){
                        this.update(ViewUpdate.GAME_UPDATE,null);
                    }
                }
                case MODEL_UPDATE -> {
                    if(!owner){
                        if(!joined && ViewManager.getInstance().getModelView().getLobbies().contains("test") && ViewManager.getInstance().getModelView().getMaxSize("test")==numPlayers) {
                            ViewManager.getInstance().joinRequest("test",username);
                            joined = true;
                        }
                    }

                }
            }
        } catch (InvalidParameterException ignored){
        }
    }

    private synchronized void draw(FullGameModelView gmv) throws InvalidParameterException {
        ItemTile[][] bookshelf = gmv.getCurrentPlayer().getBookshelf();
        int maxDepth = 3;
        boolean finish = false;
        for(int y = 2 ; y >= 0; y--){
            if(finish) break;
            for(int x = 0 ; x < bookshelf[0].length; x++){
                if (bookshelf[y][x].getType() == ItemTileType.EMPTY) {
                    maxDepth = y + 1;
                    finish = true;
                    break;
                }
            }
        }
        finish = false;
        for(int y = 4;y>=-4;y--){
            for(int x = -4; x<=4;x++){
                ItemTile tile = gmv.getTile(x,y);
                if(tile!=null && tile.getType()!= ItemTileType.EMPTY){
                    List<Position> positions = new ArrayList<>();
                    positions.add(new Position(x,y));
                    if(maxDepth>1 && gmv.getTile(x+1,y)!=null && gmv.getTile(x+1,y).getType() != ItemTileType.EMPTY){
                        positions.add(new Position(x+1,y));
                        if(maxDepth>2 && gmv.getTile(x+2,y)!=null && gmv.getTile(x+2,y).getType() != ItemTileType.EMPTY){
                            positions.add(new Position(x+2,y));
                        }
                    }
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
        ItemTile[][] bookshelf = gmv.getCurrentPlayer().getBookshelf();
        int depth = gmv.getCurrentPlayer().getTakenTiles().size()-1;
        ArrayList<Integer> availableColumns = new ArrayList<>();
        for(int x = 0; x < bookshelf[0].length; x++){
            if(bookshelf[depth][x].getType() == ItemTileType.EMPTY) availableColumns.add(x);
        }
        Collections.shuffle(availableColumns);
        viewManager.insertRequest(availableColumns.get(0));
    }

    @Override
    public void startUI() {
    }

    @Override
    public void stopUI() {
    }
}
