package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Karta;
import rs.fon.koncert_app.entity.Karta.StatusKarte;
import rs.fon.koncert_app.repository.KartaRepository;

import java.time.LocalDateTime;
import java.util.List;
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

    public Karta kupiKartu(Long kartaId, String imeKupca, String emailKupca) {
        Karta karta = kartaRepository.findById(kartaId)
                .orElseThrow(() -> new RuntimeException("Karta sa id-om " + kartaId + " nije pronađena."));

        if (karta.getStatus() != StatusKarte.DOSTUPNA) {
            throw new RuntimeException("Karta nije dostupna za kupovinu. Trenutni status: " + karta.getStatus());
        }

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
}