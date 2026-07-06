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

/**
 * Servis koji implementira sistemske operacije za upravljanje korisnicima.
 *
 * Implementira Spring Security interfejs UserDetailsService koji se koristi
 * za autentifikaciju korisnika. Pruza operacije za registraciju korisnika
 * i ucitavanje korisnika po email adresi.
 * Lozinka se uvek cuva kao BCrypt hash, nikad kao plain text.
 *
 * @author igor
 * @see Korisnik
 */
@Service
@RequiredArgsConstructor
public class KorisnikService implements UserDetailsService {

    private final KorisnikRepository korisnikRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Ucitava korisnika po email adresi za potrebe Spring Security autentifikacije.
     *
     * @param email Email adresa korisnika koji se trazi.
     * @return UserDetails objekat sa podacima o korisniku.
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     *         Ukoliko korisnik sa prosledjenom email adresom ne postoji.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronadjen: " + email));

        return User.builder()
                .username(korisnik.getEmail())
                .password(korisnik.getLozinka())
                .roles(korisnik.getUloga().name())
                .build();
    }

    /**
     * Registruje novog korisnika u sistemu.
     * Lozinka se hashuje BCrypt algoritmom pre cuvanja u bazi podataka.
     * Svaki novoregistrovani korisnik dobija ulogu KORISNIK.
     *
     * @param ime Ime korisnika.
     * @param prezime Prezime korisnika.
     * @param email Email adresa korisnika. Mora biti jedinstvena.
     * @param lozinka Lozinka korisnika u plain text formatu koja ce biti hashirana.
     * @return registrovani korisnik sa dodeljenim identifikatorom.
     * @throws java.lang.RuntimeException Ukoliko korisnik sa prosledjenom email adresom vec postoji.
     */
    public Korisnik registruj(String ime, String prezime, String email, String lozinka) {
        if (korisnikRepository.existsByEmail(email)) {
            throw new RuntimeException("Email je vec zauzet.");
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