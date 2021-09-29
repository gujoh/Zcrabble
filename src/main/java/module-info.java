module com.zcrabblers.zcrabble {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires junit;

    exports com.zcrabblers.zcrabble.Tests to junit;
    opens com.zcrabblers.zcrabble to javafx.fxml;
    opens com.zcrabblers.zcrabble.Controller to javafx.fxml;
    exports com.zcrabblers.zcrabble;
    exports com.zcrabblers.zcrabble.View;
    exports com.zcrabblers.zcrabble.Controller;
    exports com.zcrabblers.zcrabble.Model;
}