package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Karta;
import rs.fon.koncert_app.entity.Karta.StatusKarte;
import rs.fon.koncert_app.repository.KartaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servis koji implementira sistemske operacije za upravljanje kartama.
 *
 * Pruza operacije za dohvatanje karata, kupovinu, storniranje i
 * dohvatanje sumarnih informacija o dostupnosti karata za koncert.
 *
 * @author igor
 * @see Karta
 */
@Service
@RequiredArgsConstructor
public class KartaService {

    private final KartaRepository kartaRepository;

    /**
     * Vraca listu svih karata za koncert sa prosledjenim identifikatorom.
     *
     * @param koncertId Identifikator koncerta cije karte se traze.
     * @return lista svih karata za prosledjeni koncert, ili prazna lista.
     */
    public List<Karta> dohvatiSveZaKoncert(Long koncertId) {
        return kartaRepository.findAll().stream()
                .filter(k -> k.getKoncert().getId().equals(koncertId))
                .collect(Collectors.toList());
    }

    /**
     * Kupuje prvu dostupnu kartu za koncert sa prosledjenim identifikatorom.
     * Postavlja status karte na PRODATA i upisuje podatke kupca i datum prodaje.
     *
     * @param koncertId Identifikator koncerta za koji se kupuje karta.
     * @param imeKupca Ime i prezime kupca karte.
     * @param emailKupca Email adresa kupca karte.
     * @return kupljena karta sa azuriranim podacima.
     * @throws java.lang.RuntimeException Ukoliko nema dostupnih karata za prosledjeni koncert.
     */
    public Karta kupiKartu(Long koncertId, String imeKupca, String emailKupca) {
        Karta karta = kartaRepository
                .findFirstByKoncertIdAndStatus(koncertId, StatusKarte.DOSTUPNA)
                .orElseThrow(() -> new RuntimeException("Nema dostupnih karata."));

        karta.setStatus(StatusKarte.PRODATA);
        karta.setDatumProdaje(LocalDateTime.now());
        karta.setImeKupca(imeKupca);
        karta.setEmailKupca(emailKupca);

        return kartaRepository.save(karta);
    }

    /**
     * Stornira kartu sa prosledjenim identifikatorom.
     * Postavlja status karte na STORNIRANA.
     *
     * @param kartaId Identifikator karte koja se stornira.
     * @return stornirana karta sa azuriranim statusom.
     * @throws java.lang.RuntimeException Ukoliko karta sa prosledjenim id-om ne postoji.
     */
    public Karta stornirajKartu(Long kartaId) {
        Karta karta = kartaRepository.findById(kartaId)
                .orElseThrow(() -> new RuntimeException("Karta sa id-om " + kartaId + " nije pronadjena."));

        karta.setStatus(StatusKarte.STORNIRANA);

        return kartaRepository.save(karta);
    }

    /**
     * Vraca sumarne informacije o dostupnosti karata za koncert sa prosledjenim identifikatorom.
     * Sumarne informacije ukljucuju broj dostupnih karata, ukupan broj karata i cenu.
     *
     * @param koncertId Identifikator koncerta cije sumarne informacije se traze.
     * @return mapa sa kljucevima "dostupne", "ukupno" i "cena".
     */
    public Map<String, Object> getSummary(Long koncertId) {
        long dostupne = kartaRepository.countByKoncertIdAndStatus(koncertId, StatusKarte.DOSTUPNA);
        long ukupno = kartaRepository.findByKoncertId(koncertId).size();

        BigDecimal cena = kartaRepository
                .findFirstByKoncertIdAndStatus(koncertId, StatusKarte.DOSTUPNA)
                .map(Karta::getCena)
                .orElse(BigDecimal.ZERO);

        Map<String, Object> summary = new HashMap<>();
        summary.put("dostupne", dostupne);
        summary.put("ukupno", ukupno);
        summary.put("cena", cena);
        return summary;
    }

}