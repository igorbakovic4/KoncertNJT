package rs.fon.koncert_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.koncert_app.entity.Lokacija;
import rs.fon.koncert_app.service.LokacijaService;

import java.util.List;

@RestController
@RequestMapping("/api/lokacije")
@RequiredArgsConstructor
public class LokacijaController {

    private final LokacijaService lokacijaService;

    @GetMapping
    public ResponseEntity<List<Lokacija>> findAll() {
        return ResponseEntity.ok(lokacijaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lokacija> findById(@PathVariable Long id) {
        return ResponseEntity.ok(lokacijaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Lokacija> save(@RequestBody  Lokacija lokacija) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lokacijaService.save(lokacija));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lokacija> update(@PathVariable Long id, @RequestBody Lokacija lokacija) {
        return ResponseEntity.ok(lokacijaService.update(id, lokacija));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lokacijaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
