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
import java.util.Stack;

public final class Juego implements Serializable {
    private static final long serialVersionUID = 1L; // Corregido: serialVersionUID
    private static final int VidasI = 30;

    
    private Tablero tablero;
    private int puntajeJugador;
    private Carta primerSeleccion;
    private Carta segundaSeleccion;
    private int vidas;
    private boolean terminarJuego;
    private int aciertos = 0;
    Jugador nomJugador;
    private final Stack<Movimiento> historialMovimientos;

    public Juego(Tablero tablero, int puntajeJugador, Carta primerSeleccion, Carta segundaSeleccion, int vidas, boolean terminarJuego, Jugador nomJugador) {
        this.historialMovimientos = new Stack<>();
        this.tablero = tablero;
        this.puntajeJugador = puntajeJugador;
        this.primerSeleccion = primerSeleccion;
        this.segundaSeleccion = segundaSeleccion;
        this.vidas = vidas;
        this.terminarJuego = terminarJuego;
        this.nomJugador = nomJugador;
    }

    public Juego() {
        this.historialMovimientos = new Stack<>();
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
    }

   public void seleccionarCarta(int fila, int columna) {
    if (terminarJuego) return;
    
    Carta cartaSelect = tablero.getCarta(fila, columna);
    EstadoCarta estadoPrevio = cartaSelect.getEstado(); // Guardar estado previo
    
    try {
        cartaSelect.voltearCarta();
        
        if (primerSeleccion == null) {
            primerSeleccion = cartaSelect;
        } else if (segundaSeleccion == null) {
            segundaSeleccion = cartaSelect;
            guardarMovimiento();
            verificarPareja();
        }
    } catch (Excepciones.CartaNoVoltearExcepcion e) {
        System.out.println("No se puede voltear: " + e.getMessage());
    }
}
    
    private void verificarPareja() {
        if (primerSeleccion.getId().equals(segundaSeleccion.getId())) {
            primerSeleccion.setEstado(Carta.EstadoCarta.Emparejada);
            segundaSeleccion.setEstado(Carta.EstadoCarta.Emparejada);
            puntajeJugador += primerSeleccion.obtenerPuntos();
            aciertos++;
            
            if (aciertos == 3) { // Ejemplo: cada 3 aciertos? Ajustar según reglas
                quitarCastigos();
                aciertos = 0;
            }
        } else {
            vidas--;
            
            if (vidas <= 0) {
                terminarJuego = true;
            } else {
                // Volver a ocultar
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
    
    private void verificarFinJuego() {
        boolean todasEmparejadas = true;
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Carta c = tablero.getCarta(i, j);
                if (c.getEstado() != Carta.EstadoCarta.Emparejada) {
                    todasEmparejadas = false;
                    break;
                }
            }
        }
        if (todasEmparejadas) {
            terminarJuego = true;
        }
    }
    
    public void retroceder() {
    if (historialMovimientos.isEmpty()) {
        throw new Excepciones.sinMovimientosAnt("No hay movimientos anteriores");
    }
    Movimiento ultimo = historialMovimientos.pop();
    
    // Restaurar primera carta (estado + imagen)
    ultimo.getCarta1().setEstado(ultimo.getEstadoAnterior1());
    ultimo.getCarta1().setImagenCarta(
        ultimo.getEstadoAnterior1() == Carta.EstadoCarta.Oculta 
            ? ultimo.getCarta1().getImaEspalda() 
            : ultimo.getCarta1().getImaCara()
    );
    
    // Restaurar segunda carta (estado + imagen)
    if (ultimo.getCarta2() != null) {
        ultimo.getCarta2().setEstado(ultimo.getEstadoAnterior2());
        ultimo.getCarta2().setImagenCarta(
            ultimo.getEstadoAnterior2() == Carta.EstadoCarta.Oculta 
                ? ultimo.getCarta2().getImaEspalda() 
                : ultimo.getCarta2().getImaCara()
        );
    }
    
    puntajeJugador = ultimo.getPuntajeAnterior();
    vidas = ultimo.getVidaAterior();
    primerSeleccion = null;
    segundaSeleccion = null;
}
  
   private void quitarCastigos() {
    for (int i = 0; i < tablero.getFilas(); i++) {
        for (int j = 0; j < tablero.getColumnas(); j++) {
            Carta c = tablero.getCarta(i, j);
            // Solo afecta a cartas de castigo que estén ocultas
            if (c.getTipo() == Carta.TipoCarta.Castigo && c.getEstado() == Carta.EstadoCarta.Oculta) {
                try {
                    c.voltearCarta(); // Revela la carta
                    c.setEstado(Carta.EstadoCarta.Revelada); // Cambia a estado Revelada
                } catch (Excepciones.CartaNoVoltearExcepcion e) {
                    // Manejar excepción si es necesario
                }
            }
        }
    }
}
private void guardarMovimiento() {
    // Solo guardar movimiento si hay dos cartas seleccionadas
    if (primerSeleccion != null && segundaSeleccion != null) {
        // Crear movimiento con los estados ACTUALES (antes de cambiar)
        Movimiento movimiento = new Movimiento(
            primerSeleccion, 
            segundaSeleccion,
            primerSeleccion.getEstado(),  // Estado antes de verificación
            segundaSeleccion.getEstado(), // Estado antes de verificación
            puntajeJugador,
            vidas
        );
        historialMovimientos.push(movimiento);
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
    
    

   
}