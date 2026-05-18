package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Zanr;
import rs.fon.koncert_app.repository.ZanrRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZanrService {

    private final ZanrRepository zanrRepository;

    public List<Zanr> dohvatiSve() {
        return zanrRepository.findAll();
    }

    public Zanr dohvatiPoId(Long id) {
        return zanrRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Žanr sa id-om " + id + " nije pronađen."));
    }

    public Zanr sacuvajZanr(Zanr zanr) {
        return zanrRepository.save(zanr);
    }

    public void obrisiZanr(Long id) {
        if (!zanrRepository.existsById(id)) {
            throw new RuntimeException("Žanr sa id-om " + id + " nije pronađen.");
        }
        zanrRepository.deleteById(id);
    }
}