/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import ejemplog.proyectocarta_mg.Carta.EstadoCarta;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import java.text.BreakIterator;
import java.util.Stack;


public final class Juego implements Serializable {
    private static final long serialVersionUID = 1L; 
    private static final int VidasI = 30;
    private final List <MovimientoReplay> historialReplay = new ArrayList<>();
    private long tiempoInicio;
    private Tablero tablero;
    private int puntajeJugador;
    private Carta primerSeleccion;
    private Carta segundaSeleccion;
    private int vidas;
    private boolean terminarJuego;
    private int aciertos = 0;
    Jugador nomJugador;
    

    public Juego(Tablero tablero, int puntajeJugador, Carta primerSeleccion, Carta segundaSeleccion, int vidas, boolean terminarJuego, Jugador nomJugador) {
       
        this.tablero = tablero;
        this.puntajeJugador = puntajeJugador;
        this.primerSeleccion = primerSeleccion;
        this.segundaSeleccion = segundaSeleccion;
        this.vidas = vidas;
        this.terminarJuego = terminarJuego;
        this.nomJugador = nomJugador;
    }

    public Juego() {
        
        iniciarJuego();
    }
    
    public void iniciarJuego() {
        tablero = new Tablero();
        puntajeJugador = 0;
        vidas = VidasI;
        terminarJuego = false;
        primerSeleccion = null;
        segundaSeleccion = null;
        aciertos = 0; 
        tiempoInicio = System.currentTimeMillis();
        historialReplay.clear();
    }

  public void seleccionarCarta(int fila, int columna) {
    if (terminarJuego) return;
    
    Carta cartaSelect = tablero.getCarta(fila, columna);
    EstadoCarta estadoPrevio = cartaSelect.getEstado();
    
    try {
        cartaSelect.voltearCarta();
        
        if (primerSeleccion == null) {
            primerSeleccion = cartaSelect;
        } else if (segundaSeleccion == null) {
            segundaSeleccion = cartaSelect;
            verificarPareja();
        }
        
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
        historialReplay.add(new MovimientoReplay(
            fila, 
            columna, 
            tiempoTranscurrido, 
            MovimientoReplay.ActionType.SHOW
        ));
    } catch (Excepciones.CartaNoVoltearExcepcion e) {
        System.out.println("No se puede voltear: " + e.getMessage());
    }
}
   public List <MovimientoReplay> geMovimientoReplays(){
       return historialReplay;
   }
    
    private void verificarPareja() {
    if (primerSeleccion.getId().equals(segundaSeleccion.getId())) {
        primerSeleccion.setEstado(Carta.EstadoCarta.Emparejada);
        segundaSeleccion.setEstado(Carta.EstadoCarta.Emparejada);
        puntajeJugador += primerSeleccion.obtenerPuntos();
        aciertos++;
        
        if (aciertos == 3) { 
            quitarCastigos();
            aciertos = 0;
        }
    } else {
        vidas--;
        
        if (vidas <= 0) {
            terminarJuego = true;
        } else {
            // Obtener coordenadas antes de voltear
            int fila1 = -1, columna1 = -1, fila2 = -1, columna2 = -1;
            for (int i = 0; i < tablero.getFilas(); i++) {
                for (int j = 0; j < tablero.getColumnas(); j++) {
                    if (tablero.getCarta(i, j) == primerSeleccion) {
                        fila1 = i;
                        columna1 = j;
                    }
                    if (tablero.getCarta(i, j) == segundaSeleccion) {
                        fila2 = i;
                        columna2 = j;
                    }
                }
            }
            
            // Registrar acciones de ocultar (con 1 segundo de retraso)
            long tiempoOcultar = System.currentTimeMillis() - tiempoInicio + 1000;
            historialReplay.add(new MovimientoReplay(
                fila1, columna1, tiempoOcultar, MovimientoReplay.ActionType.HIDE));
            historialReplay.add(new MovimientoReplay(
                fila2, columna2, tiempoOcultar, MovimientoReplay.ActionType.HIDE));
            
            // Volver a ocultar las cartas
            primerSeleccion.setEstado(Carta.EstadoCarta.Oculta);
            primerSeleccion.setImagenCarta(primerSeleccion.getImaEspalda());
            segundaSeleccion.setEstado(Carta.EstadoCarta.Oculta);
            segundaSeleccion.setImagenCarta(segundaSeleccion.getImaEspalda());
        }
    }
    
    primerSeleccion = null;
    segundaSeleccion = null;
    verificarFinJuego();
}
    
   public void verificarFinJuego() {
    boolean todasEmparejadas = true;
    for (int i = 0; i < tablero.getFilas(); i++) {
        for (int j = 0; j < tablero.getColumnas(); j++) {
            Carta c = tablero.getCarta(i, j);
            if (c.getEstado() != Carta.EstadoCarta.Emparejada) {
                todasEmparejadas = false;
                break;
            }
        }
        if (!todasEmparejadas) break;
    }
    

   

    terminarJuego = todasEmparejadas || vidas <= 0;
}
    


  
   private void quitarCastigos() {
    for (int i = 0; i < tablero.getFilas(); i++) {
        for (int j = 0; j < tablero.getColumnas(); j++) {
            Carta c = tablero.getCarta(i, j);
            if (c.getTipo() == Carta.TipoCarta.Castigo && c.getEstado() == Carta.EstadoCarta.Oculta) {
                try {
                    c.voltearCarta();
                    c.setEstado(Carta.EstadoCarta.Revelada);
                    
                    // Registrar el evento de quitar castigo
                    historialReplay.add(new MovimientoReplay(
                        i, j,
                        System.currentTimeMillis() - tiempoInicio,
                        MovimientoReplay.ActionType.REMOVE_PENALTY
                    ));
                } catch (Excepciones.CartaNoVoltearExcepcion e) {
                    System.err.println("Error al revelar castigo: " + e.getMessage());
                }
            }
        }
    }
}


