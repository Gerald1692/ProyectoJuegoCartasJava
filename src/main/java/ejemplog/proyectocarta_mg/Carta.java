/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import java.nio.channels.CancelledKeyException;

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
public abstract class Carta implements Serializable{
  public enum EstadoCarta{
  Oculta,
  Revelada,
  Emparejada
  }
  
  public enum TipoCarta{
      Normal,
      Bonus,
      Castigo
  }
  private String id;
  private EstadoCarta estado;
  private TipoCarta tipo;
  private transient Image imagenCarta;
  private transient Image imaEspalda=null;
  private transient Image imaCara= null;

    public Carta(String id, EstadoCarta estado, TipoCarta tipo, Image imagenCarta) {
        this.id = id;
        this.estado = estado;
        this.tipo = tipo;
        this.imagenCarta = imagenCarta;
    }

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
  
   
  
    public abstract int obtenerPuntos();

     public abstract Carta clonar();
     public void cargarImagenesCarta();
     public abstract void voltearCarta() throws Excepciones.CartaNoVoltearExcepcion; 

 }
   


