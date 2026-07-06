package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Grad;
import rs.fon.koncert_app.repository.GradRepository;

import java.util.List;

/**
 * Servis koji implementira sistemske operacije za upravljanje gradovima.
 *
 * Pruza CRUD operacije nad domenskom klasom Grad.
 *
 * @author igor
 * @see Grad
 */
@Service
@RequiredArgsConstructor
public class GradService {

    private final GradRepository gradRepository;

    /**
     * Vraca listu svih gradova iz baze podataka.
     *
     * @return lista svih gradova, ili prazna lista ako nema gradova.
     */
    public List<Grad> findAll() {
        return gradRepository.findAll();
    }

    /**
     * Vraca grad sa prosledjenim identifikatorom.
     *
     * @param id Identifikator grada koji se trazi.
     * @return grad sa prosledjenim identifikatorom.
     * @throws java.lang.RuntimeException Ukoliko grad sa prosledjenim id-em ne postoji.
     */
    public Grad findById(Long id) {
        return gradRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grad sa id-em " + id + " ne postoji."));
    }

    /**
     * Cuva prosledjeni grad u bazi podataka.
     *
     * @param grad Grad koji treba sacuvati.
     * @return sacuvani grad sa dodeljenim identifikatorom.
     */
    public Grad save(Grad grad) {
        return gradRepository.save(grad);
    }

    /**
     * Azurira podatke o gradu sa prosledjenim identifikatorom.
     *
     * @param id Identifikator grada koji treba azurirati.
     * @param grad Grad sa novim podacima.
     * @return azurirani grad.
     * @throws java.lang.RuntimeException Ukoliko grad sa prosledjenim id-em ne postoji.
     */
    public Grad update(Long id, Grad grad) {
        Grad postojeci = findById(id);
        postojeci.setNaziv(grad.getNaziv());
        return gradRepository.save(postojeci);
    }

    /**
     * Brise grad sa prosledjenim identifikatorom iz baze podataka.
     *
     * @param id Identifikator grada koji treba obrisati.
     * @throws java.lang.RuntimeException Ukoliko grad sa prosledjenim id-em ne postoji.
     */
    public void delete(Long id) {
        Grad grad = findById(id);
        gradRepository.delete(grad);
    }

}