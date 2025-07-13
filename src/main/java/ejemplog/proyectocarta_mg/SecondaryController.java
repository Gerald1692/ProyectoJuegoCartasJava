package ejemplog.proyectocarta_mg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SecondaryController {
    private boolean juegoTerminado = false;
    private Timeline replayTimeline;
    private boolean enReplay = false;
    @FXML
    private Button btnReplay; // Añade esto si quieres un botón manual
    
    @FXML private Label Labeltxt;
    @FXML private Label Labeltiempo;
    @FXML private Label Labelvidas;
    @FXML private Button secondaryButton;
    @FXML private Button BtnX2_Y0, BtnX0_Y1, BtnX0_Y3, BtnX0_Y2, BtnX2_Y2;
    @FXML private Button BtnX2_Y3, BtnX2_Y1, BtnX1_Y1, BtnX1_Y0, BtnX1_Y3;
    @FXML private Button BtnX1_Y2, BtnX0_Y0;

    
    

    @FXML
   

<<<<<<< HEAD
    ////////////////////////////////////////////////
=======

>>>>>>> cb2f79b4b55bfed2f3190510ff47c331d9f58adc

private void handleGuardar() {
    TextInputDialog dialog = new TextInputDialog("Partida1");
    dialog.setTitle("Guardar Partida");
    dialog.setHeaderText("Nombre de la partida:");
    dialog.setContentText("Nombre:");

    Optional<String> result = dialog.showAndWait();
    result.ifPresent(nombre -> {
        try {
            // Pasar el nombre del jugador
            juego.guardarPartidaTxt(nombre, App.playerName);
            mostrarAlerta("Partida Guardada", "Partida guardada como: " + nombre);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    });
}

@FXML
private void handleCargar() {
    // Ruta a la carpeta "CargaPartidas" dentro de resources
    File carpeta = new File("src/main/resources/CargaPartidas");
    if (!carpeta.exists()) {
        carpeta.mkdirs();
    }
    
    File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".txt"));
    
    if (archivos == null || archivos.length == 0) {
        mostrarAlerta("Error", "No hay partidas guardadas");
        return;
    }
    
    ChoiceDialog<File> dialog = new ChoiceDialog<>(archivos[0], Arrays.asList(archivos));
    dialog.setTitle("Cargar Partida");
    dialog.setHeaderText("Seleccione una partida:");
    dialog.setContentText("Partidas:");
    
    Optional<File> result = dialog.showAndWait();
    result.ifPresent(archivo -> {
        String nombre = archivo.getName().replace(".txt", "");
        Juego juegoCargado = Juego.cargarPartidaTxt(nombre);
        
        if (juegoCargado != null) {
            this.juego = juegoCargado;
            actualizarUI();
            mostrarAlerta("Partida Cargada", "Partida cargada: " + nombre);
        }
    });
}
    //////////////////////////////////////////////////////////////////////
    
    
    private Juego juego;
    private Map<Button, Carta> buttonCartaMap = new HashMap<>();
    private List<Button> flippedCards = new ArrayList<>();
    private int score;
    private int lives;
    private int comboCount = 0;
    private static final int COMBO_REQUIRED = 3;
    private Timeline gameTimer;
    private Button[][] buttonsGrid;
    private Button btnRetrocederPaso;
    @FXML
    private Label LabelScore;

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
private void iniciarReplay() {
    // Verificar si el juego ha terminado
    if (!juegoTerminado) {
        mostrarAlerta("Replay no disponible", "El replay solo está disponible después de terminar el juego");
        return;
    }
    // Verificar si hay historial para reproducir
    List<MovimientoReplay> historial = juego != null ? juego.getHistorialReplay() : null;
    if (historial == null || historial.isEmpty()) {
        mostrarAlerta("Replay", "No hay historial para reproducir");
        return;
    }

    // Configurar estado para el replay
    enReplay = true;
    btnReplay.setDisable(true); // Deshabilitar el botón durante el replay
    disableAllCards();
    reiniciarTableroParaReplay();
    
    // Configurar timeline para el replay
    replayTimeline = new Timeline();
    replayTimeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, e -> {}));
    
    // Agregar cada movimiento al timeline
    for (MovimientoReplay movimiento : historial) {
        if (movimiento != null) {
            KeyFrame frame = new KeyFrame(
                Duration.millis(movimiento.getTiempoTranscurrido()),
                e -> reproducirMovimiento(movimiento)
            );
            replayTimeline.getKeyFrames().add(frame);
        }
    }
    
    // Configurar acción final
    replayTimeline.setOnFinished(e -> {
        finalizarReplay();
        btnReplay.setDisable(false); // Volver a habilitar el botón
    });
    
    // Iniciar el replay
    replayTimeline.play();
}
private void reiniciarTableroParaReplay() {
    for (Button[] fila : buttonsGrid) {
        for (Button btn : fila) {
            ImageView iv = (ImageView) btn.getGraphic();
            Carta carta = buttonCartaMap.get(btn);
            iv.setImage(carta.getImaEspalda());
            btn.setDisable(false); // Habilitar para la animación
            btn.setStyle("");
        }
    }
}

