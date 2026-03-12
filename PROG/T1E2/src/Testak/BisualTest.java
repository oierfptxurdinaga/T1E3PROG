package Testak;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import Main.ErronkaBisuala;
import Metodoak.Metodoak;
import E2.Taldea;

class BisualTest {

    private ErronkaBisuala gui;

    @BeforeEach
    void setUp() {
        // Inicializamos la lista maestra para que el constructor de la ventana
        // no encuentre valores nulos al rellenar los ComboBox.
        Metodoak.taldeakMasterList = new ArrayList<>();
        
        // Añadimos un equipo de prueba
        Metodoak.taldeakMasterList.add(new Taldea("Bilbao Basket", "1990", "Presidente A", 1000, 0, 0, 0, 0, 0, new ArrayList<>()));
        
        // Creamos la instancia de la interfaz
        gui = new ErronkaBisuala();
    }

    @Test
    void testPropiedadesVentana() {
        // Verifica que el título y tamaño coincidan con tu código
        assertNotNull(gui, "La ventana debe instanciarse correctamente.");
        assertEquals("Bizkaiko Saskibaloi Federazioa", gui.getTitle());
        assertEquals(1000, gui.getWidth());
        assertEquals(700, gui.getHeight());
    }

    @Test
    void testConfiguracionCierre() {
        // Comprueba que usas DO_NOTHING_ON_CLOSE para gestionar el cierre manualmente
        assertEquals(ErronkaBisuala.DO_NOTHING_ON_CLOSE, gui.getDefaultCloseOperation());
    }

    @Test
    void testVisibilidad() {
        // Verifica que la ventana sea visible tras la ejecución
        assertTrue(gui.isVisible(), "La ventana principal debería estar visible para el usuario.");
    }

    @Test
    void testCargaDeDatos() {
        // Verifica que la lista maestra no esté vacía tras inicializar el componente
        assertNotNull(Metodoak.taldeakMasterList);
        assertFalse(Metodoak.taldeakMasterList.isEmpty(), "La lista de equipos debería tener datos cargados.");
    }
}