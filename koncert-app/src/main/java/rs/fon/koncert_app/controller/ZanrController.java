package rs.fon.koncert_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.koncert_app.entity.Zanr;
import rs.fon.koncert_app.service.ZanrService;

import java.util.List;

@RestController
@RequestMapping("/api/zanrovi")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ZanrController {

    private final ZanrService zanrService;

    @GetMapping
    public List<Zanr> dohvatiSve() {
        return zanrService.dohvatiSve();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Zanr> dohvatiPoId(@PathVariable Long id) {
        return ResponseEntity.ok(zanrService.dohvatiPoId(id));
    }

    @PostMapping
    public ResponseEntity<Zanr> sacuvajZanr(@RequestBody Zanr zanr) {
        return ResponseEntity.ok(zanrService.sacuvajZanr(zanr));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiZanr(@PathVariable Long id) {
        zanrService.obrisiZanr(id);
        return ResponseEntity.noContent().build();
    }
}