module ni.edu.uam.papeleriacampus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ni.edu.uam.papeleriacampus to javafx.fxml;
    opens ni.edu.uam.papeleriacampus.controller to javafx.fxml;
    opens ni.edu.uam.papeleriacampus.model to javafx.base;

    exports ni.edu.uam.papeleriacampus;
    exports ni.edu.uam.papeleriacampus.controller;
    exports ni.edu.uam.papeleriacampus.model;
    exports ni.edu.uam.papeleriacampus.dao;
    exports ni.edu.uam.papeleriacampus.database;
}