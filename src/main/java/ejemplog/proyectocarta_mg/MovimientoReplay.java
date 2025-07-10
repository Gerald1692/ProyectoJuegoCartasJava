/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import java.io.Serializable;

/**
 *
 * @author admar
 */
public class MovimientoReplay implements Serializable {
    private final int fila;
    private final int columna;
    private final long tiempoTranscurrido; // milisegundos desde inicio
    
    public MovimientoReplay(int fila, int columna, long tiempoTranscurrido) {
        this.fila = fila;
        this.columna = columna;
        this.tiempoTranscurrido = tiempoTranscurrido;
    }
    
    // Getters
    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public long getTiempoTranscurrido() { return tiempoTranscurrido; }
}
