module ejemplog.proyectocarta_mg {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens ejemplog.proyectocarta_mg to javafx.fxml, com.google.gson;
    exports ejemplog.proyectocarta_mg;
}