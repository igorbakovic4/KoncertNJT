package rs.fon.koncert_app.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovi za domensku klasu Lokacija.
 */
class LokacijaTest {

    Lokacija l;
    Grad grad;
    Validator validator;

    @BeforeEach
    void setUp() {
        grad = new Grad();
        grad.setId(1L);
        grad.setNaziv("Beograd");

        l = new Lokacija();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        l = null;
        grad = null;
    }

    @Test
    void testSetNaziv() {
        l.setNaziv("Stark Arena");
        assertEquals("Stark Arena", l.getNaziv());
    }

    @Test
    void testSetAdresa() {
        l.setAdresa("Bulevar 58");
        assertEquals("Bulevar 58", l.getAdresa());
    }

    @Test
    void testSetKapacitet() {
        l.setKapacitet(20000);
        assertEquals(20000, l.getKapacitet());
    }

    @Test
    void testSetKapacitetNula() {
        l.setKapacitet(0);
        assertEquals(0, l.getKapacitet());
    }

    @Test
    void testSetGrad() {
        l.setGrad(grad);
        assertNotNull(l.getGrad());
        assertEquals("Beograd", l.getGrad().getNaziv());
    }

    @Test
    void testSetGradNull() {
        l.setGrad(null);
        assertNull(l.getGrad());
    }

    @Test
    void testToStringSadrziNaziv() {
        l.setNaziv("Stark Arena");
        assertTrue(l.toString().contains("Stark Arena"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(l.equals(l));
    }

    @Test
    void testEqualsNull() {
        assertFalse(l.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(l.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
            "1, Stark Arena, 1, Stark Arena, true",
            "1, Stark Arena, 2, Stark Arena, false",
            "1, Stark Arena, 1, Kombank Arena, false"
    })
    void testEquals(Long id1, String naziv1, Long id2, String naziv2, boolean jednako) {
        l.setId(id1);
        l.setNaziv(naziv1);

        Lokacija l2 = new Lokacija();
        l2.setId(id2);
        l2.setNaziv(naziv2);

        assertEquals(jednako, l.equals(l2));
    }

    @Test
    void testNazivNullValidacija() {
        l.setNaziv(null);
        assertFalse(validator.validate(l).isEmpty());
    }

    @Test
    void testNazivPrazanValidacija() {
        l.setNaziv("");
        assertFalse(validator.validate(l).isEmpty());
    }

    @Test
    void testNazivPredugiValidacija() {
        l.setNaziv("a".repeat(151));
        assertFalse(validator.validate(l).isEmpty());
    }

    @Test
    void testAdresaNullValidacija() {
        l.setAdresa(null);
        assertFalse(validator.validate(l).isEmpty());
    }

    @Test
    void testAdresaPraznaValidacija() {
        l.setAdresa("");
        assertFalse(validator.validate(l).isEmpty());
    }

    @Test
    void testKapacitetNullValidacija() {
        l.setKapacitet(null);
        assertFalse(validator.validate(l).isEmpty());
    }

    @Test
    void testKapacitetNulaValidacija() {
        l.setKapacitet(0);
        assertFalse(validator.validate(l).isEmpty());
    }

    @Test
    void testKapacitetNegativnaValidacija() {
        l.setKapacitet(-1);
        assertFalse(validator.validate(l).isEmpty());
    }

    @Test
    void testGradNullValidacija() {
        l.setGrad(null);
        assertFalse(validator.validate(l).isEmpty());
    }

    @Test
    void testLokacijaIspravnaValidacija() {
        l.setNaziv("Stark Arena");
        l.setAdresa("Bulevar 58");
        l.setKapacitet(20000);
        l.setGrad(grad);
        assertTrue(validator.validate(l).isEmpty());
    }
}