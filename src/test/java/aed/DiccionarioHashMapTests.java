package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiccionarioHashMapTests {
    private DiccionarioHashMap<String, Integer> diccionario;

    @BeforeEach
    public void setUp() {
        diccionario = new DiccionarioHashMap<>();
    }

    @Test
    public void testDiccionarioVacio() {
        assertTrue(diccionario.diccionarioVacio(), "El diccionario debería estar vacío inicialmente.");
    }

    @Test
    public void testDefinirYObtener() {
        diccionario.definir("uno", 1);
        assertEquals(1, diccionario.obtener("uno"), "Debería obtener el valor 1 para la clave 'uno'.");
    }

    @Test
    public void testEsta() {
        diccionario.definir("dos", 2);
        assertTrue(diccionario.esta("dos"), "La clave 'dos' debería estar en el diccionario.");
        assertFalse(diccionario.esta("tres"), "La clave 'tres' no debería estar en el diccionario.");
    }

    @Test
    public void testBorrar() {
        diccionario.definir("cuatro", 4);
        diccionario.borrar("cuatro");
        assertFalse(diccionario.esta("cuatro"), "La clave 'cuatro' debería haber sido borrada.");
    }

    @Test
    public void testTamaño() {
        assertEquals(0, diccionario.tamaño(), "El tamaño inicial del diccionario debería ser 0.");
        diccionario.definir("cinco", 5);
        assertEquals(1, diccionario.tamaño(), "El tamaño del diccionario debería ser 1 después de agregar una clave.");
    }

    @Test
    public void testObtenerClaveNoExistente() {
        assertNull(diccionario.obtener("noExiste"), "Debería devolver null para una clave no existente.");
    }

    @Test
    public void testToString() {
        diccionario.definir("uno", 1);
        diccionario.definir("dos", 2);
        String expected = "{uno=1, dos=2}";
        assertEquals(expected, diccionario.toString(), "La representación en cadena del diccionario no es la esperada.");
    }
}
