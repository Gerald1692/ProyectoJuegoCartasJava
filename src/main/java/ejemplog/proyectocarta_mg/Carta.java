package ejemplog.proyectocarta_mg;

import javafx.scene.image.Image;
import java.io.Serializable;

public abstract class Carta implements Serializable {
    public enum EstadoCarta {
        Oculta,
        Revelada,
        Emparejada
    }

    public enum TipoCarta {
        Normal,
        Bonus,
        Castigo
    }

    private String id;
    private EstadoCarta estado;
    private TipoCarta tipo;
    private transient Image imagenCarta;
    private transient Image imaEspalda = null;
    private transient Image imaCara = null;

    public Carta(String id, EstadoCarta estado, TipoCarta tipo) {
        this.id = id;
        this.estado = estado;
        this.tipo = tipo;
        
        try {
            // Cargar imágenes desde recursos
            this.imaEspalda = new Image(getClass().getResourceAsStream("/imagenes/imagen_de_carta.jpg"));
            this.imaCara = new Image(getClass().getResourceAsStream("/imagenes/imgNormal/" + id + ".png"));
            this.imagenCarta = imaEspalda;
        } catch (Exception e) {
            System.err.println("Error cargando imágenes para carta: " + id);
            e.printStackTrace();
        }
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public EstadoCarta getEstado() {
        return estado;
    }

    public TipoCarta getTipo() {
        return tipo;
    }

    public Image getImagenCarta() {
        return imagenCarta;
    }

    public Image getImaEspalda() {
        return imaEspalda;
    }

    public Image getImaCara() {
        return imaCara;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEstado(EstadoCarta estado) {
        this.estado = estado;
    }

    public void setTipo(TipoCarta tipo) {
        this.tipo = tipo;
    }

    public void setImagenCarta(Image imagenCarta) {
        this.imagenCarta = imagenCarta;
    }

    public void setImaEspalda(Image imaEspalda) {
        this.imaEspalda = imaEspalda;
    }

    public void setImaCara(Image imaCara) {
        this.imaCara = imaCara;
    }

    // Métodos abstractos
    public abstract int obtenerPuntos();
    public abstract Carta clonar();
    public abstract void voltearCarta() throws Excepciones.CartaNoVoltearExcepcion;
}