private void reproducirMovimiento(MovimientoReplay movimiento) {
    Button btn = buttonsGrid[movimiento.getFila()][movimiento.getColumna()];
    Carta carta = buttonCartaMap.get(btn);
    
    switch(movimiento.getActionType()) {
        case SHOW:
            flipCard(btn, true);
            break;
        case HIDE:
            flipCard(btn, false);
            break;
        case REMOVE_PENALTY:
            // Animación especial para quitar castigo
            removePenaltyAnimation(btn, carta);
            break;
    }
}

private void removePenaltyAnimation(Button btn, Carta carta) {
    // 1. Primero mostramos la carta (como SHOW)
    flipCard(btn, true);
    
    // 2. Luego una animación especial para indicar que fue removida
    PauseTransition pause = new PauseTransition(Duration.millis(500));
    pause.setOnFinished(e -> {
        // Destacar visualmente la carta de castigo removida
        btn.setStyle("-fx-border-color: gold; -fx-border-width: 3;");
        
        // Opcional: Efecto de desvanecimiento
        FadeTransition fade = new FadeTransition(Duration.millis(1000), btn);
        fade.setFromValue(1.0);
        fade.setToValue(0.5);
        fade.play();
    });
    pause.play();
}

private void finalizarReplay() {
    enReplay = false;
    // Mostrar el estado final del tablero
    actualizarUI();
} 
   
private void actualizarUI() {
    Labeltxt.setText(App.playerName);
    Labelvidas.setText("Vidas: " + juego.getVidas());
    LabelScore.setText("Puntos: " + juego.getPuntajeJugador());
    Labeltiempo.setText("Tiempo: " + App.gameDuration);
    
    Tablero tablero = juego.getTablero();
    for (int i = 0; i < tablero.getFilas(); i++) {
        for (int j = 0; j < tablero.getColumnas(); j++) {
            Button btn = buttonsGrid[i][j];
            Carta carta = tablero.getCarta(i, j);
            ImageView iv = (ImageView) btn.getGraphic();
            
            btn.setDisable(false);
            btn.setOpacity(1.0);
            btn.setStyle("");
            
            switch (carta.getEstado()) {
                case Oculta:
                    iv.setImage(carta.getImaEspalda());
                    break;
                case Revelada:
                    iv.setImage(carta.getImaCara());
                    btn.setDisable(true);
                    break;
                case Emparejada:
                    iv.setImage(carta.getImaCara());
                    btn.setDisable(true);
                    btn.setStyle("-fx-border-color: green;");
                    break;
            }
            buttonCartaMap.put(btn, carta);
        }
    }
    
    // Verificar fin del juego solo si:
    // 1. No estamos en replay
    // 2. El juego marca que ha terminado
    // 3. No hemos procesado ya el fin del juego
    if (!enReplay && juego.isTerminarJuego() && !juegoTerminado) {
        disableAllCards();
        if (juego.getVidas() <= 0) {
            gameOver(); // Esto establecerá juegoTerminado = true
        } else {
            gameWon(); // Esto establecerá juegoTerminado = true
        }
    }
    
    // Si el juego ha terminado y no estamos en replay, deshabilitar cartas
    if (juegoTerminado && !enReplay) {
        disableAllCards();
    }
}
    
  private void mostrarAlerta(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();

}

    
    

  
    
    public void initialize() {
        juego = new Juego();
        buttonsGrid = new Button[][]{
            {BtnX0_Y0, BtnX0_Y1, BtnX0_Y2, BtnX0_Y3},
            {BtnX1_Y0, BtnX1_Y1, BtnX1_Y2, BtnX1_Y3},
            {BtnX2_Y0, BtnX2_Y1, BtnX2_Y2, BtnX2_Y3}
        };
        
        lives = juego.getVidas();
        score = juego.getPuntajeJugador();
        Labeltxt.setText(App.playerName);
        Labelvidas.setText("Vidas: " + lives);
        Labeltiempo.setText("Tiempo: " + App.gameDuration);
        LabelScore.setText("Puntos: "+score);
        
        if (btnReplay != null) {
        btnReplay.setDisable(true);
    }
        setupGame();
        startTimer();
        
    }

    private void setupGame() {
        Tablero tablero = juego.getTablero();
        
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Button btn = buttonsGrid[i][j];
                Carta carta = tablero.getCarta(i, j);
                buttonCartaMap.put(btn, carta);
                
                // Configurar imagen inicial (reverso)
                ImageView iv = new ImageView(carta.getImaEspalda());
                iv.setFitHeight(90);
                iv.setFitWidth(60);
                btn.setGraphic(iv);
            }
        }
    }
    
    private void handleCardClick(Button card) {
        if (card.isDisable()) return;
        if (flippedCards.size() >= 2 || flippedCards.contains(card)) return;
        
        flipCard(card, true);
        flippedCards.add(card);
        
        if (flippedCards.size() == 2) {
            checkMatch();
        }
        Carta carta = buttonCartaMap.get(card);
    if (carta.getTipo() == Carta.TipoCarta.Bonus) {
        App.getSoundManager().sonidobonus();
       ;
        
    } else {
        App.getSoundManager().playFlipSound();
    }

    }
    
