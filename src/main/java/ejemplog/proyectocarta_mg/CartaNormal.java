package ejemplog.proyectocarta_mg;

import javafx.scene.image.Image;

public class CartaNormal extends Carta {
    private int puntos = 3;

    public CartaNormal(String id) {
        super(id, EstadoCarta.Oculta, TipoCarta.Normal);
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
    public Carta clonar() {
        return new CartaNormal(this.getId());
    }
    
    @Override
    public void voltearCarta() throws Excepciones.CartaNoVoltearExcepcion {
        // Implementación según sea necesario
    }
}