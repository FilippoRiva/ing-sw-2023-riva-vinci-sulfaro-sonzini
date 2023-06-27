package it.polimi.myshelfie.model.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExceptionsTest {
    @Test
    public void exceptionsTest() {
        LobbyFullException exc = new LobbyFullException(true);
        assertNotNull(exc.getMessage());
        exc = new LobbyFullException(false);
        assertNotNull(exc.getMessage());
        DisconnectionException discExc = new DisconnectionException("Player");
        assertNotNull(discExc.getMessage());
        LobbyExistenceException lobbyExc = new LobbyExistenceException(true, "Lobby");
        assertNotNull(lobbyExc.getMessage());
        lobbyExc = new LobbyExistenceException(false, "Lobby");
        assertNotNull(lobbyExc.getMessage());
        InvalidParameterException invalidParameterException = new InvalidParameterException("message");
        assertNotNull(invalidParameterException.getMessage());
    }
}