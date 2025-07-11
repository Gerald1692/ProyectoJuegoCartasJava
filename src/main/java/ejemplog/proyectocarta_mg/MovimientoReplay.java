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
    public enum ActionType { SHOW, HIDE ,  REMOVE_PENALTY }
    
    private final int fila;
    private final int columna;
    private final long tiempoTranscurrido;
    private final ActionType actionType; // Nuevo campo

    public MovimientoReplay(int fila, int columna, long tiempoTranscurrido, ActionType actionType) {
        this.fila = fila;
        this.columna = columna;
        this.tiempoTranscurrido = tiempoTranscurrido;
        this.actionType = actionType;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public long getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }
    
   
    public ActionType getActionType() {
        return actionType;
    }
}
