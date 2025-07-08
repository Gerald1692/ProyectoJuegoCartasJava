/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Stack;

public final class Juego implements Serializable {
    private static final long serialVersionUID = 1L; // Corregido: serialVersionUID
    private static final int VidasI = 30;

    
    private Tablero tablero;
    private int puntajeJugador;
    private Carta primerSeleccion;
    private Carta segundaSeleccion;
    private int vidas;
    private boolean terminarJuego;
    private int aciertos = 0;
    private Jugador nomJugador;
    private final Stack<Movimiento> historialMovimientos;

    public Juego(Tablero tablero, int puntajeJugador, Carta primerSeleccion, Carta segundaSeleccion, int vidas, boolean terminarJuego, Jugador nomJugador) {
        this.historialMovimientos = new Stack<>();
        this.tablero = tablero;
        this.puntajeJugador = puntajeJugador;
        this.primerSeleccion = primerSeleccion;
        this.segundaSeleccion = segundaSeleccion;
        this.vidas = vidas;
        this.terminarJuego = terminarJuego;
        this.nomJugador = nomJugador;
    }

    public Juego() {
        this.historialMovimientos = new Stack<>();
        iniciarJuego();
    }
    
    public void iniciarJuego() {
        tablero = new Tablero();
        puntajeJugador = 0;
        vidas = VidasI;
        terminarJuego = false;
        primerSeleccion = null;
        segundaSeleccion = null;
        aciertos = 0;        
    }

    public void seleccionarCarta(int fila, int columna) {
        if (terminarJuego) return;
        
        Carta cartaSelect = tablero.getCarta(fila, columna);
        
        try {
            cartaSelect.voltearCarta();
            
            if (primerSeleccion == null) {
                primerSeleccion = cartaSelect;
            } else if (segundaSeleccion == null) {
                segundaSeleccion = cartaSelect;
                verificarPareja();
            }
        } catch (Excepciones.CartaNoVoltearExcepcion e) {
            System.out.println("No se puede voltear: " + e.getMessage());
        }
    }
    
    private void verificarPareja() {
        if (primerSeleccion.getId().equals(segundaSeleccion.getId())) {
            primerSeleccion.setEstado(Carta.EstadoCarta.Emparejada);
            segundaSeleccion.setEstado(Carta.EstadoCarta.Emparejada);
            puntajeJugador += primerSeleccion.obtenerPuntos();
            aciertos++;
            
            if (aciertos == 4) { // Ejemplo: cada 3 aciertos? Ajustar según reglas
                quitarCastigos();
                aciertos = 0;
            }
        } else {
            vidas--;
            
            if (vidas <= 0) {
                terminarJuego = true;
            } else {
                // Volver a ocultar
                primerSeleccion.setEstado(Carta.EstadoCarta.Oculta);
                primerSeleccion.setImagenCarta(primerSeleccion.getImaEspalda());
                segundaSeleccion.setEstado(Carta.EstadoCarta.Oculta);
                segundaSeleccion.setImagenCarta(segundaSeleccion.getImaEspalda());
            }
        }
        primerSeleccion = null;
        segundaSeleccion = null;
        verificarFinJuego();
    }
    
    private void verificarFinJuego() {
        boolean todasEmparejadas = true;
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Carta c = tablero.getCarta(i, j);
                if (c.getEstado() != Carta.EstadoCarta.Emparejada) {
                    todasEmparejadas = false;
                    break;
                }
            }
        }
        if (todasEmparejadas) {
            terminarJuego = true;
        }
    }
    
    public void retroceder() {
        if (historialMovimientos.isEmpty()) {
            throw new Excepciones.sinMovimientosAnt("No hay movimientos anteriores");
        }
        Movimiento ultimo = historialMovimientos.pop();
        ultimo.getCarta1().setEstado(ultimo.getEstadoAnterior1());
        if (ultimo.getCarta2() != null) {
            ultimo.getCarta2().setEstado(ultimo.getEstadoAnterior2());
        }
        puntajeJugador = ultimo.getPuntajeAnterior();
        vidas = ultimo.getVidaAterior();
        primerSeleccion = null;
        segundaSeleccion = null;   
    }
  
    private void quitarCastigos() {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Carta c = tablero.getCarta(i, j);
                if (c.getTipo() == Carta.TipoCarta.Castigo && c.getEstado() == Carta.EstadoCarta.Oculta) {
                    try {
                        c.voltearCarta(); // Revelar castigos
                    } catch (Excepciones.CartaNoVoltearExcepcion e) {
                        
                    }
                }
            }
        }
    }
  
    public void guardarPartida(String archivo) {
        
    }
  
    public static Juego cargarPartida(String archivo) {
        
        return null;
        
    }

    // Getters y setters
    public Tablero getTablero() {
        return tablero;
    }

    public int getPuntajeJugador() {
        return puntajeJugador;
    }

    public Carta getPrimerSeleccion() {
        return primerSeleccion;
    }

    public Carta getSegundaSeleccion() {
        return segundaSeleccion;
    }

    public int getVidas() {
        return vidas;
    }

    public boolean isTerminarJuego() {
        return terminarJuego;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public void setPuntajeJugador(int puntajeJugador) {
        this.puntajeJugador = puntajeJugador;
    }

    public void setPrimerSeleccion(Carta primerSeleccion) {
        this.primerSeleccion = primerSeleccion;
    }

    public void setSegundaSeleccion(Carta segundaSeleccion) {
        this.segundaSeleccion = segundaSeleccion;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void setTerminarJuego(boolean terminarJuego) {
        this.terminarJuego = terminarJuego;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

   
}