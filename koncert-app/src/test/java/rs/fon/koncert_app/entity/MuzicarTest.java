package rs.fon.koncert_app.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovi za domensku klasu Muzicar.
 */
class MuzicarTest {

    Muzicar m;
    Validator validator;

    @BeforeEach
    void setUp() {
        m = new Muzicar();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
    void testNotEqualsDrugiId() {
        m.setId(1L);
        Muzicar m2 = new Muzicar();
        m2.setId(2L);
        assertNotEquals(m, m2);
    }

    @Test
    void testImeNullValidacija() {
        m.setIme(null);
        assertFalse(validator.validate(m).isEmpty());
    }

    @Test
    void testImePraznValidacija() {
        m.setIme("");
        assertFalse(validator.validate(m).isEmpty());
    }

    @Test
    void testImePredugoValidacija() {
        m.setIme("a".repeat(51));
        assertFalse(validator.validate(m).isEmpty());
    }

    @Test
    void testPrezimeNullValidacija() {
        m.setPrezime(null);
        assertFalse(validator.validate(m).isEmpty());
    }

    @Test
    void testPrezimePraznoValidacija() {
        m.setPrezime("");
        assertFalse(validator.validate(m).isEmpty());
    }

    @Test
    void testPolNeispravnaValidacija() {
        m.setIme("Marko");
        m.setPrezime("Markovic");
        m.setPol("X");
        assertFalse(validator.validate(m).isEmpty());
    }

    @Test
    void testPolMValidacija() {
        m.setIme("Marko");
        m.setPrezime("Markovic");
        m.setPol("M");
        assertTrue(validator.validate(m).isEmpty());
    }

    @Test
    void testPolZValidacija() {
        m.setIme("Ana");
        m.setPrezime("Anic");
        m.setPol("Z");
        assertTrue(validator.validate(m).isEmpty());
    }

    @Test
    void testEmailNeispravnValidacija() {
        m.setIme("Marko");
        m.setPrezime("Markovic");
        m.setKontaktEmail("nije-email");
        assertFalse(validator.validate(m).isEmpty());
    }

    @Test
    void testEmailIspravanValidacija() {
        m.setIme("Marko");
        m.setPrezime("Markovic");
        m.setKontaktEmail("marko@email.com");
        assertTrue(validator.validate(m).isEmpty());
    }
}