package ejemplog.proyectocarta_mg;

import javafx.scene.image.Image;
import java.io.Serializable;

public class CartaCastigo extends Carta implements Serializable {
    private int puntos = -2;

    public CartaCastigo(String id, EstadoCarta estado, TipoCarta tipo, Image imagenCarta) {
        super(id, estado, tipo);
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    @Override
    public int obtenerPuntos() {
        System.out.println("*"+puntos);
        return puntos;
    }
    
    @Override
    public Carta clonar() {
        return new CartaCastigo(
            this.getId(),
            EstadoCarta.Oculta,
            this.getTipo(),
            this.getImagenCarta()
        );
    }
     
    @Override
    public void voltearCarta() throws Excepciones.CartaNoVoltearExcepcion {
       
            setEstado(EstadoCarta.Revelada);
            setImagenCarta(getImaCara());
        
    }
}