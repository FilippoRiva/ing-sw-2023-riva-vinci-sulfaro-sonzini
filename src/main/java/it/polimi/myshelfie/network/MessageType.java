package it.polimi.myshelfie.network;

/**
 * Represents the type of Network messages.
 *                                         <ul>
 *                                             <li>MODEL_MESSAGE</li>
 *                                             <li>CHAT_MESSAGE</li>
 *                                             <li>LOBBY_MESSAGE</li>
 *                                             <li>GAME_MESSAGE</li>
 *                                             <li>EXCEPTION_MESSAGE</li>
 *                                             <li>SELECT_VIEW_MESSAGE</li>
 *                                             <li>VIEW_MESSAGE</li>
 *                                             <li>PING</li>
 *                                         </ul>
 */
public enum MessageType {
    MODEL_MESSAGE,
    CHAT_MESSAGE,
    LOBBY_MESSAGE,
    GAME_MESSAGE,
    EXCEPTION_MESSAGE,
    SELECT_VIEW_MESSAGE,
    VIEW_MESSAGE,
    PING
}
