package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Korisnik;
import rs.fon.koncert_app.repository.KorisnikRepository;

@Service
@RequiredArgsConstructor
public class KorisnikService implements UserDetailsService {

    private final KorisnikRepository korisnikRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen: " + email));

        return User.builder()
                .username(korisnik.getEmail())
                .password(korisnik.getLozinka())
                .roles(korisnik.getUloga().name())
                .build();
    }

    public Korisnik registruj(String ime, String prezime, String email, String lozinka) {
        if (korisnikRepository.existsByEmail(email)) {
            throw new RuntimeException("Email je već zauzet.");
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(ime);
        korisnik.setPrezime(prezime);
        korisnik.setEmail(email);
        korisnik.setLozinka(passwordEncoder.encode(lozinka));
        korisnik.setUloga(Korisnik.Uloga.KORISNIK);

        return korisnikRepository.save(korisnik);
    }
}