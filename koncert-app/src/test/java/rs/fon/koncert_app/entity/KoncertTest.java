package rs.fon.koncert_app.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovi za domensku klasu Koncert.
 */
class KoncertTest {

    Koncert k;
    Lokacija lokacija;

    @BeforeEach
    void setUp() {
        Grad grad = new Grad();
        grad.setId(1L);
        grad.setNaziv("Beograd");

        lokacija = new Lokacija();
        lokacija.setId(1L);
        lokacija.setNaziv("Stark Arena");
        lokacija.setKapacitet(20000);
        lokacija.setGrad(grad);

        k = new Koncert();
        k.setId(1L);
        k.setNaziv("Exit Festival");
        k.setDatum(LocalDate.of(2026, 7, 10));
        k.setVremePocetka(LocalTime.of(20, 0));
        k.setVremeTrajanja(180);
        k.setStatus(Koncert.StatusKoncerta.PLANIRAN);
        k.setLokacija(lokacija);
        k.setIzvodjaci(new ArrayList<>());
    }

    @AfterEach
    void tearDown() {
        k = null;
        lokacija = null;
    }

    @Test
    void testSetNaziv() {
        k.setNaziv("Rock Fest");
        assertEquals("Rock Fest", k.getNaziv());
    }

    @Test
    void testSetDatum() {
        k.setDatum(LocalDate.of(2027, 1, 1));
        assertEquals(LocalDate.of(2027, 1, 1), k.getDatum());
    }

    @Test
    void testSetVremePocetka() {
        k.setVremePocetka(LocalTime.of(18, 30));
        assertEquals(LocalTime.of(18, 30), k.getVremePocetka());
    }

    @Test
    void testSetVremeTrajanja() {
        k.setVremeTrajanja(120);
        assertEquals(120, k.getVremeTrajanja());
    }

    @Test
    void testSetStatusPlaniran() {
        k.setStatus(Koncert.StatusKoncerta.PLANIRAN);
        assertEquals(Koncert.StatusKoncerta.PLANIRAN, k.getStatus());
    }

    @Test
    void testSetStatusAktivan() {
        k.setStatus(Koncert.StatusKoncerta.AKTIVAN);
        assertEquals(Koncert.StatusKoncerta.AKTIVAN, k.getStatus());
    }

    @Test
    void testSetStatusOtkazan() {
        k.setStatus(Koncert.StatusKoncerta.OTKAZAN);
        assertEquals(Koncert.StatusKoncerta.OTKAZAN, k.getStatus());
    }

    @Test
    void testSetStatusZavrsen() {
        k.setStatus(Koncert.StatusKoncerta.ZAVRSEN);
        assertEquals(Koncert.StatusKoncerta.ZAVRSEN, k.getStatus());
    }

    @Test
    void testSetLokacija() {
        assertNotNull(k.getLokacija());
        assertEquals("Stark Arena", k.getLokacija().getNaziv());
    }

    @Test
    void testCetiriStatusaPostoje() {
        assertEquals(4, Koncert.StatusKoncerta.values().length);
    }

    @Test
    void testToStringSadrziNaziv() {
        assertTrue(k.toString().contains("Exit Festival"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(k.equals(k));
    }

    @Test
    void testEqualsNull() {
        assertFalse(k.equals(null));
    }

    @Test
    void testEqualsDvaKoncerta() {
        Koncert k2 = new Koncert();
        k2.setId(1L);
        k2.setNaziv("Exit Festival");
        k2.setDatum(LocalDate.of(2026, 7, 10));
        k2.setVremePocetka(LocalTime.of(20, 0));
        k2.setVremeTrajanja(180);
        k2.setStatus(Koncert.StatusKoncerta.PLANIRAN);
        k2.setLokacija(lokacija);
        k2.setIzvodjaci(new ArrayList<>());
        assertEquals(k, k2);
    }

    @Test
    void testNotEqualsDrugiId() {
        Koncert k2 = new Koncert();
        k2.setId(2L);
        assertNotEquals(k, k2);
    }
}