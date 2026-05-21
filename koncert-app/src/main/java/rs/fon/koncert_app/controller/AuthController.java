package rs.fon.koncert_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.koncert_app.entity.Korisnik;
import rs.fon.koncert_app.repository.KorisnikRepository;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KorisnikRepository korisnikRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> podaci) {
        String email = podaci.get("email");
        String password = podaci.get("password");

        Optional<Korisnik> korisnik = korisnikRepository.findByEmail(email);

        if (korisnik.isEmpty() || !korisnik.get().getPassword().equals(password)) {
            return ResponseEntity.status(401).body(Map.of("greska", "Pogrešan email ili lozinka."));
        }

        Korisnik k = korisnik.get();
        return ResponseEntity.ok(Map.of(
                "token", "ok",
                "korisnik", Map.of(
                        "id", k.getId(),
                        "ime", k.getIme(),
                        "prezime", k.getPrezime(),
                        "email", k.getEmail()
                )
        ));
    }

    @PostMapping("/registracija")
    public ResponseEntity<?> registracija(@RequestBody Korisnik korisnik) {
        if (korisnikRepository.findByEmail(korisnik.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("greska", "Email je već zauzet."));
        }
        Korisnik sacuvan = korisnikRepository.save(korisnik);
        return ResponseEntity.ok(Map.of(
                "poruka", "Registracija uspješna.",
                "korisnik", Map.of(
                        "id", sacuvan.getId(),
                        "ime", sacuvan.getIme(),
                        "prezime", sacuvan.getPrezime(),
                        "email", sacuvan.getEmail()
                )
        ));
    }
}