package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Zanr;
import rs.fon.koncert_app.repository.ZanrRepository;

import java.util.List;

/**
 * Servis koji implementira sistemske operacije za upravljanje zanrovima.
 *
 * Pruza operacije za dohvatanje, cuvanje i brisanje zanrova.
 *
 * @author igor
 * @see Zanr
 */
@Service
@RequiredArgsConstructor
public class ZanrService {

    private final ZanrRepository zanrRepository;

    /**
     * Vraca listu svih zanrova iz baze podataka.
     *
     * @return lista svih zanrova, ili prazna lista ako nema zanrova.
     */
    public List<Zanr> dohvatiSve() {
        return zanrRepository.findAll();
    }

    /**
     * Vraca zanr sa prosledjenim identifikatorom.
     *
     * @param id Identifikator zanra koji se trazi.
     * @return zanr sa prosledjenim identifikatorom.
     * @throws java.lang.RuntimeException Ukoliko zanr sa prosledjenim id-om ne postoji.
     */
    public Zanr dohvatiPoId(Long id) {
        return zanrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zanr sa id-om " + id + " nije pronadjen."));
    }

    /**
     * Cuva prosledjeni zanr u bazi podataka.
     *
     * @param zanr Zanr koji treba sacuvati.
     * @return sacuvani zanr sa dodeljenim identifikatorom.
     */
    public Zanr sacuvajZanr(Zanr zanr) {
        return zanrRepository.save(zanr);
    }

    /**
     * Brise zanr sa prosledjenim identifikatorom iz baze podataka.
     *
     * @param id Identifikator zanra koji treba obrisati.
     * @throws java.lang.RuntimeException Ukoliko zanr sa prosledjenim id-om ne postoji.
     */
    public void obrisiZanr(Long id) {
        if (!zanrRepository.existsById(id)) {
            throw new RuntimeException("Zanr sa id-om " + id + " nije pronadjen.");
        }
        zanrRepository.deleteById(id);
    }
}