package ejemplog.proyectocarta_mg;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;

public class SettingsController {
    @FXML private Slider volumeSlider;
    @FXML private Button btnApply; // Añadido el botón

    @FXML
    private void initialize() {
        // Configurar volumen inicial
        volumeSlider.setValue(App.getSoundManager().getVolume());
        
        // Aplicar cambios al presionar el botón
        btnApply.setOnAction(event -> applySettings());
    }

    private void applySettings() {
        // Actualizar volumen
        App.getSoundManager().setVolume(volumeSlider.getValue());
        
        // Cerrar la ventana
        btnApply.getScene().getWindow().hide();
    }
}