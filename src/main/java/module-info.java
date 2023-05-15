module ingsw.myshelfie {
    //module dependencies
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;
    exports it.polimi.myshelfie.network; //making network accessible to java-rmi
    opens it.polimi.myshelfie.view.graphical; //making graphical UI open for javafx
}