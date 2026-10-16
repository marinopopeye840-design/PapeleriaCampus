module ni.edu.uam.papeleriacampus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ni.edu.uam.papeleriacampus to javafx.fxml;
    exports ni.edu.uam.papeleriacampus;
}