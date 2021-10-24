module com.zcrabblers.zcrabble {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires junit;

    opens com.zcrabblers.zcrabble to javafx.fxml;
    opens com.zcrabblers.zcrabble.controller to javafx.fxml;
    exports com.zcrabblers.zcrabble;
    exports com.zcrabblers.zcrabble.controller;
    exports com.zcrabblers.zcrabble.model;
    exports com.zcrabblers.zcrabble.utils;
    exports com.zcrabblers.zcrabble.model.gameBoard;
    exports com.zcrabblers.zcrabble.model.observers;
    exports com.zcrabblers.zcrabble.model.players;

}