package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Lokacija;
import rs.fon.koncert_app.repository.LokacijaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LokacijaService {

    private final LokacijaRepository lokacijaRepository;
    private final GradService gradService;

    public List<Lokacija> findAll() {
        return lokacijaRepository.findAll();
    }

    public Lokacija findById(Long id) {
        return lokacijaRepository.findById(id).orElseThrow(() -> new RuntimeException("Lokacija sa id-em " + id + " ne postoji."));
    }

    public Lokacija save(Lokacija lokacija) {
        gradService.findById(lokacija.getGrad().getId());
        return lokacijaRepository.save(lokacija);
    }

    public Lokacija update(Long id, Lokacija lokacija) {
        Lokacija postojeca = findById(id);
        postojeca.setNaziv(lokacija.getNaziv());
        postojeca.setAdresa(lokacija.getAdresa());
        postojeca.setKapacitet(lokacija.getKapacitet());
        postojeca.setGrad(lokacija.getGrad());
        return lokacijaRepository.save(postojeca);
    }

    public void delete(Long id) {
        Lokacija lokacija = findById(id);
        lokacijaRepository.delete(lokacija);
    }

}
