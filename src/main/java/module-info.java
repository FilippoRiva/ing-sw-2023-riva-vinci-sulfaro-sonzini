module ingsw.myshelfie {
    //module dependencies
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;
    requires java.logging;

    //making network accessible to java-rmi
    exports it.polimi.myshelfie;
    exports it.polimi.myshelfie.controller.interfaces;
    exports it.polimi.myshelfie.model;
    exports it.polimi.myshelfie.model.chatting;
    exports it.polimi.myshelfie.model.exceptions;
    exports it.polimi.myshelfie.model.events;
    exports it.polimi.myshelfie.model.modelviews;
    exports it.polimi.myshelfie.network;
    exports it.polimi.myshelfie.view;
    exports it.polimi.myshelfie.view.graphical;
    exports it.polimi.myshelfie.view.events;
    exports it.polimi.myshelfie.model.interfaces;
    exports it.polimi.myshelfie.utils;
    exports it.polimi.myshelfie.model.goals;
    exports it.polimi.myshelfie.view.graphical.controllers;
    exports it.polimi.myshelfie.view.graphical.controllers.game;
    exports it.polimi.myshelfie.view.graphical.controllers.pregame;

    //making graphical UI open for javafx
    opens it.polimi.myshelfie.view.graphical.controllers;
    opens it.polimi.myshelfie.view.graphical.controllers.pregame;
    opens it.polimi.myshelfie.view.graphical.controllers.game;

    exports it.polimi.myshelfie.view.graphical.components;
}