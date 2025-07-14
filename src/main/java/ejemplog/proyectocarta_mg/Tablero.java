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
        /**
     * Método para obtener una representación completa del tablero para guardar
     * @return Array bidimensional con información de cada carta
     */
    public String[][] obtenerMapeoCompleto() {
        String[][] mapeo = new String[filas][columnas];
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Carta carta = cartas[i][j];
                // Formato: "ID,TIPO,ESTADO"
                mapeo[i][j] = carta.getId() + "," + 
                             carta.getTipo().name() + "," + 
                             carta.getEstado().name();
            }
        }
        
        return mapeo;
    }
    
   
 public void cargarDesdeMapeo(String[][] mapeo) {
    int filas = mapeo.length;
    int columnas = mapeo[0].length;
    
    this.filas = filas;
    this.columnas = columnas;
    this.cartas = new Carta[filas][columnas];
    
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            String[] datos = mapeo[i][j].split(",");
            String id = datos[0];
            Carta.TipoCarta tipo = Carta.TipoCarta.valueOf(datos[1]);
            Carta.EstadoCarta estado = Carta.EstadoCarta.valueOf(datos[2]);
            
            Carta carta;
            switch (tipo) {
                case Normal:
                    carta = new CartaNormal(id);
                    break;
                case Bonus:
                    carta = new CartaBonus(id, estado, tipo, null);
                    break;
                case Castigo:
                    carta = new CartaCastigo(id, estado, tipo, null);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de carta desconocido: " + tipo);
            }
            
            // Mantener el estado original de la carta
            carta.setEstado(estado);
            
            // Configurar la imagen según el estado
            if (estado == Carta.EstadoCarta.Oculta) {
                carta.setImagenCarta(carta.getImaEspalda());
            } else {
                carta.setImagenCarta(carta.getImaCara());
                
                // SOLO PARA CARTAS NORMALES Y BONUS: 
                // Si están reveladas pero no emparejadas, volver a ocultar
                if (estado == Carta.EstadoCarta.Revelada && 
                    tipo != Carta.TipoCarta.Castigo) {
                    
                    carta.setEstado(Carta.EstadoCarta.Oculta);
                    carta.setImagenCarta(carta.getImaEspalda());
                }
            }
            
            cartas[i][j] = carta;
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