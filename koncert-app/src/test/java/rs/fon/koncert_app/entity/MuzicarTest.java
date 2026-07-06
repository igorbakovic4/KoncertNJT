package rs.fon.koncert_app.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovi za domensku klasu Muzicar.
 */
class MuzicarTest {

    Muzicar m;

    @BeforeEach
    void setUp() {
        m = new Muzicar();
    }

    @AfterEach
    void tearDown() {
        m = null;
    }

    @Test
    void testSetIme() {
        m.setIme("Marko");
        assertEquals("Marko", m.getIme());
    }

    @Test
    void testSetPrezime() {
        m.setPrezime("Markovic");
        assertEquals("Markovic", m.getPrezime());
    }

    @Test
    void testSetPol() {
        m.setPol("M");
        assertEquals("M", m.getPol());
    }

    @Test
    void testSetKontaktEmail() {
        m.setKontaktEmail("marko@email.com");
        assertEquals("marko@email.com", m.getKontaktEmail());
    }

    @Test
    void testMuzicarJeIzvodjac() {
        assertInstanceOf(Izvodjac.class, m);
    }

    @Test
    void testToStringSadrziIme() {
        m.setIme("Marko");
        m.setPrezime("Markovic");
        assertTrue(m.toString().contains("Marko"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(m.equals(m));
    }

    @Test
    void testEqualsNull() {
        assertFalse(m.equals(null));
    }

    @Test
    void testEqualsDvaMuzicara() {
        m.setId(1L);
        m.setIme("Marko");

        Muzicar m2 = new Muzicar();
        m2.setId(1L);
        m2.setIme("Marko");

        assertEquals(m, m2);
    }

    @Test
    void testNotEqualsDrugIid() {
        m.setId(1L);
        Muzicar m2 = new Muzicar();
        m2.setId(2L);
        assertNotEquals(m, m2);
    }
}