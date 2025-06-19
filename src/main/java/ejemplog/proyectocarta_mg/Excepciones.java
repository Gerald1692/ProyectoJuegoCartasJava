/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

/**
 *
 * @author admar
 */
public final class Excepciones {
    
    public static class CartaNoVoltearExcepcion extends Exception{
        public CartaNoVoltearExcepcion(String mensaje){
            super(mensaje);
        }
    }
    
    public static class sinMovimientosAnt extends RuntimeException{
        public sinMovimientosAnt (String mensaje){
            super(mensaje);
        }
    }
    
    
}
