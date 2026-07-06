package rs.fon.koncert_app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.koncert_app.entity.Grad;
import rs.fon.koncert_app.entity.Lokacija;
import rs.fon.koncert_app.repository.LokacijaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testovi za sistemske operacije LokacijaService.
 */
@ExtendWith(MockitoExtension.class)
class LokacijaServiceTest {

    @Mock
    LokacijaRepository lokacijaRepository;

    @Mock
    GradService gradService;

    @InjectMocks
    LokacijaService lokacijaService;

    Lokacija lokacija;
    Grad grad;

    @BeforeEach
    void setUp() {
        grad = new Grad();
        grad.setId(1L);
        grad.setNaziv("Beograd");

        lokacija = new Lokacija();
        lokacija.setId(1L);
        lokacija.setNaziv("Stark Arena");
        lokacija.setAdresa("Bulevar 58");
        lokacija.setKapacitet(20000);
        lokacija.setGrad(grad);
    }

    @AfterEach
    void tearDown() {
        lokacija = null;
        grad = null;
    }

    @Test
    void testFindAll() {
        when(lokacijaRepository.findAll()).thenReturn(List.of(lokacija));

        List<Lokacija> rezultat = lokacijaService.findAll();

        assertEquals(1, rezultat.size());
        assertEquals("Stark Arena", rezultat.get(0).getNaziv());
    }

    @Test
    void testFindAllPrazno() {
        when(lokacijaRepository.findAll()).thenReturn(List.of());

        assertTrue(lokacijaService.findAll().isEmpty());
    }

    @Test
    void testFindById() {
        when(lokacijaRepository.findById(1L)).thenReturn(Optional.of(lokacija));

        Lokacija rezultat = lokacijaService.findById(1L);

        assertNotNull(rezultat);
        assertEquals("Stark Arena", rezultat.getNaziv());
    }

    @Test
    void testFindByIdNePostoji() {
        when(lokacijaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lokacijaService.findById(99L));
    }

    @Test
    void testSave() {
        when(gradService.findById(1L)).thenReturn(grad);
        when(lokacijaRepository.save(lokacija)).thenReturn(lokacija);

        Lokacija rezultat = lokacijaService.save(lokacija);

        assertNotNull(rezultat);
        verify(lokacijaRepository, times(1)).save(lokacija);
    }

    @Test
    void testSaveGradNePostoji() {
        when(gradService.findById(1L))
                .thenThrow(new RuntimeException("Grad ne postoji."));

        assertThrows(RuntimeException.class, () -> lokacijaService.save(lokacija));
    }

    @Test
    void testUpdate() {
        Lokacija izmenjenaLokacija = new Lokacija();
        izmenjenaLokacija.setNaziv("Kombank Arena");
        izmenjenaLokacija.setAdresa("Nova adresa");
        izmenjenaLokacija.setKapacitet(5000);
        izmenjenaLokacija.setGrad(grad);

        when(lokacijaRepository.findById(1L)).thenReturn(Optional.of(lokacija));
        when(lokacijaRepository.save(any())).thenReturn(lokacija);

        Lokacija rezultat = lokacijaService.update(1L, izmenjenaLokacija);

        assertNotNull(rezultat);
        verify(lokacijaRepository).save(any());
    }

    @Test
    void testUpdateNePostoji() {
        when(lokacijaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> lokacijaService.update(99L, lokacija));
    }

    @Test
    void testDelete() {
        when(lokacijaRepository.findById(1L)).thenReturn(Optional.of(lokacija));
        doNothing().when(lokacijaRepository).delete(lokacija);

        assertDoesNotThrow(() -> lokacijaService.delete(1L));
        verify(lokacijaRepository).delete(lokacija);
    }

    @Test
    void testDeleteNePostoji() {
        when(lokacijaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> lokacijaService.delete(99L));
    }
}