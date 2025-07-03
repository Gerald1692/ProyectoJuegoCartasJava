/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ejemplog.proyectocarta_mg;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author admar
 */
public class JuegoTest {
    
    public JuegoTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    


    /**
     * Test of iniciarJuego method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testIniciarJuego() {
        System.out.println("iniciarJuego");
        Juego instance = new Juego();
        instance.iniciarJuego();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of seleccionarCarta method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testSeleccionarCarta() {
        System.out.println("seleccionarCarta");
        int fila = 0;
        int columna = 0;
        Juego instance = new Juego();
        instance.seleccionarCarta(fila, columna);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retroceder method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testRetroceder() {
        System.out.println("retroceder");
        Juego instance = new Juego();
        instance.retroceder();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of guardarPartida method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testGuardarPartida() {
        System.out.println("guardarPartida");
        String arghivo = "";
        Juego instance = new Juego();
        instance.guardarPartida(arghivo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cargarPartida method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testCargarPartida() {
        System.out.println("cargarPartida");
        Juego expResult = null;
        Juego result = Juego.cargarPartida();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTablero method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testGetTablero() {
        System.out.println("getTablero");
        Juego instance = new Juego();
        Tablero expResult = null;
        Tablero result = instance.getTablero();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPuentajeJugador method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testGetPuentajeJugador() {
        System.out.println("getPuentajeJugador");
        Juego instance = new Juego();
        int expResult = 0;
        int result = instance.getPuentajeJugador();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrimerSeleccion method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testGetPrimerSeleccion() {
        System.out.println("getPrimerSeleccion");
        Juego instance = new Juego();
        Carta expResult = null;
        Carta result = instance.getPrimerSeleccion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSegundaSeleccion method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testGetSegundaSeleccion() {
        System.out.println("getSegundaSeleccion");
        Juego instance = new Juego();
        Carta expResult = null;
        Carta result = instance.getSegundaSeleccion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVidas method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testGetVidas() {
        System.out.println("getVidas");
        Juego instance = new Juego();
        int expResult = 0;
        int result = instance.getVidas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTerminarJuego method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testIsTerminarJuego() {
        System.out.println("isTerminarJuego");
        Juego instance = new Juego();
        boolean expResult = false;
        boolean result = instance.isTerminarJuego();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAciertos method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testGetAciertos() {
        System.out.println("getAciertos");
        Juego instance = new Juego();
        int expResult = 0;
        int result = instance.getAciertos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTablero method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testSetTablero() {
        System.out.println("setTablero");
        Tablero tablero = null;
        Juego instance = new Juego();
        instance.setTablero(tablero);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPuentajeJugador method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testSetPuentajeJugador() {
        System.out.println("setPuentajeJugador");
        int puentajeJugador = 0;
        Juego instance = new Juego();
        instance.setPuentajeJugador(puentajeJugador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrimerSeleccion method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testSetPrimerSeleccion() {
        System.out.println("setPrimerSeleccion");
        Carta primerSeleccion = null;
        Juego instance = new Juego();
        instance.setPrimerSeleccion(primerSeleccion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSegundaSeleccion method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testSetSegundaSeleccion() {
        System.out.println("setSegundaSeleccion");
        Carta segundaSeleccion = null;
        Juego instance = new Juego();
        instance.setSegundaSeleccion(segundaSeleccion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVidas method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testSetVidas() {
        System.out.println("setVidas");
        int vidas = 0;
        Juego instance = new Juego();
        instance.setVidas(vidas);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTerminarJuego method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testSetTerminarJuego() {
        System.out.println("setTerminarJuego");
        boolean terminarJuego = false;
        Juego instance = new Juego();
        instance.setTerminarJuego(terminarJuego);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAciertos method, of class Juego.
     */
    @org.junit.jupiter.api.Test
    public void testSetAciertos() {
        System.out.println("setAciertos");
        int aciertos = 0;
        Juego instance = new Juego();
        instance.setAciertos(aciertos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
