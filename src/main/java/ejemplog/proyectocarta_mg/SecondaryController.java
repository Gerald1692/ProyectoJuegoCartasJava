package ejemplog.proyectocarta_mg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.image.Image;
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
    
    private Map<Button, Carta> buttonCartaMap = new HashMap<>();
    private List<Carta> todasLasCartas = new ArrayList<>();
    private List<Button> flippedCards = new ArrayList<>();
    private int score = 0;
    private int lives = 3;
    private int comboCount = 0;
    private static final int COMBO_REQUIRED = 3;
    private Timeline gameTimer;

    @FXML
    public void initialize() {
        Labeltxt.setText(App.playerName);
        Labelvidas.setText("Vidas: " + lives);
        Labeltiempo.setText("Tiempo: " + App.gameDuration);
        
        inicializarCartas();
        setupGame();
        startTimer();
    }
    
    private void inicializarCartas() {
        // Crear cartas normales (pares)
        String[] simbolos = {"girasol", "cerebro", "zombie", "planta", "sol", "luna"};
        for (String simbolo : simbolos) {
            todasLasCartas.add(new CartaNormal(simbolo));
            todasLasCartas.add(new CartaNormal(simbolo)); // Par
        }
        
        // Mezclar las cartas
        Collections.shuffle(todasLasCartas);
    }

    private void setupGame() {
        List<Button> botones = getAllCardButtons();
        
        // Asignar cartas a botones
        for (int i = 0; i < botones.size(); i++) {
            Button btn = botones.get(i);
            Carta carta = todasLasCartas.get(i);
            buttonCartaMap.put(btn, carta);
            
            // Configurar imagen inicial (reverso)
            ImageView iv = new ImageView(carta.getImaEspalda());
            iv.setFitHeight(90);
            iv.setFitWidth(60);
            btn.setGraphic(iv);
        }
    }
    
    private List<Button> getAllCardButtons() {
        return List.of(
            BtnX0_Y0, BtnX0_Y1, BtnX0_Y2, BtnX0_Y3,
            BtnX1_Y0, BtnX1_Y1, BtnX1_Y2, BtnX1_Y3,
            BtnX2_Y0, BtnX2_Y1, BtnX2_Y2, BtnX2_Y3
        );
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
                iv.setImage(carta.getImaCara());
            } else {
                // Usar la nueva imagen de reverso
                iv.setImage(carta.getImaEspalda());
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
        Carta carta1 = buttonCartaMap.get(btn1);
        Carta carta2 = buttonCartaMap.get(btn2);
        
        if (carta1.getId().equals(carta2.getId())) {
            // Par correcto
            carta1.setEstado(Carta.EstadoCarta.Emparejada);
            carta2.setEstado(Carta.EstadoCarta.Emparejada);
            
            // Calcular puntos según tipo de carta
            int puntos = carta1.obtenerPuntos();
            updateScore(puntos);
            
            // Deshabilitar cartas emparejadas
            btn1.setDisable(true);
            btn2.setDisable(true);
            
            flippedCards.clear();
            
            // Verificar si ganó
            if (checkWin()) {
                gameWon();
            }
        } else {
            lives--;
            comboCount = 0; // Resetear combo
            updateLives();
            
            // Voltear cartas después de un retraso
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                flipCard(btn1, false);
                flipCard(btn2, false);
                flippedCards.clear();
            });
            pause.play();
        }
    }
    
    private void updateScore(int points) {
        score += points;
        // Actualizar UI con el puntaje
        System.out.println("Puntos: " + score);
    }
    
    private void updateLives() {
        Labelvidas.setText("Vidas: " + lives);
        if (lives <= 0) {
            gameOver();
        }
    }
    
    private void applyCombo() {
        comboCount = 0;
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