module wildIsland {
    requires javafx.controls;
    requires javafx.fxml;


    exports wildIsland;
    opens wildIsland to javafx.fxml;
}