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
 * Testovi za domensku klasu Grad.
 */
class GradTest {

    Grad g;
    Validator validator;

    @BeforeEach
    void setUp() {
        g = new Grad();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        g = null;
    }

    @Test
    void testSetId() {
        g.setId(1L);
        assertEquals(1L, g.getId());
    }

    @Test
    void testSetNaziv() {
        g.setNaziv("Beograd");
        assertEquals("Beograd", g.getNaziv());
    }

    @Test
    void testSetNazivNull() {
        g.setNaziv(null);
        assertNull(g.getNaziv());
    }

    @Test
    void testToStringSadrziNaziv() {
        g.setNaziv("Beograd");
        assertTrue(g.toString().contains("Beograd"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(g.equals(g));
    }

    @Test
    void testEqualsNull() {
        assertFalse(g.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(g.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
            "1, Beograd, 1, Beograd, true",
            "1, Beograd, 2, Beograd, false",
            "1, Beograd, 1, Nis, false"
    })
    void testEquals(Long id1, String naziv1, Long id2, String naziv2, boolean jednako) {
        g.setId(id1);
        g.setNaziv(naziv1);

        Grad g2 = new Grad();
        g2.setId(id2);
        g2.setNaziv(naziv2);

        assertEquals(jednako, g.equals(g2));
    }

    @Test
    void testHashCodeIstiZaIsteObjekte() {
        g.setId(1L);
        g.setNaziv("Beograd");

        Grad g2 = new Grad();
        g2.setId(1L);
        g2.setNaziv("Beograd");

        assertEquals(g.hashCode(), g2.hashCode());
    }

    @Test
    void testNazivNullValidacija() {
        g.setNaziv(null);
        Set<ConstraintViolation<Grad>> violations = validator.validate(g);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNazivPrazanValidacija() {
        g.setNaziv("");
        Set<ConstraintViolation<Grad>> violations = validator.validate(g);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNazivSamoRazmaciValidacija() {
        g.setNaziv("   ");
        Set<ConstraintViolation<Grad>> violations = validator.validate(g);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNazivPredugiValidacija() {
        g.setNaziv("a".repeat(101));
        Set<ConstraintViolation<Grad>> violations = validator.validate(g);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNazivIspravanValidacija() {
        g.setNaziv("Beograd");
        Set<ConstraintViolation<Grad>> violations = validator.validate(g);
        assertTrue(violations.isEmpty());
    }
}