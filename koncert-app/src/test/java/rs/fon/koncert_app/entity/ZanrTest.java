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
 * Testovi za domensku klasu Zanr.
 */
class ZanrTest {

    Zanr z;
    Validator validator;

    @BeforeEach
    void setUp() {
        z = new Zanr();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        z = null;
    }

    @Test
    void testSetNaziv() {
        z.setNaziv("Rock");
        assertEquals("Rock", z.getNaziv());
    }

    @Test
    void testSetId() {
        z.setId(1L);
        assertEquals(1L, z.getId());
    }

    @Test
    void testToStringSadrziNaziv() {
        z.setNaziv("Rock");
        assertTrue(z.toString().contains("Rock"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(z.equals(z));
    }

    @Test
    void testEqualsNull() {
        assertFalse(z.equals(null));
    }

    @Test
    void testEqualsDrugaKlasa() {
        assertFalse(z.equals(new String()));
    }

    @ParameterizedTest
    @CsvSource({
            "1, Rock, 1, Rock, true",
            "1, Rock, 2, Rock, false",
            "1, Rock, 1, Pop, false"
    })
    void testEquals(Long id1, String naziv1, Long id2, String naziv2, boolean jednako) {
        z.setId(id1);
        z.setNaziv(naziv1);

        Zanr z2 = new Zanr();
        z2.setId(id2);
        z2.setNaziv(naziv2);

        assertEquals(jednako, z.equals(z2));
    }

    @Test
    void testNazivNullValidacija() {
        z.setNaziv(null);
        assertFalse(validator.validate(z).isEmpty());
    }

    @Test
    void testNazivPrazanValidacija() {
        z.setNaziv("");
        assertFalse(validator.validate(z).isEmpty());
    }

    @Test
    void testNazivPredugiValidacija() {
        z.setNaziv("a".repeat(51));
        assertFalse(validator.validate(z).isEmpty());
    }

    @Test
    void testZanrIspravanValidacija() {
        z.setNaziv("Rock");
        assertTrue(validator.validate(z).isEmpty());
    }
}