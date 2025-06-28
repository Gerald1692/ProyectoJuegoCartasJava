package ejemplog.proyectocarta_mg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class SecondaryController {

    @FXML private Label Labeltxt;
    @FXML private Label Labeltiempo;
    @FXML private Label Labelvidas;
    @FXML private Button secondaryButton;
    @FXML private Button BtnX2_Y0, BtnX0_Y1, BtnX0_Y3, BtnX0_Y2, BtnX2_Y2;
    @FXML private Button BtnX2_Y3, BtnX2_Y1, BtnX1_Y1, BtnX1_Y0, BtnX1_Y3;
    @FXML private Button BtnX1_Y2, BtnX0_Y0;

    private List<String> cardValues = new ArrayList<>();
    private Button firstCard = null;
    private Button secondCard = null;
    private int pairsFound = 0;
    private int  lives =3 ;
    private int timeSeconds = 120;
    private Timeline timeline;

    @FXML
    public void initialize() {
        Labeltxt.setText(App.playerName);
        setupGame();
        
    }

    private void setupGame() {
        Labelvidas.setText("Vidas: " + lives);
        Labeltiempo.setText("Tiempo: " + timeSeconds);
        
        String[] symbols = {"girasol", "cerebro", "zombie", "planta", "sol", "luna"};
        for (String symbol : symbols) {
            cardValues.add(symbol);
            cardValues.add(symbol);
        }
        Collections.shuffle(cardValues);
        
        Button[] buttons = {
            BtnX0_Y0, BtnX0_Y1, BtnX0_Y2, BtnX0_Y3,
            BtnX1_Y0, BtnX1_Y1, BtnX1_Y2, BtnX1_Y3,
            BtnX2_Y0, BtnX2_Y1, BtnX2_Y2, BtnX2_Y3
        };
        
        for (int i = 0; i < buttons.length; i++) {
            Button btn = buttons[i];
            String value = i < cardValues.size() ? cardValues.get(i) : "";
            btn.setText("");
            btn.setDisable(false);
            btn.setUserData(value);
            btn.setOnAction(e -> handleCardClick(btn));
        }
    }

    private void handleCardClick(Button card) {
        if (!card.getText().isEmpty() || secondCard != null) return;
        
        String value = (String) card.getUserData();
        card.setText(value);
        
        if (firstCard == null) {
            firstCard = card;
        } else {
            secondCard = card;
            checkMatch();
        }
    }

    private void checkMatch() {
        String val1 = (String) firstCard.getUserData();
        String val2 = (String) secondCard.getUserData();
        
        if (val1.equals(val2)) {
            pairsFound++;
            firstCard.setDisable(true);
            secondCard.setDisable(true);
            resetSelection();
            
            if (pairsFound == 6) {
                gameWon();
            }
        }
    }

    private void resetSelection() {
        firstCard = null;
        secondCard = null;
    }

    

    private void gameOver() {
        timeline.stop();
        showAlert("Game Over", "Se acabó el tiempo o las vidas!");
        disableAllCards();
    }

    private void gameWon() {
        timeline.stop();
        showAlert("¡Felicidades!", "¡Ganaste el juego!");
        disableAllCards();
    }

    private void disableAllCards() {
        Button[] buttons = {
            BtnX0_Y0, BtnX0_Y1, BtnX0_Y2, BtnX0_Y3,
            BtnX1_Y0, BtnX1_Y1, BtnX1_Y2, BtnX1_Y3,
            BtnX2_Y0, BtnX2_Y1, BtnX2_Y2, BtnX2_Y3
        };
        for (Button btn : buttons) {
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
        if (timeline != null) {
            timeline.stop();
        }
        App.setRoot("primary");
    }
}