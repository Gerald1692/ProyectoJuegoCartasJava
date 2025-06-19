/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

/**
 *
 * @author admar
 */
public class Movimiento {
    private Carta carta1;
    private Carta carta2;
    private Carta.EstadoCarta estadoAnterior1;
    private Carta.EstadoCarta estadoAnterior2;
    private int puntajeAnterior;
    private int vidaAterior;

    public Movimiento(Carta carta1, Carta carta2, Carta.EstadoCarta estadoAnterior1, Carta.EstadoCarta estadoAnterior2, int puntajeAnterior, int vidaAterior) {
        this.carta1 = carta1;
        this.carta2 = carta2;
        this.estadoAnterior1 = estadoAnterior1;
        this.estadoAnterior2 = estadoAnterior2;
        this.puntajeAnterior = puntajeAnterior;
        this.vidaAterior = vidaAterior;
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

    public void setCarta1(Carta carta1) {
        this.carta1 = carta1;
    }

    public void setCarta2(Carta carta2) {
        this.carta2 = carta2;
    }

    public void setEstadoAnterior1(Carta.EstadoCarta estadoAnterior1) {
        this.estadoAnterior1 = estadoAnterior1;
    }

    public void setEstadoAnterior2(Carta.EstadoCarta estadoAnterior2) {
        this.estadoAnterior2 = estadoAnterior2;
    }

    public void setPuntajeAnterior(int puntajeAnterior) {
        this.puntajeAnterior = puntajeAnterior;
    }

    public void setVidaAterior(int vidaAterior) {
        this.vidaAterior = vidaAterior;
    }
    
    
    
}
