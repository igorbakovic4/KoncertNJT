package rs.fon.koncert_app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.koncert_app.entity.Grad;
import rs.fon.koncert_app.repository.GradRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testovi za sistemske operacije GradService.
 */
@ExtendWith(MockitoExtension.class)
class GradServiceTest {

    @Mock
    GradRepository gradRepository;

    @InjectMocks
    GradService gradService;

    Grad grad;

    @BeforeEach
    void setUp() {
        grad = new Grad();
        grad.setId(1L);
        grad.setNaziv("Beograd");
    }

    @AfterEach
    void tearDown() {
        grad = null;
    }

    @Test
    void testFindAll() {
        when(gradRepository.findAll()).thenReturn(List.of(grad));

        List<Grad> rezultat = gradService.findAll();

        assertEquals(1, rezultat.size());
        assertEquals("Beograd", rezultat.get(0).getNaziv());
        verify(gradRepository, times(1)).findAll();
    }

    @Test
    void testFindAllPrazno() {
        when(gradRepository.findAll()).thenReturn(List.of());

        List<Grad> rezultat = gradService.findAll();

        assertTrue(rezultat.isEmpty());
    }

    @Test
    void testFindById() {
        when(gradRepository.findById(1L)).thenReturn(Optional.of(grad));

        Grad rezultat = gradService.findById(1L);

        assertNotNull(rezultat);
        assertEquals("Beograd", rezultat.getNaziv());
    }

    @Test
    void testFindByIdNePostoji() {
        when(gradRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gradService.findById(99L));
    }

    @Test
    void testSave() {
        when(gradRepository.save(grad)).thenReturn(grad);

        Grad rezultat = gradService.save(grad);

        assertNotNull(rezultat);
        assertEquals("Beograd", rezultat.getNaziv());
        verify(gradRepository, times(1)).save(grad);
    }

    @Test
    void testUpdate() {
        Grad izmenjeni = new Grad();
        izmenjeni.setNaziv("Novi Sad");

        when(gradRepository.findById(1L)).thenReturn(Optional.of(grad));
        when(gradRepository.save(any(Grad.class))).thenReturn(grad);

        Grad rezultat = gradService.update(1L, izmenjeni);

        assertNotNull(rezultat);
        verify(gradRepository, times(1)).save(any(Grad.class));
    }

    @Test
    void testUpdateNePostoji() {
        when(gradRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gradService.update(99L, grad));
    }

    @Test
    void testDelete() {
        when(gradRepository.findById(1L)).thenReturn(Optional.of(grad));
        doNothing().when(gradRepository).delete(grad);

        assertDoesNotThrow(() -> gradService.delete(1L));
        verify(gradRepository, times(1)).delete(grad);
    }

    @Test
    void testDeleteNePostoji() {
        when(gradRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gradService.delete(99L));
    }
}