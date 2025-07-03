/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class Tablero implements Serializable {
    private Carta[][] cartas;
    private int filas = 3; // 3 filas
    private int columnas = 4; // 4 columnas
    
    public Tablero() {
        inicializarCartas();
    }
    
    private void inicializarCartas() {
        cartas = new Carta[filas][columnas];
        List<Carta> listaCartas = new ArrayList<>();
        
        // 4 pares de cartas normales (8 cartas)
        for (int i = 0; i < 4; i++) {
            String id = "N" + i;
            listaCartas.add(new CartaNormal(id));
            listaCartas.add(new CartaNormal(id)); // Par
        }
        
        // 1 par de cartas bonus (2 cartas)
        String idBonus = "B0";
        listaCartas.add(new CartaBonus(idBonus, Carta.EstadoCarta.Oculta, Carta.TipoCarta.Bonus, null));
        listaCartas.add(new CartaBonus(idBonus, Carta.EstadoCarta.Oculta, Carta.TipoCarta.Bonus, null));
        
        // 1 par de cartas castigo (2 cartas)
        String idCastigo = "C0";
        listaCartas.add(new CartaCastigo(idCastigo, Carta.EstadoCarta.Oculta, Carta.TipoCarta.Castigo, null));
        listaCartas.add(new CartaCastigo(idCastigo, Carta.EstadoCarta.Oculta, Carta.TipoCarta.Castigo, null));
        
        Collections.shuffle(listaCartas);
        
        int index = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                cartas[i][j] = listaCartas.get(index);
                index++;
            }
        }
    }
    
    public Carta getCarta(int fila, int columna) {
        return cartas[fila][columna];
    }

    public Carta[][] getCartas() {
        return cartas;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setCartas(Carta[][] cartas) {
        this.cartas = cartas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }
}