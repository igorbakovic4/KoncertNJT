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
        String username = podaci.get("username");
        String password = podaci.get("password");

        Optional<Korisnik> korisnik = korisnikRepository.findByUsername(username);

        if (korisnik.isEmpty() || !korisnik.get().getPassword().equals(password)) {
            return ResponseEntity.status(401).body(Map.of("greska", "Pogrešno korisničko ime ili lozinka."));
        }

        Korisnik k = korisnik.get();
        return ResponseEntity.ok(Map.of(
                "token", "ok",
                "korisnik", Map.of(
                        "id", k.getId(),
                        "username", k.getUsername(),
                        "ime", k.getIme() != null ? k.getIme() : "",
                        "prezime", k.getPrezime() != null ? k.getPrezime() : ""
                )
        ));
    }
}