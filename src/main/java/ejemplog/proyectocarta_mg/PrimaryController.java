package ejemplog.proyectocarta_mg;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class PrimaryController {
    @FXML
    private Button BtnEntrar;
    @FXML
    private TextArea textonombre;
    @FXML
    private Button btnReplays;
    @FXML
    private Button btnSettings;

    @FXML
    private void switchToSecondary() throws IOException {
        String nombre = textonombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un nombre");
            return;
        }
        App.playerName = nombre;
        App.setRoot("secondary");
    }

    @FXML
    private void showReplays() {
        mostrarAlerta("Repeticiones", "Funcionalidad en desarrollo");
    }

    @FXML
    private void showSettings() {
        mostrarAlerta("Ajustes", "Funcionalidad en desarrollo");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}