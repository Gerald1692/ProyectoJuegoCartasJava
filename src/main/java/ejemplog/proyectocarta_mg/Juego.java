/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;
import java.io.File;
import java.util.Stack;
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
public class Juego implements Serializable{
  private static final long serialversionUID=1L;
  private static final int VidasI = 5;
  private Tablero tablero;
  private int puntajeJugador;
  private Carta primerSeleccion;
  private Carta segundaSeleccion;
  private int vidas;
  private boolean terminarJuego;
  private int aciertos= 0;
  private Jugador nomJugador;
  
  private Stack<Movimiento> historialMovimientos = new  Stack<>();

    public Juego(Tablero tablero, int puentajeJugador, Carta primerSeleccion, Carta segundaSeleccion, int vidas, boolean terminarJuego,Jugador nomJugador) {
        this.tablero = tablero;
        this.puntajeJugador = puentajeJugador;
        this.primerSeleccion = primerSeleccion;
        this.segundaSeleccion = segundaSeleccion;
        this.vidas = vidas;
        this.terminarJuego = terminarJuego;
        this.nomJugador = nomJugador;
    }
    public Juego(){
        iniciarJuego();
    }
    
  public void iniciarJuego (){
        nomJugador = nomJugador;
        tablero = new Tablero();
        puntajeJugador =0;
        vidas = VidasI;
        terminarJuego = false;
        primerSeleccion = null;
        segundaSeleccion = null;
        aciertos =0;        
        
    }
  public void seleccionarCarta(int fila, int columna){
  if (terminarJuego) return;
  
  Carta cartaSelect = tablero.getCarta(fila, columna);
  
      try {
          cartaSelect.voltearCarta();
      } catch (Excepciones.CartaNoVoltearExcepcion e) {
          System.err.println("No se puede voltear la carta" + e.getMessage());
          return;
      }
  
  if (primerSeleccion == null){
      primerSeleccion = cartaSelect;
  }
  else if(segundaSeleccion == null){
      segundaSeleccion = cartaSelect;
      verificarPareja();
  }
  
  }
  
  private void verificarPareja(){
    if (primerSeleccion.getId().equals(segundaSeleccion.getId())){

        puntajeJugador += primerSeleccion.obtenerPuntos();
        primerSeleccion.setEstado(Carta.EstadoCarta.Emparejada);
        segundaSeleccion.setEstado(Carta.EstadoCarta.Emparejada);
        
        aciertos++;
        
        if(aciertos == 3){
            quitarCastidos();
            aciertos =0;
            
        
        }
    }
    
    else{
        vidas--;
        
        if(vidas<= 0){
            terminarJuego=true;
        }
        
        else{
            primerSeleccion.setEstado(Carta.EstadoCarta.Oculta);
            primerSeleccion.setImagenCarta(primerSeleccion.getImaEspalda());
            segundaSeleccion.setEstado(Carta.EstadoCarta.Oculta);
            segundaSeleccion.setImagenCarta(segundaSeleccion.getImaEspalda());
        }
            
            
    }
    primerSeleccion =null;
    segundaSeleccion = null;
    verificarFinJuego();
    
  
  }
  
  private void verificarFinJuego(){
    boolean todasEmparejadas = true;
    for(int i=0; i<tablero.getFilas();i++){
        for(int j=0;j< tablero.getColumnas();j++){
            Carta c = tablero.getCarta(i, j);
            if(c.getEstado()!= Carta.EstadoCarta.Emparejada){
                todasEmparejadas=false;
                break;
            }
        }
    }
    if(todasEmparejadas){
        terminarJuego= true;
    }
  }
  public void retroceder(){
      if (historialMovimientos.isEmpty()){
          throw new Excepciones.sinMovimientosAnt("error");
      }
       Movimiento ultimo = historialMovimientos.pop();
       ultimo.getCarta1().setEstado(ultimo.getEstadoAnterior1());
       if(ultimo.getCarta2() !=null){
           ultimo.getCarta2().setEstado(ultimo.getEstadoAnterior2());
        }
          
        puntajeJugador = ultimo.getPuntajeAnterior();
        vidas = ultimo.getVidaAterior();
        primerSeleccion = null;
        segundaSeleccion = null;   
          
      
  }
  
  private void quitarCastidos(){
      for(int i=0; i<tablero.getFilas();i++){
          for(int j=0; j< tablero.getColumnas();j++){
          Carta c =tablero.getCarta(i, j);
          if(c.getTipo()== Carta.TipoCarta.Castigo && c.getEstado()== Carta.EstadoCarta.Oculta){
              c.setEstado(Carta.EstadoCarta.Revelada);
              c.setImagenCarta(c.getImaCara());
          }
          }
      }
  }
  
  public void guardarPartida (String arghivo){
    String rutaArchivo = "Partidas/PartidasJugadas.dat";  
      try {
       File carpeta = new File("Partidas");
       
       if(!carpeta.exists()){
           carpeta.mkdir();
       }
       try(ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaArchivo)) ){
           salida.writeObject(this);
               System.out.println("se guardo");
           }
      } 
     
      catch (IOException e) {
          System.err.println("error");
      }
    
  
  }
  public static Juego cargarPartida(){
    String rutaArchivo = "Partidas/PartidasJugadas.dat";  
    try(ObjectInputStream entrada =new ObjectInputStream(new FileInputStream(rutaArchivo))){
        Juego juegoCargado = (Juego) entrada.readObject();
        System.out.println("se cargo");
        return juegoCargado;
    }
    
    catch(IOException | ClassNotFoundException e){
          System.err.println("error");
          e.printStackTrace();
          return null;
    }
  }
    public Tablero getTablero() {
        return tablero;
    }

    public int getPuentajeJugador() {
        return puntajeJugador;
    }

    public Carta getPrimerSeleccion() {
        return primerSeleccion;
    }

    public Carta getSegundaSeleccion() {
        return segundaSeleccion;
    }

    public int getVidas() {
        return vidas;
    }

    public boolean isTerminarJuego() {
        return terminarJuego;
    }

    public int getAciertos() {
        return aciertos;
    }
    
    

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public void setPuentajeJugador(int puentajeJugador) {
        this.puntajeJugador = puentajeJugador;
    }

    public void setPrimerSeleccion(Carta primerSeleccion) {
        this.primerSeleccion = primerSeleccion;
    }

    public void setSegundaSeleccion(Carta segundaSeleccion) {
        this.segundaSeleccion = segundaSeleccion;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void setTerminarJuego(boolean terminarJuego) {
        this.terminarJuego = terminarJuego;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }
  
  
}
