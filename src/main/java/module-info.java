module com.example.socialnetworkgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.socialnetworkgui to javafx.fxml;
    exports com.example.socialnetworkgui;
    exports com.example.socialnetworkgui.controllers;
    opens com.example.socialnetworkgui.controllers to javafx.fxml;
}