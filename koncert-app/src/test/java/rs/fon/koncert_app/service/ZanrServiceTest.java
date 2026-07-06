package rs.fon.koncert_app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.koncert_app.entity.Zanr;
import rs.fon.koncert_app.repository.ZanrRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testovi za sistemske operacije ZanrService.
 */
@ExtendWith(MockitoExtension.class)
class ZanrServiceTest {

    @Mock
    ZanrRepository zanrRepository;

    @InjectMocks
    ZanrService zanrService;

    Zanr zanr;

    @BeforeEach
    void setUp() {
        zanr = new Zanr();
        zanr.setId(1L);
        zanr.setNaziv("Rock");
    }

    @AfterEach
    void tearDown() {
        zanr = null;
    }

    @Test
    void testDohvatiSve() {
        when(zanrRepository.findAll()).thenReturn(List.of(zanr));

        List<Zanr> rezultat = zanrService.dohvatiSve();

        assertEquals(1, rezultat.size());
        assertEquals("Rock", rezultat.get(0).getNaziv());
    }

    @Test
    void testDohvatiSvePrazno() {
        when(zanrRepository.findAll()).thenReturn(List.of());

        assertTrue(zanrService.dohvatiSve().isEmpty());
    }

    @Test
    void testDohvatiPoId() {
        when(zanrRepository.findById(1L)).thenReturn(Optional.of(zanr));

        Zanr rezultat = zanrService.dohvatiPoId(1L);

        assertNotNull(rezultat);
        assertEquals("Rock", rezultat.getNaziv());
    }

    @Test
    void testDohvatiPoIdNePostoji() {
        when(zanrRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> zanrService.dohvatiPoId(99L));
    }

    @Test
    void testSacuvajZanr() {
        when(zanrRepository.save(zanr)).thenReturn(zanr);

        Zanr rezultat = zanrService.sacuvajZanr(zanr);

        assertNotNull(rezultat);
        assertEquals("Rock", rezultat.getNaziv());
        verify(zanrRepository, times(1)).save(zanr);
    }

    @Test
    void testObrisiZanr() {
        when(zanrRepository.existsById(1L)).thenReturn(true);
        doNothing().when(zanrRepository).deleteById(1L);

        assertDoesNotThrow(() -> zanrService.obrisiZanr(1L));
        verify(zanrRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObrisiZanrNePostoji() {
        when(zanrRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> zanrService.obrisiZanr(99L));
    }
}