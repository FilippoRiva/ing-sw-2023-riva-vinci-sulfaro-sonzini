package it.polimi.myshelfie.view.textual.pages;

import it.polimi.myshelfie.model.chatting.Message;
import it.polimi.myshelfie.model.chatting.Messages;
import it.polimi.myshelfie.model.modelviews.LobbyModelView;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewType;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.Terminal;

import java.util.List;

public class ChatPage extends ScreenPage{
    String[] colors = {
            ANSI.PINK_FG,ANSI.BLUE_FG,ANSI.GREEN_FG,ANSI.LIGHTBLUE_FG, ANSI.YELLOW_FG,ANSI.RED_FG
    };
    String username;
    public ChatPage(Terminal t, ViewBuffer viewBuffer, List<String> inputParameters, String username) {
        super(t, viewBuffer, inputParameters);
        this.username = username;
    }

    @Override
    public void render() {
        t.clearScreen();
        t.centerPrint("CHAT",2);
        LobbyModelView lobby = viewBuffer.getLobbyModelView();
        PlayerChatModelView chat = viewBuffer.getPlayerChatModelView();
        int line = 4;
        if(lobby == null || chat == null || chat.getMessages().size()==0) {
            t.centerPrint("no messages ", 4);
        } else {
            List<Message> messages = chat.getMessages();
            if(messages.size()>29) messages = messages.subList(messages.size()-29,messages.size());
            int idx=0;
            for(Message msg : messages){
                int playerIndex = lobby.getPlayers().indexOf(msg.getSender());
                String color;
                if(playerIndex!=-1) color = colors[lobby.getPlayers().indexOf(msg.getSender())];
                else color = ANSI.GRAY_FG; //if player has left
                if(msg.getReceiver().equals(Messages.toEveryOne)){
                    t.printMessage(
                            color+msg.getSender() +ANSI.RESET+" : "+msg.getMessage(),
                            line+idx,
                            (msg.getSender()+msg.getMessage()).length()+3,
                            msg.getSender().length()+3);
                } else {
                    if(msg.getSender().equals(username)){
                        playerIndex = lobby.getPlayers().indexOf(msg.getReceiver());
                        if(playerIndex!=-1) color = colors[lobby.getPlayers().indexOf(msg.getReceiver())];
                        else color = ANSI.GRAY_FG; //if player has left
                        t.printMessage(
                                "w(to :"+color+msg.getReceiver() +ANSI.RESET+ ") : "+msg.getMessage(),
                                line+idx,
                                (msg.getSender()+msg.getMessage()).length()+10,
                                username.length()+10);
                    }
                    else t.printMessage(
                            "w(from :"+color+msg.getSender() +ANSI.RESET+ ") : "+msg.getMessage(),
                            line+idx,
                            (msg.getSender()+msg.getMessage()).length()+12,
                            username.length()+12);
                }
                idx++;
            }
        }
        if(viewBuffer.getViewType() == ViewType.GAME) t.printAction("help, game, goals");
        if(viewBuffer.getViewType() == ViewType.LOBBY) t.printAction("help, lobby");
        printBorder();
    }
}
