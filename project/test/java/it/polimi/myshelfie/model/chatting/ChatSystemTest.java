package it.polimi.myshelfie.model.chatting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatSystemTest {
    ArrayList<Message> messagesToA = new ArrayList<>();
    ArrayList<Message> messagesToB = new ArrayList<>();
    ChatSystem cs;

    @BeforeEach
    void setUp() {
        //adding two players
        cs = new ChatSystem();
        cs.addPlayerToChat("A", (o, arg) -> {
            PlayerChatView pcw = (PlayerChatView)o  ;
            messagesToA.add(pcw.getLastMessage());
        });
        cs.addPlayerToChat("B", (o, arg) -> {
            PlayerChatView pcw = (PlayerChatView)o  ;
            messagesToB.add(pcw.getLastMessage());
        });
    }
    @Test
    void addMessage() {
        Message msg;

        //testing wrong messages
        msg = new Message(Messages.fromNobody,"","");
        cs.addMessage(msg);
        assertEquals(0, cs.getMessages().size(), "Message should be discarded");

        //testing private message
        msg = new Message("A","B","Ciao");
        cs.addMessage(msg);
        assertEquals(0, messagesToA.size(), "A should not receive any message");
        assertEquals("Ciao",messagesToB.get(0).getMessage(),"Ciao should be received by B");

        //testing Messages.toEveryone messages
        messagesToA.clear();
        messagesToB.clear();
        msg = new Message("A", Messages.toEveryOne,"Ciao");
        cs.addMessage(msg);
        assertEquals("Ciao", messagesToA.get(0).getMessage(),"Ciao should be received by A");
        assertEquals("Ciao",messagesToB.get(0).getMessage(),"Ciao should be received by B");
        msg = new Message("B",Messages.toEveryOne,"Ciao anche a te");
        cs.addMessage(msg);
        assertEquals("Ciao anche a te",messagesToA.get(1).getMessage(),"Ciao anche a te should be received by A");
        assertEquals("Ciao anche a te",messagesToB.get(1).getMessage(),"Ciao anche a te should be received by B");
    }

    @Test
    void getPlayerChatView() {
        assertNull(cs.getPlayerChatView("Giorgio"), "Giorgio is not a player in this cs");
        assertNotNull(cs.getPlayerChatView("A"), "A should return his chatView");
    }


    @Test
    void getMessagesTest(){
        Message msgToB = new Message("A","B","Ciao");
        Message msgToA = new Message("B", "A","Ciao anche a te");
        cs.addMessage(msgToB);
        cs.addMessage(msgToA);
        PlayerChatView pcwA = cs.getPlayerChatView("A");
        PlayerChatView pcwB = cs.getPlayerChatView("B");
        assertEquals(pcwA.getMessages().get(0), msgToA);
        assertEquals(pcwB.getMessages().get(0), msgToB);
    }
}