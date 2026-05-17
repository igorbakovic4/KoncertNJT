package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Grad;
import rs.fon.koncert_app.repository.GradRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradService {

    private final GradRepository gradRepository;

    public List<Grad> findAll() {
        return gradRepository.findAll();
    }

    public Grad findById(Long id) {
        return gradRepository.findById(id).orElseThrow(() -> new RuntimeException("Grad sa id-em " + id + " ne postoji."));
    }

    public Grad save(Grad grad) {
        return gradRepository.save(grad);
    }

    public Grad update(Long id, Grad grad) {
        Grad postojeci = findById(id);
        postojeci.setNaziv(grad.getNaziv());
        return gradRepository.save(postojeci);
    }

    public void delete(Long id) {
        Grad grad = findById(id);
        gradRepository.delete(grad);
    }

}
