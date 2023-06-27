package it.polimi.myshelfie.model.chatting;

import it.polimi.myshelfie.model.Player;
import it.polimi.myshelfie.model.events.ChatEvent;
import it.polimi.myshelfie.model.modelviews.PlayerChatModelView;
import it.polimi.myshelfie.utils.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatSystemTest {

    ChatSystem cs;

    @BeforeEach
    void setUp() {
        //adding two players
        cs = new ChatSystem();
        cs.addPlayerToChat("Pippo");
        assertEquals(cs.getPlayerChats().size(), 1);
        assertTrue(cs.getPlayerChats().containsKey("Pippo"));
        cs.addPlayerToChat("Luca");
        assertEquals(cs.getPlayerChats().size(), 2);
        assertTrue(cs.getPlayerChats().containsKey("Pippo"));
        assertTrue(cs.getPlayerChats().containsKey("Luca"));
        cs.addPlayerToChat("Andrea");
        assertEquals(cs.getPlayerChats().size(), 3);
        assertTrue(cs.getPlayerChats().containsKey("Pippo"));
        assertTrue(cs.getPlayerChats().containsKey("Luca"));
        assertTrue(cs.getPlayerChats().containsKey("Andrea"));
    }

    @Test
    void chatObserverTest() {
        final PlayerChatModelView[] views = new PlayerChatModelView[2];
        final ChatEvent[] events = new ChatEvent[2];
        Observer<ChatEvent, PlayerChatModelView> obs1 = new Observer<ChatEvent, PlayerChatModelView>() {
            @Override
            public void update(ChatEvent evt, PlayerChatModelView chatModelView) {
                views[0] = chatModelView;
                events[0] = evt;
            }
        };
        cs.addChatObserver("Luca", obs1);
        Observer<ChatEvent, PlayerChatModelView> obs2 = new Observer<ChatEvent, PlayerChatModelView>() {
            @Override
            public void update(ChatEvent evt, PlayerChatModelView chatModelView) {
                views[1] = chatModelView;
                events[1] = evt;
            }
        };
        cs.addChatObserver("Pippo", obs2);
        cs.addMessage(new Message("Luca", "Pippo", "ciao"));
        assertNotNull(views[0]);
        assertNotNull(views[0].getLastMessage());
        assertEquals(views[0].getLastMessage(), views[1].getLastMessage());
        assertEquals(events[0], events[1]);
        cs.addMessage(new Message("Pippo", "Luca", "ciao anche a te"));
        assertNotNull(views[0]);
        assertNotNull(views[0].getLastMessage());
        assertEquals(views[0].getLastMessage(), views[1].getLastMessage());
        assertEquals(events[0], events[1]);
        views[0] = null;
        views[1] = null;
        events[0] = null;
        events[1] = null;
        cs.removeChatObserver("Luca", obs1);
        cs.removeChatObserver("Pippo", obs2);
        cs.addMessage(new Message("Pippo", "Luca", "ciao"));
        assertNull(views[0]);
        assertNull(views[1]);
        assertNull(events[0]);
        assertNull(events[1]);
        Messages messages = new Messages();
    }

    @Test
    void addMessage() {
        Message msg;
        Player p1 = new Player("Pippo");
        Player p2 = new Player("Luca");
        Player p3 = new Player("Andrea");
        //cs.addPlayerToChat(p1.getName());
        //cs.addPlayerToChat(p2.getName());
        //cs.addPlayerToChat(p3.getName());

        //right message p1->p2
        msg = new Message(p1.getName(), p2.getName(), "msg2");
        cs.addMessage(msg);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().size(), 1);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().get(0).getMessage(), "msg2");
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().get(0).getSender(), p1.getName());
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().get(0).getReceiver(), p2.getName());
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().size(), 1);
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().get(0).getMessage(), "msg2");
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().get(0).getSender(), p1.getName());
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().get(0).getReceiver(), p2.getName());
        assertEquals(cs.getPlayerChats().get(p3.getName()).getMessages().size(), 0);
        //sender=receiver ==> ignored
        msg = new Message(p1.getName(), p1.getName(), "msg0");
        cs.addMessage(msg);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().size(), 1);
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().size(), 1);
        assertEquals(cs.getPlayerChats().get(p3.getName()).getMessages().size(), 0);
        //receiver doesn't exists
        msg = new Message(p1.getName(), Messages.fromNobody, "msg1");
        cs.addMessage(msg);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().size(), 1);
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().size(), 1);
        assertEquals(cs.getPlayerChats().get(p3.getName()).getMessages().size(), 0);
        //message to everybody
        msg = new Message(p1.getName(), null, "msg3");
        cs.addMessage(msg);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().size(), 2);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().get(1).getMessage(), "msg3");
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().size(), 2);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().get(1).getMessage(), "msg3");
        assertEquals(cs.getPlayerChats().get(p3.getName()).getMessages().size(), 1);
        assertEquals(cs.getPlayerChats().get(p3.getName()).getMessages().get(0).getMessage(), "msg3");
        //message from nobody ==> ignore
        msg = new Message(null, p1.getName(), "msg4");
        cs.addMessage(msg);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().size(), 2);
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().size(), 2);
        assertEquals(cs.getPlayerChats().get(p3.getName()).getMessages().size(), 1);
        //sender doesn't exists ==> ignore
        msg = new Message("Marco", p1.getName(), "msg3");
        cs.addMessage(msg);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().size(), 2);
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().size(), 2);
        assertEquals(cs.getPlayerChats().get(p3.getName()).getMessages().size(), 1);
        //empty message ==> ignore
        msg = new Message(p2.getName(), p1.getName(), null);
        cs.addMessage(msg);
        assertEquals(cs.getPlayerChats().get(p1.getName()).getMessages().size(), 2);
        assertEquals(cs.getPlayerChats().get(p2.getName()).getMessages().size(), 2);
        assertEquals(cs.getPlayerChats().get(p3.getName()).getMessages().size(), 1);
        List<Message> messages = cs.getMessages();
        assertEquals(2, messages.size());
    }

    @Test
    void getPlayerChatView() {
        Player p1 = new Player("Pippo");
        Player p2 = new Player("Luca");
        PlayerChatModelView view1 = new PlayerChatModelView(cs.getPlayerChats().get(p1.getName()));
        PlayerChatModelView view3 = new PlayerChatModelView(cs.getPlayerChats().get(p1.getName()));
        assertEquals(view1.getMessages().size(), cs.getPlayerChats().get(p1.getName()).getMessages().size());
        cs.addMessage(new Message(p1.getName(), p2.getName(), "msg1"));
        assertEquals(view1.getLastMessage().getMessage(), "msg1");
    }
}