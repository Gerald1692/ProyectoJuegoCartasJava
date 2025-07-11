package ejemplog.proyectocarta_mg;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
private void showSettings() {
    try {
        // Cargar el FXML de ajustes
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Parent root = loader.load();
        
        // Crear nueva escena
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Ajustes");
        stage.setScene(scene);
        
        // Configurar como ventana modal
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta("Error", "No se pudo cargar la ventana de ajustes.");
    }
}
    @FXML
    private void switchToSecondary() throws IOException {
        String nombre = textonombre.getText().trim();
        if (nombre.isEmpty()) {
            mostrarAlerta("Error", "Debe ingresar un nombre");
            return;
        }
        App.resetGame();
        App.playerName = nombre;
        App.setRoot("secondary");
    }

    @FXML
    private void showReplays() {
        mostrarAlerta("Repeticiones", "Funcionalidad en desarrollo");
    }

   

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}