package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CursadaTests {

    private Cursada cursada;

    @BeforeEach
    void init() {
        cursada = new Cursada("Algoritmos");
        cursada.getEquivalentes().agregar("Algoritmos");
        cursada.getEquivalentes().agregar("Estructuras de Datos");
        cursada.getEquivalentes().agregar("Programación Avanzada");
    }

    @Test
    void testInicializacion() {
        assertEquals("Algoritmos", cursada.getNombreEstandarizado());
        assertEquals(0, cursada.getEstudiantes().size());
        assertEquals(4, cursada.getDocentes().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(0, cursada.getDocentes().get(i));
        }
        assertEquals(0, cursada.getCupo());
        assertEquals(0, cursada.getInscriptos());
    }

    @Test
    void testAgregarEstudiante() {
        cursada.getEstudiantes().add("123/23");
        assertEquals(1, cursada.getEstudiantes().size());
        assertTrue(cursada.getEstudiantes().contains("123/23"));
    }

    @Test
    void testAgregarDocente() {
        cursada.getDocentes().set(0, 1);
        assertEquals(1, cursada.getDocentes().get(0).intValue());
    }

    @Test
    void testEquivalentes() {
        assertTrue(cursada.getEquivalentes().pertenece("Algoritmos"));
        assertTrue(cursada.getEquivalentes().pertenece("Estructuras de Datos"));
        assertTrue(cursada.getEquivalentes().pertenece("Programación Avanzada"));
        assertFalse(cursada.getEquivalentes().pertenece("Matemática"));
    }

    @Test
    void testToString() {
        cursada.getEstudiantes().add("123/23");
        String resultado = cursada.toString();
        assertTrue(resultado.contains("Algoritmos"));
        assertTrue(resultado.contains("123/23"));
        assertTrue(resultado.contains("Equivalentes: Algoritmos, Estructuras de Datos, Programación Avanzada"));
    }

    
}
