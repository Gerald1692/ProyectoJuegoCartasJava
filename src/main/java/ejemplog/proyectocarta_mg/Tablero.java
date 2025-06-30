/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;
import javafx.scene.image.Image;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

/**
 *
 * @author admar
 */
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class Tablero implements Serializable{
    
    private Carta [][] cartas;
    private int filas=4;
    private int columnas=3;
    
    
    private List<Image> imagenesNormales;
    private List<Image> imagenesBonus;
    private List<Image> imagenesCastigo;
    
    
    
    public Tablero(){
        imagenesNormales = CargadorImagenes.cargarDesdeCarpeta("imagenes/ImgNormal");
        imagenesBonus = CargadorImagenes.cargarDesdeCarpeta("imagenes/ImgBonus");
        imagenesCastigo = CargadorImagenes.cargarDesdeCarpeta("imagenes/Castigo");
        inicializarCartas();
    }
    
    
    
    private void inicializarCartas(){
        cartas = new Carta[filas][columnas];
        int totalCartas = filas * columnas;
        List<Carta> listaCartas = new ArrayList<>();
        
       
        
        int indiceNormal= 0;
        int indiceBonus= 0;
        int indiceCastigo= 0;
        
        
        for (int i=0; i<4;i++){
            
            String id = "N"+i;
            
            Image imagenCarta=imagenesNormales.get(indiceNormal++);;
            
            Carta carta1 = new CartaNormal(id, Carta.EstadoCarta.Oculta, Carta.TipoCarta.Normal,imagenCarta);
            Carta carta2 = new CartaNormal( id, Carta.EstadoCarta.Oculta, Carta.TipoCarta.Normal, imagenCarta);
            
            listaCartas.add(carta1);
            listaCartas.add(carta2.clonar());
        }
        
        for (int i =0;i<1;i++){
            
            String id = "B"+i;
            
            Image imagenCarta= imagenesBonus.get(indiceBonus++);
            
            Carta carta1 = new CartaBonus(id, Carta.EstadoCarta.Oculta, Carta.TipoCarta.Bonus, imagenCarta);
            Carta carta2 = new CartaBonus(id, Carta.EstadoCarta.Oculta, Carta.TipoCarta.Bonus, imagenCarta);
            
            listaCartas.add(carta1);
            listaCartas.add(carta2.clonar());
        
        }
        
        for (int i = 0;i <1; i++ ){
            String id = "C"+i;
            
            Image imagenCarta=imagenesCastigo.get(indiceCastigo);
            
            Carta carta1 = new CartaCastigo(id, Carta.EstadoCarta.Revelada, Carta.TipoCarta.Castigo, imagenCarta);
            Carta carta2 = new CartaCastigo(id, Carta.EstadoCarta.Revelada, Carta.TipoCarta.Castigo, imagenCarta);
            listaCartas.add(carta1);
            listaCartas.add(carta2.clonar());
        }
        
        Collections.shuffle(listaCartas);
        int index =0;
        for(int i =0;i < filas; i++){
            for (int j =0; j < columnas; j++){
            cartas[i][j] = listaCartas.get(index);
            index++;
            }
        }
        
        
    }
    public Carta getCarta(int fila, int columna){
        return cartas [fila][columna];
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
