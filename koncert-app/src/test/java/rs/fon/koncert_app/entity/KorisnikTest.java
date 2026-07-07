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
 * Testovi za domensku klasu Korisnik.
 */
class KorisnikTest {

    Korisnik korisnik;
    Validator validator;

    @BeforeEach
    void setUp() {
        korisnik = new Korisnik();
        korisnik.setId(1L);
        korisnik.setIme("Marko");
        korisnik.setPrezime("Markovic");
        korisnik.setEmail("marko@email.com");
        korisnik.setLozinka("hashed_lozinka");
        korisnik.setUloga(Korisnik.Uloga.KORISNIK);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        korisnik = null;
    }

    @Test
    void testSetIme() {
        korisnik.setIme("Ana");
        assertEquals("Ana", korisnik.getIme());
    }

    @Test
    void testSetPrezime() {
        korisnik.setPrezime("Anic");
        assertEquals("Anic", korisnik.getPrezime());
    }

    @Test
    void testSetEmail() {
        korisnik.setEmail("ana@email.com");
        assertEquals("ana@email.com", korisnik.getEmail());
    }

    @Test
    void testSetLozinka() {
        korisnik.setLozinka("nova_lozinka");
        assertEquals("nova_lozinka", korisnik.getLozinka());
    }

    @Test
    void testSetUlogaKorisnik() {
        korisnik.setUloga(Korisnik.Uloga.KORISNIK);
        assertEquals(Korisnik.Uloga.KORISNIK, korisnik.getUloga());
    }

    @Test
    void testSetUlogaAdmin() {
        korisnik.setUloga(Korisnik.Uloga.ADMIN);
        assertEquals(Korisnik.Uloga.ADMIN, korisnik.getUloga());
    }

    @Test
    void testDveUlogePostoje() {
        assertEquals(2, Korisnik.Uloga.values().length);
    }

    @Test
    void testToStringSadrziEmail() {
        assertTrue(korisnik.toString().contains("marko@email.com"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(korisnik.equals(korisnik));
    }

    @Test
    void testEqualsNull() {
        assertFalse(korisnik.equals(null));
    }

    @ParameterizedTest
    @CsvSource({
            "1, marko@email.com, 1, marko@email.com, true",
            "1, marko@email.com, 2, marko@email.com, false",
            "1, marko@email.com, 1, ana@email.com, false"
    })
    void testEquals(Long id1, String email1, Long id2, String email2, boolean jednako) {
        korisnik.setId(id1);
        korisnik.setEmail(email1);

        Korisnik k2 = new Korisnik();
        k2.setId(id2);
        k2.setEmail(email2);
        k2.setIme("Marko");
        k2.setPrezime("Markovic");
        k2.setLozinka("hashed_lozinka");
        k2.setUloga(Korisnik.Uloga.KORISNIK);

        assertEquals(jednako, korisnik.equals(k2));
    }

    @Test
    void testImeNullValidacija() {
        korisnik.setIme(null);
        assertFalse(validator.validate(korisnik).isEmpty());
    }

    @Test
    void testImePraznoValidacija() {
        korisnik.setIme("");
        assertFalse(validator.validate(korisnik).isEmpty());
    }

    @Test
    void testPrezimeNullValidacija() {
        korisnik.setPrezime(null);
        assertFalse(validator.validate(korisnik).isEmpty());
    }

    @Test
    void testEmailNullValidacija() {
        korisnik.setEmail(null);
        assertFalse(validator.validate(korisnik).isEmpty());
    }

    @Test
    void testEmailNeispravnValidacija() {
        korisnik.setEmail("nije-email");
        assertFalse(validator.validate(korisnik).isEmpty());
    }

    @Test
    void testEmailPredugiValidacija() {
        korisnik.setEmail("a".repeat(95) + "@b.com");
        assertFalse(validator.validate(korisnik).isEmpty());
    }

    @Test
    void testLozinkaPraznaValidacija() {
        korisnik.setLozinka("");
        assertFalse(validator.validate(korisnik).isEmpty());
    }

    @Test
    void testUlogaNullValidacija() {
        korisnik.setUloga(null);
        assertFalse(validator.validate(korisnik).isEmpty());
    }

    @Test
    void testKorisnikIspravanValidacija() {
        assertTrue(validator.validate(korisnik).isEmpty());
    }
}