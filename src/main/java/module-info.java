module ejemplog.proyectocarta_mg {
    requires javafx.controls;
    requires javafx.fxml;

    opens ejemplog.proyectocarta_mg to javafx.fxml;
    exports ejemplog.proyectocarta_mg;
}
