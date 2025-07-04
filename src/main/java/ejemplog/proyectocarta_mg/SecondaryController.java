package ejemplog.proyectocarta_mg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class SecondaryController {
    @FXML private Label Labeltxt;
    @FXML private Label Labeltiempo;
    @FXML private Label Labelvidas;
    @FXML private Button secondaryButton;
    @FXML private Button BtnX2_Y0, BtnX0_Y1, BtnX0_Y3, BtnX0_Y2, BtnX2_Y2;
    @FXML private Button BtnX2_Y3, BtnX2_Y1, BtnX1_Y1, BtnX1_Y0, BtnX1_Y3;
    @FXML private Button BtnX1_Y2, BtnX0_Y0;
    
    private Juego juego;
    private Map<Button, Carta> buttonCartaMap = new HashMap<>();
    private List<Button> flippedCards = new ArrayList<>();
    private int score;
    private int lives;
    private int comboCount = 0;
    private static final int COMBO_REQUIRED = 3;
    private Timeline gameTimer;
    private Button[][] buttonsGrid;
    @FXML
    private Button BtnGuardar;
    @FXML
    private Button btnRetrocederPaso;
    @FXML
    private Label LabelScore;

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
    }
    
    private void flipCard(Button card, boolean showFront) {
        ImageView iv = (ImageView) card.getGraphic();
        RotateTransition rt = new RotateTransition(Duration.millis(500), iv);
        
        rt.setAxis(Rotate.Y_AXIS);
        rt.setFromAngle(showFront ? 0 : 180);
        rt.setToAngle(showFront ? 180 : 0);
        
        rt.setOnFinished(e -> {
            Carta carta = buttonCartaMap.get(card);
            try {
                if (showFront) {
                    carta.voltearCarta(); // Usa el método de la carta
                    iv.setImage(carta.getImagenCarta());
                } else {
                    carta.setEstado(Carta.EstadoCarta.Oculta);
                    carta.setImagenCarta(carta.getImaEspalda());
                    iv.setImage(carta.getImagenCarta());
                }
            } catch (Exception ex) {
                System.err.println("Error cambiando imagen: " + ex.getMessage());
            }
        });
        
        rt.play();
    }
    
   private void checkMatch() {
    Button btn1 = flippedCards.get(0);
    Button btn2 = flippedCards.get(1);
    
    PauseTransition initialPause = new PauseTransition(Duration.millis(500));
    initialPause.setOnFinished(e -> {
        int fila1 = getFila(btn1);
        int columna1 = getColumna(btn1);
        int fila2 = getFila(btn2);
        int columna2 = getColumna(btn2);

        // Procesar selección de cartas
        juego.seleccionarCarta(fila1, columna1);
        juego.seleccionarCarta(fila2, columna2);
        
        // Actualizar estado del juego
        score = juego.getPuntajeJugador();
        lives = juego.getVidas();
        
        // Actualizar UI inmediatamente
        Labelvidas.setText("Vidas: " + lives);
        LabelScore.setText("Puntos: " + score);
        
        if (juego.isTerminarJuego()) {
            if (lives <= 0) {
                gameOver();
            } else {
                gameWon();
            }
        } else {
            // Si no es pareja, voltear de nuevo
            if (!buttonCartaMap.get(btn1).getId().equals(buttonCartaMap.get(btn2).getId())) {
                PauseTransition flipBackPause = new PauseTransition(Duration.seconds(1));
                flipBackPause.setOnFinished(ev -> {
                    flipCard(btn1, false);
                    flipCard(btn2, false);
                    flippedCards.clear();
                });
                flipBackPause.play();
            } else {
                // Si es pareja, deshabilitar
                btn1.setDisable(true);
                btn2.setDisable(true);
                flippedCards.clear();
                
                // Verificar si el jugador ganó
                if (checkWin()) {
                    gameWon();
                }
            }
        }
    });
    initialPause.play();
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
    
    private void updateScore(int points) {
        Labelvidas.setText("Puntos: " + score); // Asumiendo que tienes un Label para puntos
    }
    
    private void updateLives() {
        Labelvidas.setText("Vidas: " + lives);
        if (lives <= 0) {
            gameOver();
        }
    }
    
    private void applyCombo() {
        comboCount = 12;
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
        gameTimer.stop();
        showAlert("Game Over", "Se acabó el tiempo o las vidas!\nPuntuación final: " + score);
        disableAllCards();
    }

    private void gameWon() {
        gameTimer.stop();
        showAlert("¡Felicidades!", "¡Ganaste el juego con " + score + " puntos!");
        disableAllCards();
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
        if (gameTimer != null) {
            gameTimer.stop();
        }
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