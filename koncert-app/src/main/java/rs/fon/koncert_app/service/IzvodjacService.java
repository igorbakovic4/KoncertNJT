package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Bend;
import rs.fon.koncert_app.entity.Izvodjac;
import rs.fon.koncert_app.entity.Muzicar;
import rs.fon.koncert_app.repository.IzvodjacRepository;

import java.util.List;

/**
 * Servis koji implementira sistemske operacije za upravljanje izvodjacima.
 *
 * Pruza operacije za dohvatanje, cuvanje i brisanje izvodjaca.
 * Podrzava rad sa oba tipa izvodjaca — Muzicar i Bend.
 *
 * @author igor
 * @see Izvodjac
 * @see Muzicar
 * @see Bend
 */
@Service
@RequiredArgsConstructor
public class IzvodjacService {

    private final IzvodjacRepository izvodjacRepository;

    /**
     * Vraca listu svih izvodjaca iz baze podataka.
     *
     * @return lista svih izvodjaca, ili prazna lista ako nema izvodjaca.
     */
    public List<Izvodjac> dohvatiSve() {
        return izvodjacRepository.findAll();
    }

    /**
     * Vraca izvodjaca sa prosledjenim identifikatorom.
     *
     * @param id Identifikator izvodjaca koji se trazi.
     * @return izvodjac sa prosledjenim identifikatorom.
     * @throws java.lang.RuntimeException Ukoliko izvodjac sa prosledjenim id-om ne postoji.
     */
    public Izvodjac dohvatiPoId(Long id) {
        return izvodjacRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Izvodjac sa id-om " + id + " nije pronadjen."));
    }

    /**
     * Cuva prosledjenog muzicara u bazi podataka.
     *
     * @param muzicar Muzicar koji treba sacuvati.
     * @return sacuvani muzicar sa dodeljenim identifikatorom.
     */
    public Muzicar sacuvajMuzicara(Muzicar muzicar) {
        return (Muzicar) izvodjacRepository.save(muzicar);
    }

    /**
     * Cuva prosledjeni bend u bazi podataka.
     *
     * @param bend Bend koji treba sacuvati.
     * @return sacuvani bend sa dodeljenim identifikatorom.
     */
    public Bend sacuvajBend(Bend bend) {
        return (Bend) izvodjacRepository.save(bend);
    }

    /**
     * Brise izvodjaca sa prosledjenim identifikatorom iz baze podataka.
     *
     * @param id Identifikator izvodjaca koji treba obrisati.
     * @throws java.lang.RuntimeException Ukoliko izvodjac sa prosledjenim id-om ne postoji.
     */
    public void obrisiIzvodjaca(Long id) {
        if (!izvodjacRepository.existsById(id)) {
            throw new RuntimeException("Izvodjac sa id-om " + id + " nije pronadjen.");
        }
        izvodjacRepository.deleteById(id);
    }
}