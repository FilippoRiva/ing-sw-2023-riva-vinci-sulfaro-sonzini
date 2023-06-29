package it.polimi.myshelfie.view;


/**
 * Represents all the states of the view.
 *                                       <ul>
 *                                           <li>INIT</li>
 *                                           <li>JOIN</li>
 *                                           <li>CREATE</li>
 *                                           <li>LOBBY</li>
 *                                           <li>GAME</li>
 *                                           <li>CHAT</li>
 *                                           <li>GOALS</li>
 *                                           <li>HELP</li>
 *                                           <li>DISCONNECTION</li>
 *                                           <li>WIN</li>
 *                                           <li>STOPPED</li>
 *                                       </ul>
 */
public enum InternalViewState {
    INIT,
    JOIN,
    CREATE,
    LOBBY,
    GAME,
    CHAT,
    GOALS,
    HELP,
    DISCONNECTION,
    WIN,
    STOPPED
}
