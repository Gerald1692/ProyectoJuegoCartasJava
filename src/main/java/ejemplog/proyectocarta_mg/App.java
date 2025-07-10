package ejemplog.proyectocarta_mg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    public static String playerName;
    public static int gameDuration = 100;
    private static Scene scene;
    private static SoundManager soundManager;
    
    public static SoundManager getSoundManager() {
        return soundManager;
    }

    @Override
    public void start(Stage stage) throws IOException {
        soundManager = new SoundManager();
        soundManager.playBackgroundMusic();
        
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Provigio - Juego de Memoria");
        
        // Detener todos los sonidos al cerrar la ventana
        stage.setOnCloseRequest(event -> {
            soundManager.stopAllSounds();
        });
        
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}