private void flipCard(Button card, boolean showFront) {
    // No reproducir sonido durante el replay
    if (!enReplay) {
        App.getSoundManager().playFlipSound();
    }

    ImageView iv = (ImageView) card.getGraphic();
    Carta carta = buttonCartaMap.get(card);
    
    // Configurar la animación de volteo
    RotateTransition rt = new RotateTransition(Duration.millis(300), iv);
    rt.setAxis(Rotate.Y_AXIS);
    
    if (showFront) {
        rt.setFromAngle(0);
        rt.setToAngle(180);
    } else {
        rt.setFromAngle(180);
        rt.setToAngle(0);
    }

    // Deshabilitar interacción durante la animación
    card.setDisable(true);

    rt.setOnFinished(e -> {
        try {
            // Cambiar la imagen según el tipo de acción
            if (showFront) {
                iv.setImage(carta.getImaCara());
                
                // Solo actualizar estado si no es replay
                if (!enReplay) {
                    carta.setEstado(Carta.EstadoCarta.Revelada);
                }
            } else {
                iv.setImage(carta.getImaEspalda());
                
                // Solo actualizar estado si no es replay
                if (!enReplay) {
                    carta.setEstado(Carta.EstadoCarta.Oculta);
                }
            }

            // Habilitar solo si no está emparejada y no es replay
            if (!enReplay) {
                card.setDisable(carta.getEstado() == Carta.EstadoCarta.Emparejada);
            } else {
                // Durante replay, mantener habilitado para próximas animaciones
                card.setDisable(false);
            }
        } catch (Exception ex) {
            System.err.println("Error en flipCard: " + ex.getMessage());
        }
    });

    rt.play();
}
    
