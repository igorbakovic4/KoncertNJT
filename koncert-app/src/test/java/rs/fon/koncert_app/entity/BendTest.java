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
 * Testovi za domensku klasu Bend.
 */
class BendTest {

    Bend b;
    Validator validator;

    @BeforeEach
    void setUp() {
        b = new Bend();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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

    @Test
    void testNazivNullValidacija() {
        b.setNaziv(null);
        assertFalse(validator.validate(b).isEmpty());
    }

    @Test
    void testNazivPrazanValidacija() {
        b.setNaziv("");
        assertFalse(validator.validate(b).isEmpty());
    }

    @Test
    void testNazivPredugiValidacija() {
        b.setNaziv("a".repeat(101));
        assertFalse(validator.validate(b).isEmpty());
    }

    @Test
    void testBrojClanovaNullValidacija() {
        b.setBrojClanova(null);
        assertFalse(validator.validate(b).isEmpty());
    }

    @Test
    void testBrojClanovaNulaValidacija() {
        b.setBrojClanova(0);
        assertFalse(validator.validate(b).isEmpty());
    }

    @Test
    void testBrojClanovaNegativnaValidacija() {
        b.setBrojClanova(-1);
        assertFalse(validator.validate(b).isEmpty());
    }

    @Test
    void testBendIspravanValidacija() {
        b.setNaziv("Bijelo Dugme");
        b.setBrojClanova(5);
        assertTrue(validator.validate(b).isEmpty());
    }

    @Test
    void testEmailNeispravnValidacija() {
        b.setNaziv("Bijelo Dugme");
        b.setBrojClanova(5);
        b.setKontaktEmail("nije-email");
        assertFalse(validator.validate(b).isEmpty());
    }
}