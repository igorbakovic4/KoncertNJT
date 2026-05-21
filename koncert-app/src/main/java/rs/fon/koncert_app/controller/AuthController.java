package rs.fon.koncert_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import rs.fon.koncert_app.dto.AuthResponse;
import rs.fon.koncert_app.dto.LoginRequest;
import rs.fon.koncert_app.dto.RegistracijaRequest;
import rs.fon.koncert_app.entity.Korisnik;
import rs.fon.koncert_app.repository.KorisnikRepository;
import rs.fon.koncert_app.security.JwtService;
import rs.fon.koncert_app.service.KorisnikService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final KorisnikService korisnikService;
    private final KorisnikRepository korisnikRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Pogrešan email ili lozinka.");
        }

        Korisnik korisnik = korisnikRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String token = jwtService.generisiToken(korisnik);

        return ResponseEntity.ok(new AuthResponse(
                token,
                korisnik.getId(),
                korisnik.getIme(),
                korisnik.getPrezime(),
                korisnik.getEmail()
        ));
    }

    @PostMapping("/registracija")
    public ResponseEntity<?> registracija(@RequestBody RegistracijaRequest request) {
        try {
            Korisnik korisnik = korisnikService.registruj(
                    request.getIme(),
                    request.getPrezime(),
                    request.getEmail(),
                    request.getPassword()
            );

            String token = jwtService.generisiToken(korisnik);

            return ResponseEntity.ok(new AuthResponse(
                    token,
                    korisnik.getId(),
                    korisnik.getIme(),
                    korisnik.getPrezime(),
                    korisnik.getEmail()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}