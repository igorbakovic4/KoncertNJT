package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Bend;
import rs.fon.koncert_app.entity.Izvodjac;
import rs.fon.koncert_app.entity.Muzicar;
import rs.fon.koncert_app.repository.IzvodjacRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IzvodjacService {

    private final IzvodjacRepository izvodjacRepository;

    public List<Izvodjac> dohvatiSve() {
        return izvodjacRepository.findAll();
    }

    public Izvodjac dohvatiPoId(Long id) {
        return izvodjacRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Izvodjac sa id-om " + id + " nije pronađen."));
    }

    public Muzicar sacuvajMuzicara(Muzicar muzicar) {
        return (Muzicar) izvodjacRepository.save(muzicar);
    }

    public Bend sacuvajBend(Bend bend) {
        return (Bend) izvodjacRepository.save(bend);
    }

    public void obrisiIzvodjaca(Long id) {
        if (!izvodjacRepository.existsById(id)) {
            throw new RuntimeException("Izvodjac sa id-om " + id + " nije pronađen.");
        }
        izvodjacRepository.deleteById(id);
    }
}