package rs.fon.koncert_app.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovi za domensku klasu Karta.
 */
class KartaTest {

    Karta karta;
    Koncert koncert;

    @BeforeEach
    void setUp() {
        Grad grad = new Grad();
        grad.setNaziv("Beograd");

        Lokacija lokacija = new Lokacija();
        lokacija.setNaziv("Stark Arena");
        lokacija.setGrad(grad);

        koncert = new Koncert();
        koncert.setId(1L);
        koncert.setNaziv("Exit Festival");
        koncert.setDatum(LocalDate.of(2026, 7, 10));
        koncert.setVremePocetka(LocalTime.of(20, 0));
        koncert.setStatus(Koncert.StatusKoncerta.PLANIRAN);
        koncert.setLokacija(lokacija);

        karta = new Karta();
        karta.setId(1L);
        karta.setRed(1);
        karta.setSediste(5);
        karta.setCena(new BigDecimal("2000.00"));
        karta.setStatus(Karta.StatusKarte.DOSTUPNA);
        karta.setKoncert(koncert);
    }

    @AfterEach
    void tearDown() {
        karta = null;
        koncert = null;
    }

    @Test
    void testSetRed() {
        karta.setRed(3);
        assertEquals(3, karta.getRed());
    }

    @Test
    void testSetSediste() {
        karta.setSediste(10);
        assertEquals(10, karta.getSediste());
    }

    @Test
    void testSetCena() {
        karta.setCena(new BigDecimal("3000.00"));
        assertEquals(new BigDecimal("3000.00"), karta.getCena());
    }

    @Test
    void testSetStatusDostupna() {
        karta.setStatus(Karta.StatusKarte.DOSTUPNA);
        assertEquals(Karta.StatusKarte.DOSTUPNA, karta.getStatus());
    }

    @Test
    void testSetStatusProdata() {
        karta.setStatus(Karta.StatusKarte.PRODATA);
        assertEquals(Karta.StatusKarte.PRODATA, karta.getStatus());
    }

    @Test
    void testSetStatusRezervirana() {
        karta.setStatus(Karta.StatusKarte.REZERVISANA);
        assertEquals(Karta.StatusKarte.REZERVISANA, karta.getStatus());
    }

    @Test
    void testSetStatusStornirana() {
        karta.setStatus(Karta.StatusKarte.STORNIRANA);
        assertEquals(Karta.StatusKarte.STORNIRANA, karta.getStatus());
    }

    @Test
    void testCetiriStatusaPostoje() {
        assertEquals(4, Karta.StatusKarte.values().length);
    }

    @Test
    void testSetImeKupca() {
        karta.setImeKupca("Marko Markovic");
        assertEquals("Marko Markovic", karta.getImeKupca());
    }

    @Test
    void testSetEmailKupca() {
        karta.setEmailKupca("marko@email.com");
        assertEquals("marko@email.com", karta.getEmailKupca());
    }

    @Test
    void testSetDatumProdaje() {
        LocalDateTime sada = LocalDateTime.now();
        karta.setDatumProdaje(sada);
        assertEquals(sada, karta.getDatumProdaje());
    }

    @Test
    void testSetKoncert() {
        assertNotNull(karta.getKoncert());
        assertEquals("Exit Festival", karta.getKoncert().getNaziv());
    }

    @Test
    void testToStringSadrziRed() {
        assertTrue(karta.toString().contains("1"));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(karta.equals(karta));
    }

    @Test
    void testEqualsNull() {
        assertFalse(karta.equals(null));
    }

    @Test
    void testEqualsDveKarte() {
        Karta k2 = new Karta();
        k2.setId(1L);
        k2.setRed(1);
        k2.setSediste(5);
        k2.setCena(new BigDecimal("2000.00"));
        k2.setStatus(Karta.StatusKarte.DOSTUPNA);
        k2.setKoncert(koncert);
        assertEquals(karta, k2);
    }

    @Test
    void testNotEqualsDrugiId() {
        Karta k2 = new Karta();
        k2.setId(2L);
        assertNotEquals(karta, k2);
    }
}