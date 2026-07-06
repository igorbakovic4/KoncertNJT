package rs.fon.koncert_app.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovi za domensku klasu Bend.
 */
class BendTest {

    Bend b;

    @BeforeEach
    void setUp() {
        b = new Bend();
    }

    @AfterEach
    void tearDown() {
        b = null;
    }

    @Test
    void testSetNaziv() {
        b.setNaziv("Bijelo Dugme");
        assertEquals("Bijelo Dugme", b.getNaziv());
    }

    @Test
    void testSetBrojClanova() {
        b.setBrojClanova(5);
        assertEquals(5, b.getBrojClanova());
    }

    @Test
    void testSetKontaktEmail() {
        b.setKontaktEmail("bend@email.com");
        assertEquals("bend@email.com", b.getKontaktEmail());
    }

    @Test
    void testBendJeIzvodjac() {
        assertInstanceOf(Izvodjac.class, b);
    }

    @Test
    void testToStringSadrziNaziv() {
        b.setNaziv("Bijelo Dugme");
        assertTrue(b.toString().contains("Bijelo Dugme"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(b.equals(b));
    }

    @Test
    void testEqualsNull() {
        assertFalse(b.equals(null));
    }

    @ParameterizedTest
    @CsvSource({
            "1, Bijelo Dugme, 1, Bijelo Dugme, true",
            "1, Bijelo Dugme, 2, Bijelo Dugme, false",
            "1, Bijelo Dugme, 1, Riblja Corba, false"
    })
    void testEquals(Long id1, String naziv1, Long id2, String naziv2, boolean jednako) {
        b.setId(id1);
        b.setNaziv(naziv1);

        Bend b2 = new Bend();
        b2.setId(id2);
        b2.setNaziv(naziv2);

        assertEquals(jednako, b.equals(b2));
    }
}