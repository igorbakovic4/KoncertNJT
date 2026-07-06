package rs.fon.koncert_app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.koncert_app.entity.Bend;
import rs.fon.koncert_app.entity.Izvodjac;
import rs.fon.koncert_app.entity.Muzicar;
import rs.fon.koncert_app.repository.IzvodjacRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testovi za sistemske operacije IzvodjacService.
 */
@ExtendWith(MockitoExtension.class)
class IzvodjacServiceTest {

    @Mock
    IzvodjacRepository izvodjacRepository;

    @InjectMocks
    IzvodjacService izvodjacService;

    Muzicar muzicar;
    Bend bend;

    @BeforeEach
    void setUp() {
        muzicar = new Muzicar();
        muzicar.setId(1L);
        muzicar.setIme("Marko");
        muzicar.setPrezime("Markovic");
        muzicar.setKontaktEmail("marko@email.com");

        bend = new Bend();
        bend.setId(2L);
        bend.setNaziv("Bijelo Dugme");
        bend.setBrojClanova(5);
        bend.setKontaktEmail("bend@email.com");
    }

    @AfterEach
    void tearDown() {
        muzicar = null;
        bend = null;
    }

    @Test
    void testDohvatiSve() {
        when(izvodjacRepository.findAll()).thenReturn(List.of(muzicar, bend));

        List<Izvodjac> rezultat = izvodjacService.dohvatiSve();

        assertEquals(2, rezultat.size());
    }

    @Test
    void testDohvatiSvePrazno() {
        when(izvodjacRepository.findAll()).thenReturn(List.of());

        assertTrue(izvodjacService.dohvatiSve().isEmpty());
    }

    @Test
    void testDohvatiPoId() {
        when(izvodjacRepository.findById(1L)).thenReturn(Optional.of(muzicar));

        Izvodjac rezultat = izvodjacService.dohvatiPoId(1L);

        assertNotNull(rezultat);
        assertInstanceOf(Muzicar.class, rezultat);
    }

    @Test
    void testDohvatiPoIdNePostoji() {
        when(izvodjacRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> izvodjacService.dohvatiPoId(99L));
    }

    @Test
    void testSacuvajMuzicara() {
        when(izvodjacRepository.save(muzicar)).thenReturn(muzicar);

        Muzicar rezultat = izvodjacService.sacuvajMuzicara(muzicar);

        assertNotNull(rezultat);
        assertEquals("Marko", rezultat.getIme());
        verify(izvodjacRepository, times(1)).save(muzicar);
    }

    @Test
    void testSacuvajBend() {
        when(izvodjacRepository.save(bend)).thenReturn(bend);

        Bend rezultat = izvodjacService.sacuvajBend(bend);

        assertNotNull(rezultat);
        assertEquals("Bijelo Dugme", rezultat.getNaziv());
    }

    @Test
    void testObrisiIzvodjaca() {
        when(izvodjacRepository.existsById(1L)).thenReturn(true);
        doNothing().when(izvodjacRepository).deleteById(1L);

        assertDoesNotThrow(() -> izvodjacService.obrisiIzvodjaca(1L));
        verify(izvodjacRepository).deleteById(1L);
    }

    @Test
    void testObrisiIzvodjacaNePostoji() {
        when(izvodjacRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> izvodjacService.obrisiIzvodjaca(99L));
    }
}