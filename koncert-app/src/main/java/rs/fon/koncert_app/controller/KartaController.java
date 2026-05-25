package rs.fon.koncert_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.fon.koncert_app.entity.Karta;
import rs.fon.koncert_app.service.KartaService;

import java.util.List;

@RestController
@RequestMapping("/api/karte")
@RequiredArgsConstructor
public class KartaController {

    private final KartaService kartaService;

    @GetMapping("/koncert/{koncertId}")
    public ResponseEntity<List<Karta>> dohvatiSveZaKoncert(@PathVariable Long koncertId) {
        return ResponseEntity.ok(kartaService.dohvatiSveZaKoncert(koncertId));
    }

    @PostMapping("/{idKoncert}/kupi")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Karta> kupiKartu(
            @PathVariable Long id,
            @RequestParam String imeKupca,
            @RequestParam String emailKupca) {
        return ResponseEntity.ok(kartaService.kupiKartu(id, imeKupca, emailKupca));
    }

    @PostMapping("/{id}/storniraj")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Karta> stornirajKartu(@PathVariable Long id) {
        return ResponseEntity.ok(kartaService.stornirajKartu(id));
    }
}