/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import javafx.scene.image.Image;
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
public class CartaBonus extends Carta implements Serializable{

    private int puntos=5;
    
   

    public CartaBonus(String id, EstadoCarta estado, TipoCarta tipo, Image imagenCarta) {
        super(id, estado, tipo, imagenCarta);
    }
    
    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    

   @Override
    public int obtenerPuntos() {
        
        return puntos;
        
    }
   
    @Override
       public Carta clonar(){
           return new CartaBonus(
           this.getId(),
            EstadoCarta.Oculta,
            this.getTipo(),
            this.getImagenCarta()
                   
           );
           
           

       }
       
    @Override
    public void voltearCarta() throws Excepciones.CartaNoVoltearExcepcion{
        if(getEstado()== EstadoCarta.Emparejada){
            throw new Excepciones.CartaNoVoltearExcepcion("ya le encontraste pareja");
        }
        
        if(getEstado() == EstadoCarta.Revelada){
            throw new Excepciones.CartaNoVoltearExcepcion("La carta ya esta revelada");
        }
        
        if (getEstado() == EstadoCarta.Oculta){
            
            setEstado(EstadoCarta.Revelada);
            setImagenCarta(getImaCara());
        }         
    
        
        
    }
    
    
    
    }

//Prueba
