package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConjuntoDigitalTests {

    private ConjuntoDigital conjunto;

    @BeforeEach
    void init() {
        conjunto = new ConjuntoDigital();
    }

    @Test
    void testAgregarYPertenece() {
        conjunto.agregar("Algoritmos");
        assertTrue(conjunto.pertenece("Algoritmos"));
        assertFalse(conjunto.pertenece("Estructuras"));
    }

    @Test
    void testAgregarMultiplesElementos() {
        conjunto.agregar("Algoritmos");
        conjunto.agregar("Estructuras");
        conjunto.agregar("Algoritmos");
        assertTrue(conjunto.pertenece("Algoritmos"));
        assertTrue(conjunto.pertenece("Estructuras"));
        assertEquals(2, conjunto.tamaño());
    }

    @Test
    void testSacarElemento() {
        conjunto.agregar("Algoritmos");
        assertTrue(conjunto.pertenece("Algoritmos"));
        conjunto.sacar("Algoritmos");
        assertFalse(conjunto.pertenece("Algoritmos"));
    }

    @Test
    void testAgregarMateriasYEquivalencias() {
        conjunto.agregar("Algoritmos");
        conjunto.agregar("Estructuras de Datos");
        conjunto.agregar("Programación Avanzada");
        assertTrue(conjunto.pertenece("Algoritmos"));
        assertTrue(conjunto.pertenece("Estructuras de Datos"));
        assertTrue(conjunto.pertenece("Programación Avanzada"));
        assertEquals(3, conjunto.tamaño());
    }

    @Test
    void testAgregarDuplicados() {
        conjunto.agregar("Algoritmos");
        conjunto.agregar("Algoritmos");
        conjunto.agregar("Algoritmos");

        // Este test fallará si el tamaño del conjunto no es 1.
        assertEquals(1, conjunto.tamaño(), "El tamaño del conjunto debería ser 1 después de agregar duplicados.");
    }

    @Test
    void testObtenerElementos() {
        conjunto.agregar("Algoritmos");
        conjunto.agregar("Estructuras");
        conjunto.agregar("Programación");

        List<String> expectedElements = Arrays.asList("Algoritmos", "Estructuras", "Programación");
        ArrayList<String> elementos = conjunto.obtenerElementos();

        assertEquals(expectedElements.size(), elementos.size(), "El tamaño del ArrayList debería ser 3.");
        assertTrue(elementos.containsAll(expectedElements), "El ArrayList debería contener todos los elementos esperados.");
        assertTrue(expectedElements.containsAll(elementos), "El ArrayList no debería contener elementos no esperados.");
    }
}
