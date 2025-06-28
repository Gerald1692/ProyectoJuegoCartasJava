package ejemplog.proyectocarta_mg.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FlowController {
    private static FlowController INSTANCE = new FlowController();
    private static Stage mainStage;
    private static ResourceBundle idioma;
    private static final HashMap<String, FXMLLoader> loaders = new HashMap<>();

    private FlowController() {}

    public static FlowController getInstance() {
        return INSTANCE;
    }

    public void initialize(Stage stage, ResourceBundle idioma) {
        FlowController.mainStage = stage;
        FlowController.idioma = idioma;
    }

    // ... otros métodos ...  
    
    public void exit() {
        if (mainStage != null) {
            mainStage.close();
        }
    }
}