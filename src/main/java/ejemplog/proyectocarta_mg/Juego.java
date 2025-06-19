/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;
import java.util.Stack;
/**
 *
 * @author admar
 */
public class Juego {
  private static final int VidasI = 5;
  private Tablero tablero;
  private int puntajeJugador;
  private Carta primerSeleccion;
  private Carta segundaSeleccion;
  private int vidas;
  private boolean terminarJuego;
  
  private Stack<Movimiento> historialMovimientos = new  Stack<>();

    public Juego(Tablero tablero, int puentajeJugador, Carta primerSeleccion, Carta segundaSeleccion, int vidas, boolean terminarJuego) {
        this.tablero = tablero;
        this.puntajeJugador = puentajeJugador;
        this.primerSeleccion = primerSeleccion;
        this.segundaSeleccion = segundaSeleccion;
        this.vidas = vidas;
        this.terminarJuego = terminarJuego;
    }
    public Juego(){
        iniciarJuego();
    }
    
  public void iniciarJuego (){
        tablero = new Tablero();
        puntajeJugador =0;
        vidas = VidasI;
        terminarJuego = false;
        primerSeleccion = null;
        segundaSeleccion = null;
        
        
    }
  public void seleccionarCarta(int fila, int columna){
  if (terminarJuego) return;
  
  Carta cartaSelect = tablero.getCarta(fila, columna);
  
  if (cartaSelect.getEstado()!= Carta.EstadoCarta.Oculta){
      return;
  }
  
  cartaSelect.setEstado(Carta.EstadoCarta.Revelada);
  
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
  
  
}
