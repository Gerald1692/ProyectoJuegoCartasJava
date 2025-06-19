/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import ejemplog.proyectocarta_mg.Carta;
import javafx.scene.image.Image;

/**
 *
 * @author admar
 */
public class CartaNormal extends Carta{
    private int puntos=3;

    public CartaNormal(String id, EstadoCarta estado, TipoCarta tipo, Image imagenCarta) {
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
        
        if (getEstado() == EstadoCarta.Oculta)
            
            setEstado(EstadoCarta.Revelada);
            setImagenCarta(getImaCara());
                    
    
        
        
    }
}
      
