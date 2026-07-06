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

@Service
@RequiredArgsConstructor
public class KartaService {

    private final KartaRepository kartaRepository;

    public List<Karta> dohvatiSveZaKoncert(Long koncertId) {
        return kartaRepository.findAll().stream()
                .filter(k -> k.getKoncert().getId().equals(koncertId))
                .collect(Collectors.toList());
    }

    public Karta kupiKartu(Long koncertId, String imeKupca, String emailKupca) {
        Karta karta = kartaRepository.findFirstByKoncertIdAndStatus(koncertId, StatusKarte.DOSTUPNA)
                                        .orElseThrow(() -> new RuntimeException("Nema dostupnih karata."));

        karta.setStatus(StatusKarte.PRODATA);
        karta.setDatumProdaje(LocalDateTime.now());
        karta.setImeKupca(imeKupca);
        karta.setEmailKupca(emailKupca);

        return kartaRepository.save(karta);
    }

    public Karta stornirajKartu(Long kartaId) {
        Karta karta = kartaRepository.findById(kartaId)
                .orElseThrow(() -> new RuntimeException("Karta sa id-om " + kartaId + " nije pronađena."));

        karta.setStatus(StatusKarte.STORNIRANA);

        return kartaRepository.save(karta);
    }

    public Map<String, Object> getSummary(Long koncertId) {
        long dostupne = kartaRepository.countByKoncertIdAndStatus(koncertId, StatusKarte.DOSTUPNA);
        long ukupno = kartaRepository.findByKoncertId(koncertId).size();

        BigDecimal cena = kartaRepository.findFirstByKoncertIdAndStatus(koncertId, StatusKarte.DOSTUPNA)
                .map(Karta::getCena).orElse(BigDecimal.ZERO);

        Map<String, Object> summary = new HashMap<>();
        summary.put("dostupne", dostupne);
        summary.put("ukupno", ukupno);
        summary.put("cena", cena);
        return summary;
    }

}