private void checkMatch() {
    Button btn1 = flippedCards.get(0);
    Button btn2 = flippedCards.get(1);
    
    PauseTransition inicioPause = new PauseTransition(Duration.millis(500));
    inicioPause.setOnFinished(e -> {
        int fila1 = getFila(btn1);
        int columna1 = getColumna(btn1);
        int fila2 = getFila(btn2);
        int columna2 = getColumna(btn2);

        boolean sonPareja = buttonCartaMap.get(btn1).getId().equals(buttonCartaMap.get(btn2).getId());
        
        if (sonPareja) {
            juego.seleccionarCarta(fila1, columna1);
            juego.seleccionarCarta(fila2, columna2);
            
            // ACTUALIZAR UI DESPUÉS DE CAMBIOS
            actualizarUI();
            
            flippedCards.clear();
            
            if (checkWin()) gameWon();
        } else {
            PauseTransition mostrarPareja = new PauseTransition(Duration.millis(500));
            mostrarPareja.setOnFinished(ev -> {
                juego.seleccionarCarta(fila1, columna1);
                juego.seleccionarCarta(fila2, columna2);
                
                // ACTUALIZAR UI DESPUÉS DE CAMBIOS
                actualizarUI();
                
                flipCard(btn1, false);
                flipCard(btn2, false);
                
                flippedCards.clear();
            });
            mostrarPareja.play();
        }
    });
    
    inicioPause.play();
    // ELIMINAR actualizarUI() de aquí
}
    
    private int getFila(Button btn) {
        for (int i = 0; i < buttonsGrid.length; i++) {
            for (int j = 0; j < buttonsGrid[i].length; j++) {
                if (buttonsGrid[i][j] == btn) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private int getColumna(Button btn) {
        for (int i = 0; i < buttonsGrid.length; i++) {
            for (int j = 0; j < buttonsGrid[i].length; j++) {
                if (buttonsGrid[i][j] == btn) {
                    return j;
                }
            }
        }
        return -1;
    }
    
   
    
    private void applyCombo() {
        comboCount = 5;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Combo!");
        alert.setHeaderText(null);
        alert.setContentText("¡Has activado un combo! Se eliminan cartas de castigo");
        alert.showAndWait();
    }
    
    private boolean checkWin() {
        
        return getAllCardButtons().stream().allMatch(Button::isDisable);
    }
    
    private void startTimer() {
        gameTimer = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                App.gameDuration--;
                Labeltiempo.setText("Tiempo: " + App.gameDuration);
                if (App.gameDuration <= 0) {
                    gameOver();
                }
            })
        );
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();
    }
    
 private void gameOver() {
    juegoTerminado = true; // Marcar juego como terminado
    gameTimer.stop();
    int puntajeFinal = juego.getPuntajeJugador();
    Platform.runLater(() -> {
        App.getSoundManager().playLoseSound();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Se acabó el tiempo o las vidas!\nPuntuación final: " + puntajeFinal);
        
        alert.setOnHidden(event -> {
            App.getSoundManager().resumeBackgroundMusic();
            btnReplay.setDisable(false); // Habilitar el botón de replay
        });
        
        alert.showAndWait();
        disableAllCards();
    });
}

private void gameWon() {
    juegoTerminado = true; // Marcar juego como terminado
    gameTimer.stop();
    int puntajeFinal = juego.getPuntajeJugador();
    Platform.runLater(() -> {
        App.getSoundManager().playGameEndSound();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Felicidades!");
        alert.setHeaderText(null);
        alert.setContentText("¡Ganaste el juego con " + puntajeFinal + " puntos! ");
        
        alert.setOnHidden(event -> {
            App.getSoundManager().resumeBackgroundMusic();
            btnReplay.setDisable(false); // Habilitar el botón de replay
        });
        
        alert.showAndWait();
        disableAllCards();
    });
}

    private void disableAllCards() {
        for (Button btn : getAllCardButtons()) {
            btn.setDisable(true);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private List<Button> getAllCardButtons() {
        return List.of(
            BtnX0_Y0, BtnX0_Y1, BtnX0_Y2, BtnX0_Y3,
            BtnX1_Y0, BtnX1_Y1, BtnX1_Y2, BtnX1_Y3,
            BtnX2_Y0, BtnX2_Y1, BtnX2_Y2, BtnX2_Y3
        );
    }

    @FXML
    private void switchToPrimary() throws IOException {
        gameTimer.stop();
        if (gameTimer != null) {
            gameTimer.stop();
        }
        App.resetGame();
        App.setRoot("primary");
    }
    
    // Métodos para manejar clics en botones específicos
    @FXML private void handleBtnX0_Y0() { handleCardClick(BtnX0_Y0); }
    @FXML private void handleBtnX0_Y1() { handleCardClick(BtnX0_Y1); }
    @FXML private void handleBtnX0_Y2() { handleCardClick(BtnX0_Y2); }
    @FXML private void handleBtnX0_Y3() { handleCardClick(BtnX0_Y3); }
    @FXML private void handleBtnX1_Y0() { handleCardClick(BtnX1_Y0); }
    @FXML private void handleBtnX1_Y1() { handleCardClick(BtnX1_Y1); }
    @FXML private void handleBtnX1_Y2() { handleCardClick(BtnX1_Y2); }
    @FXML private void handleBtnX1_Y3() { handleCardClick(BtnX1_Y3); }
    @FXML private void handleBtnX2_Y0() { handleCardClick(BtnX2_Y0); }
    @FXML private void handleBtnX2_Y1() { handleCardClick(BtnX2_Y1); }
    @FXML private void handleBtnX2_Y2() { handleCardClick(BtnX2_Y2); }
    @FXML private void handleBtnX2_Y3() { handleCardClick(BtnX2_Y3); }
}