    public void guardarPartidaTxt(String nombreArchivo, String nombreJugador) throws FileNotFoundException {
    File carpeta = new File("src/main/resources/CargaPartidas");
    if (!carpeta.exists()) {
        carpeta.mkdirs();
    }
    
    File archivo = new File(carpeta, nombreArchivo + ".txt");
    try (PrintWriter writer = new PrintWriter(archivo)) {
        // Guardar datos básicos del juego
        writer.println("Jugador: " + nombreJugador);
        writer.println("Puntaje: " + puntajeJugador);
        writer.println("Vidas: " + vidas);
        writer.println("Tiempo: " + App.gameDuration);
        writer.println("Terminado: " + terminarJuego);
        writer.println("Aciertos: " + aciertos);
        writer.println("--- TABLERO ---");
        
        // Obtener mapeo completo del tablero
        String[][] mapeoTablero = tablero.obtenerMapeoCompleto();
        
        // Guardar dimensiones del tablero
        writer.println("Filas: " + mapeoTablero.length);
        writer.println("Columnas: " + mapeoTablero[0].length);
        
        // Guardar cada posición del tablero con formato específico
        for (int i = 0; i < mapeoTablero.length; i++) {
            for (int j = 0; j < mapeoTablero[i].length; j++) {
                // Formato: FILA,COLUMNA,DATOS_CARTA
                writer.println(i + "," + j + "," + mapeoTablero[i][j]);
            }
        }
        
        System.out.println("Partida guardada exitosamente: " + nombreArchivo);
    } catch (IOException e) {
        System.err.println("Error guardando partida: " + e.getMessage());
        throw new FileNotFoundException("No se pudo crear el archivo de guardado");
    }
}
  
    public static Juego cargarPartidaTxt(String nombreArchivo) {
    File carpeta = new File("src/main/resources/CargaPartidas");
    File archivo = new File(carpeta, nombreArchivo + ".txt");
    
    if (!archivo.exists()) {
        System.err.println("El archivo de partida no existe: " + nombreArchivo);
        return null;
    }
    
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        Juego juego = new Juego();
        String line;
        boolean leyendoTablero = false;
        String[][] mapeoTablero = null;
        int filas = 0, columnas = 0;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            
            if (line.equals("--- TABLERO ---")) {
                leyendoTablero = true;
                continue;
            }
            
            if (!leyendoTablero) {
                // Leer datos básicos del juego
                if (line.startsWith("Jugador: ")) {
                    juego.nomJugador = new Jugador(line.substring(9));
                } else if (line.startsWith("Puntaje: ")) {
                    juego.puntajeJugador = Integer.parseInt(line.substring(9));
                } else if (line.startsWith("Vidas: ")) {
                    juego.vidas = Integer.parseInt(line.substring(7));
                } else if (line.startsWith("Tiempo: ")) {
                    App.gameDuration = Integer.parseInt(line.substring(8));
                } else if (line.startsWith("Terminado: ")) {
                    juego.terminarJuego = Boolean.parseBoolean(line.substring(11).trim());
                } else if (line.startsWith("Aciertos: ")) {
                    juego.aciertos = Integer.parseInt(line.substring(10).trim());
                }
            } else {
                // Leer datos del tablero
                if (line.startsWith("Filas: ")) {
                    filas = Integer.parseInt(line.substring(7));
                } else if (line.startsWith("Columnas: ")) {
                    columnas = Integer.parseInt(line.substring(10));
                    mapeoTablero = new String[filas][columnas];
                } else {
                    // Leer posición específica del tablero
                    String[] partes = line.split(",", 3); // Límite 3 para evitar problemas con datos de carta
                    int fila = Integer.parseInt(partes[0]);
                    int columna = Integer.parseInt(partes[1]);
                    String datosCarta = partes[2];
                    
                    mapeoTablero[fila][columna] = datosCarta;
                }
            }
        }
        
        // Reconstruir el tablero desde el mapeo
        if (mapeoTablero != null) {
            juego.tablero = new Tablero();
            juego.tablero.cargarDesdeMapeo(mapeoTablero);
            juego.verificarFinJuego();
        } else {
            System.err.println("Error: No se pudo reconstruir el tablero");
            return null;
        }
        
        System.out.println("Partida cargada exitosamente: " + nombreArchivo);
        return juego;
        
    } catch (IOException e) {
        System.err.println("Error cargando partida: " + e.getMessage());
        return null;
    } catch (NumberFormatException e) {
        System.err.println("Error en formato de datos: " + e.getMessage());
        return null;
    }
}
    
    
  
    
    

    // Getters y setters
    public Tablero getTablero() {
        return tablero;
    }

    public int getPuntajeJugador() {
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

    public void setPuntajeJugador(int puntajeJugador) {
        this.puntajeJugador = puntajeJugador;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static int getVidasI() {
        return VidasI;
    }

    public List<MovimientoReplay> getHistorialReplay() {
        return historialReplay;
    }

    public long getTiempoInicio() {
        return tiempoInicio;
    }

    public Jugador getNomJugador() {
        return nomJugador;
    }
    
    

   
}