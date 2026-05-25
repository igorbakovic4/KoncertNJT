package rs.fon.koncert_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.koncert_app.entity.Karta;
import rs.fon.koncert_app.entity.Koncert;
import rs.fon.koncert_app.service.KoncertService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/koncerti")
@RequiredArgsConstructor
public class KoncertController {

    private final KoncertService koncertService;

    @GetMapping
    public ResponseEntity<List<Koncert>> findAll() {
        return ResponseEntity.ok(koncertService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Koncert> findById(@PathVariable Long id) {
        return ResponseEntity.ok(koncertService.findById(id));
    }

    @GetMapping("/datum")
    public ResponseEntity<List<Koncert>> findByDatum(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum) {
        return ResponseEntity.ok(koncertService.findByDatum(datum));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Koncert>> findByStatus(@RequestParam Koncert.StatusKoncerta status) {
        return ResponseEntity.ok(koncertService.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Koncert> save(@RequestBody Koncert koncert) {
        return ResponseEntity.status(HttpStatus.CREATED).body(koncertService.save(koncert));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Koncert> update(@PathVariable Long id, @RequestBody Koncert koncert) {
        return ResponseEntity.ok(koncertService.update(id, koncert));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        koncertService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/izvodjaci")
    public ResponseEntity<Koncert> updateIzvodjaci(
            @PathVariable Long id,
            @RequestBody List<Long> izvodjacIds) {
        return ResponseEntity.ok(koncertService.updateIzvodjaci(id, izvodjacIds));
    }

    @PostMapping("/{id}/generisi-karte")
    public ResponseEntity<?> generisiKarte(@PathVariable Long id, @RequestParam BigDecimal cena) {
        try {
            List<Karta> karte = koncertService.generisiKarte(id, cena);
            return ResponseEntity.ok(Map.of(
                    "poruka", "Uspešno generisano " + karte.size() + " karata.",
                    "broj", karte.size()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
