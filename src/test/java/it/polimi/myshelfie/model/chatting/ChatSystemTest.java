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
        //todo
        //todo
    }
    @Test
    void addMessage() {
        Message msg;
        Messages messages = new Messages() {
        };
        //todo
    }

    @Test
    void getPlayerChatView() {
        //todo
    }


    @Test
    void getMessagesTest(){
        Message msgToB = new Message("A","B","Ciao");
        Message msgToA = new Message("B", "A","Ciao anche a te");
        cs.addMessage(msgToB);
        cs.addMessage(msgToA);
        //todo
    }
}