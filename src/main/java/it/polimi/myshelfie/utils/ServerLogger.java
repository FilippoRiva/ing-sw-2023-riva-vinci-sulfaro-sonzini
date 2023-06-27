package it.polimi.myshelfie.utils;

import it.polimi.myshelfie.network.ServerConnectionHandlerImpl;

import java.util.logging.*;

/**
 * Singleton used for server logs
 */
public class ServerLogger {

    public static final Logger logger = Logger.getLogger("ServerLogger");
    private static StreamHandler stream;

    public static void initialize() {
        LogManager.getLogManager().reset();
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s [%1$tc]%n");
        ServerLogger.stream = new StreamHandler(System.out, new SimpleFormatter());
        String setLevel = System.getenv("LOG_LEVEL");
        if (setLevel != null) {
            try {
                Level level = Level.parse(setLevel);
                ServerLogger.logger.setLevel(level);
                stream.setLevel(level);
                System.out.println("LOG_LEVEL set to " + setLevel);
            } catch (IllegalArgumentException e) {
                System.out.println("LOG_LEVEL env variable is not set correctly. Detected value : " + setLevel + "\n" +
                        "Please insert a valid value between : ALL, FINEST, FINER, FINE, CONFIG, INFO, WARNING, SEVERE");
                System.exit(-1);
            }
        } else {
            System.out.println("LOG_LEVEL set to INFO");
            ServerLogger.logger.setLevel(Level.INFO);
            stream.setLevel(Level.INFO);
        }
        ServerLogger.logger.addHandler(stream);
    }

    /**
     * Logs a message to System out if the Level is equal or higher than the set one.
     *
     * @param level   the level
     * @param message the message
     */
    public static void log(Level level, String message) {
        logger.log(level, message);
        stream.flush();
    }

    public static void logClientInfo(ServerConnectionHandlerImpl connectionHandler, String message) {
        String hostnameAndId = String.format("\" %-15s | ID : %-3s \"", connectionHandler.hostName, connectionHandler.clientID);
        ServerLogger.log(Level.INFO, hostnameAndId + " > " + message);
        stream.flush();
    }
}
