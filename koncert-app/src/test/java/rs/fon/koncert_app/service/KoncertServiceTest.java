package rs.fon.koncert_app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.koncert_app.entity.*;
import rs.fon.koncert_app.repository.IzvodjacRepository;
import rs.fon.koncert_app.repository.KartaRepository;
import rs.fon.koncert_app.repository.KoncertRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testovi za sistemske operacije KoncertService.
 */
@ExtendWith(MockitoExtension.class)
class KoncertServiceTest {

    @Mock
    KoncertRepository koncertRepository;

    @Mock
    IzvodjacRepository izvodjacRepository;

    @Mock
    KartaRepository kartaRepository;

    @Mock
    LokacijaService lokacijaService;

    @InjectMocks
    KoncertService koncertService;

    Koncert koncert;
    Lokacija lokacija;

    @BeforeEach
    void setUp() {
        Grad grad = new Grad();
        grad.setId(1L);
        grad.setNaziv("Beograd");

        lokacija = new Lokacija();
        lokacija.setId(1L);
        lokacija.setNaziv("Stark Arena");
        lokacija.setKapacitet(100);
        lokacija.setGrad(grad);

        koncert = new Koncert();
        koncert.setId(1L);
        koncert.setNaziv("Exit Festival");
        koncert.setDatum(LocalDate.of(2026, 7, 10));
        koncert.setVremePocetka(LocalTime.of(20, 0));
        koncert.setVremeTrajanja(180);
        koncert.setStatus(Koncert.StatusKoncerta.PLANIRAN);
        koncert.setLokacija(lokacija);
        koncert.setIzvodjaci(new ArrayList<>());
    }

    @AfterEach
    void tearDown() {
        koncert = null;
        lokacija = null;
    }

    @Test
    void testFindAll() {
        when(koncertRepository.findAll()).thenReturn(List.of(koncert));

        List<Koncert> rezultat = koncertService.findAll();

        assertEquals(1, rezultat.size());
        assertEquals("Exit Festival", rezultat.get(0).getNaziv());
    }

    @Test
    void testFindAllPrazno() {
        when(koncertRepository.findAll()).thenReturn(List.of());

        assertTrue(koncertService.findAll().isEmpty());
    }

    @Test
    void testFindById() {
        when(koncertRepository.findById(1L)).thenReturn(Optional.of(koncert));

        Koncert rezultat = koncertService.findById(1L);

        assertNotNull(rezultat);
        assertEquals("Exit Festival", rezultat.getNaziv());
    }

    @Test
    void testFindByIdNePostoji() {
        when(koncertRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> koncertService.findById(99L));
    }

    @Test
    void testFindByStatus() {
        when(koncertRepository.findByStatus(Koncert.StatusKoncerta.PLANIRAN))
                .thenReturn(List.of(koncert));

        List<Koncert> rezultat = koncertService.findByStatus(Koncert.StatusKoncerta.PLANIRAN);

        assertEquals(1, rezultat.size());
        assertEquals(Koncert.StatusKoncerta.PLANIRAN, rezultat.get(0).getStatus());
    }

    @Test
    void testFindByDatum() {
        LocalDate datum = LocalDate.of(2026, 7, 10);
        when(koncertRepository.findByDatum(datum)).thenReturn(List.of(koncert));

        List<Koncert> rezultat = koncertService.findByDatum(datum);

        assertEquals(1, rezultat.size());
        assertEquals(datum, rezultat.get(0).getDatum());
    }

    @Test
    void testSaveTerminSlobodan() {
        when(lokacijaService.findById(1L)).thenReturn(lokacija);
        when(koncertRepository.findByLokacijaAndDatumAndVremePocetka(
                any(), any(), any()))
                .thenReturn(Optional.empty());
        when(koncertRepository.save(koncert)).thenReturn(koncert);

        Koncert rezultat = koncertService.save(koncert);

        assertNotNull(rezultat);
        verify(koncertRepository).save(koncert);
    }

    @Test
    void testSaveTerminZauzet() {
        when(lokacijaService.findById(1L)).thenReturn(lokacija);
        when(koncertRepository.findByLokacijaAndDatumAndVremePocetka(
                any(), any(), any()))
                .thenReturn(Optional.of(koncert));

        assertThrows(RuntimeException.class, () -> koncertService.save(koncert));
    }

    @Test
    void testUpdate() {
        Koncert izmenjeni = new Koncert();
        izmenjeni.setNaziv("Rock Fest");
        izmenjeni.setDatum(LocalDate.of(2026, 8, 1));
        izmenjeni.setVremePocetka(LocalTime.of(19, 0));
        izmenjeni.setVremeTrajanja(120);
        izmenjeni.setStatus(Koncert.StatusKoncerta.AKTIVAN);
        izmenjeni.setLokacija(lokacija);
        izmenjeni.setIzvodjaci(new ArrayList<>());

        when(koncertRepository.findById(1L)).thenReturn(Optional.of(koncert));
        when(koncertRepository.save(any())).thenReturn(koncert);

        Koncert rezultat = koncertService.update(1L, izmenjeni);

        assertNotNull(rezultat);
        verify(koncertRepository).save(any());
    }

    @Test
    void testUpdateNePostoji() {
        when(koncertRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> koncertService.update(99L, koncert));
    }

    @Test
    void testDelete() {
        when(koncertRepository.findById(1L)).thenReturn(Optional.of(koncert));
        doNothing().when(koncertRepository).delete(koncert);

        assertDoesNotThrow(() -> koncertService.delete(1L));
        verify(koncertRepository).delete(koncert);
    }

    @Test
    void testDeleteNePostoji() {
        when(koncertRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> koncertService.delete(99L));
    }

    @Test
    void testGenerisiKarte() {
        when(koncertRepository.findById(1L)).thenReturn(Optional.of(koncert));
        when(kartaRepository.findByKoncertId(1L)).thenReturn(List.of());
        when(kartaRepository.saveAll(anyList()))
                .thenAnswer(i -> i.getArguments()[0]);

        List<Karta> karte = koncertService.generisiKarte(1L, new BigDecimal("2000"));

        assertFalse(karte.isEmpty());
        karte.forEach(k -> {
            assertEquals(Karta.StatusKarte.DOSTUPNA, k.getStatus());
            assertEquals(new BigDecimal("2000"), k.getCena());
        });
    }

    @Test
    void testGenerisiKarteVecPostoje() {
        Karta postojecaKarta = new Karta();
        when(koncertRepository.findById(1L)).thenReturn(Optional.of(koncert));
        when(kartaRepository.findByKoncertId(1L))
                .thenReturn(List.of(postojecaKarta));

        assertThrows(RuntimeException.class,
                () -> koncertService.generisiKarte(1L, new BigDecimal("2000")));
    }

    @Test
    void testUpdateIzvodjaci() {
        List<Long> ids = List.of(1L, 2L);
        Muzicar m = new Muzicar();
        m.setId(1L);

        when(koncertRepository.findById(1L)).thenReturn(Optional.of(koncert));
        when(izvodjacRepository.findAllById(ids)).thenReturn(List.of(m));
        when(koncertRepository.save(any())).thenReturn(koncert);

        Koncert rezultat = koncertService.updateIzvodjaci(1L, ids);

        assertNotNull(rezultat);
        verify(koncertRepository).save(any());
    }
}