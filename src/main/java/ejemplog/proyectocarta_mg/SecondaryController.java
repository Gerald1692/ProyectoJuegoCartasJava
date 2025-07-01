package ejemplog.proyectocarta_mg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SecondaryController {

    @FXML private Label Labeltxt;
    @FXML private Label Labeltiempo;
    @FXML private Label Labelvidas;
    @FXML private Button secondaryButton;
  
    
    private Juego juego;
    private List<Button> listaBotones =new ArrayList<>();
    private Map<Button,Carta> mapaBotones = new HashMap<>();
    @FXML
    private Button BTN_02;
    @FXML
    private Button BTN_00;
    @FXML
    private Button BTN_01;
    @FXML
    private Button BTN_03;
    @FXML
    private Button BTN_10;
    @FXML
    private Button BTN_11;
    @FXML
    private Button BTN_12;
    @FXML
    private Button BTN_21;
    @FXML
    private Button BTN_22;
    @FXML
    private Button BTN_23;
    @FXML
    private Button BTN_13;
    @FXML
    private Button BTN_20;

    public void iniciarJuego(Juego juego, Jugador jugador){
    this.juego= juego;
    Labeltxt.setText(jugador.getNombreJugador());
    llenarListaBotones();
    asignarDatosBotones();  
    }
    
    private void llenarListaBotones() {
        listaBotones.add(BTN_00); listaBotones.add(BTN_01);
        listaBotones.add(BTN_02); listaBotones.add(BTN_03);
        listaBotones.add(BTN_10); listaBotones.add(BTN_11);
        listaBotones.add(BTN_12); listaBotones.add(BTN_13);
        listaBotones.add(BTN_20); listaBotones.add(BTN_21);
        listaBotones.add(BTN_22); listaBotones.add(BTN_23);
    }
    
    

    private void asignarDatosBotones(){
        Tablero tablero = juego.getTablero();
        int index = 0;
    
        for(int i= 0; i< tablero.getFilas(); i++){
            for(int j=0; j< tablero.getColumnas();j++){
                Carta carta = tablero.getCarta(i, j);
                Button boton = listaBotones.get(index++);
                
                ImageView view = new ImageView(carta.getImaEspalda());
                view.setFitWidth(80);
                view.setFitHeight(80);
                boton.setGraphic(view);
                
                mapaBotones.put(boton, carta);
                
                int fila =i;
                int columna =j;
                
                boton.setOnAction(e -> {
                    try {
                        manejarSeleccion(fila, columna, boton);
                    } catch (Excepciones.CartaNoVoltearExcepcion ex) {
                        Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }
    }
    private void manejarSeleccion(int fila,int columna, Button boton) throws Excepciones.CartaNoVoltearExcepcion{
        Carta carta = juego.getTablero().getCarta(fila, columna);
        carta.voltearCarta();
        ImageView view = new ImageView(carta.getImagenCarta());
        view.setFitWidth(80);
        view.setFitHeight(80);
        
        boton.setGraphic(view);
        
        juego.seleccionarCarta(fila, columna);
        
        if(juego.isTerminarJuego()){
            mostrarFinJuego();
        }
        actualizarValores();
    }

    private void actualizarValores(){
        Labelvidas.setText(" "+juego.getVidas());
        
    }
    
    private void mostrarFinJuego(){
        for(Button b: listaBotones){
            b.setDisable(true);

        }
        System.out.println("Ganaste");
    }
    
    
    
    @FXML
    private void switchToPrimary() throws IOException {
        
        App.setRoot("primary");
    }

    @FXML
    private void iniciarJuego(ActionEvent event) {
    }
}