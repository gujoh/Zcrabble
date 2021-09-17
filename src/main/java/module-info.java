module com.zcrabblers.zcrabble {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.zcrabblers.zcrabble to javafx.fxml;
    opens com.zcrabblers.zcrabble.Controller to javafx.fxml;
    exports com.zcrabblers.zcrabble;
    exports com.zcrabblers.zcrabble.Controller;
}