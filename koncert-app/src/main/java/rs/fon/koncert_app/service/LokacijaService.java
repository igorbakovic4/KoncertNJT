package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Lokacija;
import rs.fon.koncert_app.repository.LokacijaRepository;

import java.util.List;

/**
 * Servis koji implementira sistemske operacije za upravljanje lokacijama.
 *
 * Pruza CRUD operacije nad domenskom klasom Lokacija.
 * Pre cuvanja lokacije, proverava da li prosledjeni grad postoji u bazi.
 *
 * @author igor
 * @see Lokacija
 */
@Service
@RequiredArgsConstructor
public class LokacijaService {

    private final LokacijaRepository lokacijaRepository;
    private final GradService gradService;

    /**
     * Vraca listu svih lokacija iz baze podataka.
     *
     * @return lista svih lokacija, ili prazna lista ako nema lokacija.
     */
    public List<Lokacija> findAll() {
        return lokacijaRepository.findAll();
    }

    /**
     * Vraca lokaciju sa prosledjenim identifikatorom.
     *
     * @param id Identifikator lokacije koja se trazi.
     * @return lokacija sa prosledjenim identifikatorom.
     * @throws java.lang.RuntimeException Ukoliko lokacija sa prosledjenim id-em ne postoji.
     */
    public Lokacija findById(Long id) {
        return lokacijaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lokacija sa id-em " + id + " ne postoji."));
    }

    /**
     * Cuva prosledjenu lokaciju u bazi podataka.
     * Pre cuvanja proverava da li grad koji je dodeljen lokaciji postoji.
     *
     * @param lokacija Lokacija koju treba sacuvati.
     * @return sacuvana lokacija sa dodeljenim identifikatorom.
     * @throws java.lang.RuntimeException Ukoliko grad dodeljen lokaciji ne postoji.
     */
    public Lokacija save(Lokacija lokacija) {
        gradService.findById(lokacija.getGrad().getId());
        return lokacijaRepository.save(lokacija);
    }

    /**
     * Azurira podatke o lokaciji sa prosledjenim identifikatorom.
     *
     * @param id Identifikator lokacije koja treba da se azurira.
     * @param lokacija Lokacija sa novim podacima.
     * @return azurirana lokacija.
     * @throws java.lang.RuntimeException Ukoliko lokacija sa prosledjenim id-em ne postoji.
     */
    public Lokacija update(Long id, Lokacija lokacija) {
        Lokacija postojeca = findById(id);
        postojeca.setNaziv(lokacija.getNaziv());
        postojeca.setAdresa(lokacija.getAdresa());
        postojeca.setKapacitet(lokacija.getKapacitet());
        postojeca.setGrad(lokacija.getGrad());
        return lokacijaRepository.save(postojeca);
    }

    /**
     * Brise lokaciju sa prosledjenim identifikatorom iz baze podataka.
     *
     * @param id Identifikator lokacije koju treba obrisati.
     * @throws java.lang.RuntimeException Ukoliko lokacija sa prosledjenim id-em ne postoji.
     */
    public void delete(Long id) {
        Lokacija lokacija = findById(id);
        lokacijaRepository.delete(lokacija);
    }

}