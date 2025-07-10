/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;
import ejemplog.proyectocarta_mg.Carta.EstadoCarta;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.IOException;

/**
 *
 * @author admar
 */
public class Movimiento {
    private final Carta carta1;
    private final Carta carta2;
    private final EstadoCarta estadoAnterior1;
    private final EstadoCarta estadoAnterior2;
    private final int puntajeAnterior;
    private final int vidaAterior;

    public Movimiento(Carta c1, Carta c2, EstadoCarta est1, EstadoCarta est2, int score, int vidas) {
        this.carta1 = c1;
        this.carta2 = c2;
        this.estadoAnterior1 = est1;
        this.estadoAnterior2 = est2;
        this.puntajeAnterior = score;
        this.vidaAterior = vidas;
    }

     public Carta getCarta1() {
        return carta1;
    }

    public Carta getCarta2() {
        return carta2;
    }

    public Carta.EstadoCarta getEstadoAnterior1() {
        return estadoAnterior1;
    }

    public Carta.EstadoCarta getEstadoAnterior2() {
        return estadoAnterior2;
    }

    public int getPuntajeAnterior() {
        return puntajeAnterior;
    }

    public int getVidaAterior() {
        return vidaAterior;
    }

 
    
    
}
