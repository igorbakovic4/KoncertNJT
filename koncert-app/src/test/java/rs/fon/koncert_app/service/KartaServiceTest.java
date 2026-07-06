package rs.fon.koncert_app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.koncert_app.entity.*;
import rs.fon.koncert_app.repository.KartaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testovi za sistemske operacije KartaService.
 */
@ExtendWith(MockitoExtension.class)
class KartaServiceTest {

    @Mock
    KartaRepository kartaRepository;

    @InjectMocks
    KartaService kartaService;

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
        koncert.setStatus(Koncert.StatusKoncerta.AKTIVAN);
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
    void testDohvatiSveZaKoncert() {
        when(kartaRepository.findAll()).thenReturn(List.of(karta));

        List<Karta> rezultat = kartaService.dohvatiSveZaKoncert(1L);

        assertEquals(1, rezultat.size());
        assertEquals(1, rezultat.get(0).getRed());
    }

    @Test
    void testDohvatiSveZaKoncertPrazno() {
        when(kartaRepository.findAll()).thenReturn(List.of());

        assertTrue(kartaService.dohvatiSveZaKoncert(1L).isEmpty());
    }

    @Test
    void testKupiKartuDostupna() {
        when(kartaRepository.findFirstByKoncertIdAndStatus(1L, Karta.StatusKarte.DOSTUPNA))
                .thenReturn(Optional.of(karta));
        when(kartaRepository.save(any())).thenReturn(karta);

        Karta rezultat = kartaService.kupiKartu(1L, "Marko", "marko@email.com");

        assertEquals(Karta.StatusKarte.PRODATA, rezultat.getStatus());
        assertEquals("Marko", rezultat.getImeKupca());
        assertEquals("marko@email.com", rezultat.getEmailKupca());
        assertNotNull(rezultat.getDatumProdaje());
    }

    @Test
    void testKupiKartuNemaDostupnih() {
        when(kartaRepository.findFirstByKoncertIdAndStatus(1L, Karta.StatusKarte.DOSTUPNA))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> kartaService.kupiKartu(1L, "Marko", "marko@email.com"));
    }

    @Test
    void testStornirajKartu() {
        when(kartaRepository.findById(1L)).thenReturn(Optional.of(karta));
        when(kartaRepository.save(any())).thenReturn(karta);

        Karta rezultat = kartaService.stornirajKartu(1L);

        assertEquals(Karta.StatusKarte.STORNIRANA, rezultat.getStatus());
        verify(kartaRepository, times(1)).save(karta);
    }

    @Test
    void testStornirajKartuNePostoji() {
        when(kartaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> kartaService.stornirajKartu(99L));
    }

    @Test
    void testGetSummaryImaDostupnih() {
        when(kartaRepository.countByKoncertIdAndStatus(1L, Karta.StatusKarte.DOSTUPNA))
                .thenReturn(5L);
        when(kartaRepository.findByKoncertId(1L)).thenReturn(List.of(karta));
        when(kartaRepository.findFirstByKoncertIdAndStatus(1L, Karta.StatusKarte.DOSTUPNA))
                .thenReturn(Optional.of(karta));

        Map<String, Object> summary = kartaService.getSummary(1L);

        assertEquals(5L, summary.get("dostupne"));
        assertEquals(Long.valueOf(1), summary.get("ukupno"));
        assertEquals(new BigDecimal("2000.00"), summary.get("cena"));
    }

    @Test
    void testGetSummaryNemaDostupnih() {
        when(kartaRepository.countByKoncertIdAndStatus(1L, Karta.StatusKarte.DOSTUPNA))
                .thenReturn(0L);
        when(kartaRepository.findByKoncertId(1L)).thenReturn(List.of());
        when(kartaRepository.findFirstByKoncertIdAndStatus(1L, Karta.StatusKarte.DOSTUPNA))
                .thenReturn(Optional.empty());

        Map<String, Object> summary = kartaService.getSummary(1L);

        assertEquals(0L, summary.get("dostupne"));
        assertEquals(BigDecimal.ZERO, summary.get("cena"));
    }
}