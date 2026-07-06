package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Klasa Korisnik predstavlja registrovanog korisnika aplikacije.
 *
 * Korisnik moze imati ulogu ADMIN ili KORISNIK. Admin ima pristup svim
 * operacijama, dok obican korisnik moze samo da pregleda sadrzaj i kupuje karte.
 * Lozinka se cuva kao BCrypt hash, nikad kao plain text.
 *
 * @author igor
 */
@Entity
@Table(name = "korisnik")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Korisnik {

    /**
     * Jedinstveni identifikator korisnika koji se automatski generise u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ime korisnika. Ne sme biti null.
     */
    @Column(nullable = false)
    private String ime;

    /**
     * Prezime korisnika. Ne sme biti null.
     */
    @Column(nullable = false)
    private String prezime;

    /**
     * Email adresa korisnika. Mora biti jedinstvena u bazi podataka. Ne sme biti null.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Lozinka korisnika sacuvana kao BCrypt hash. Ne sme biti null.
     */
    @Column(nullable = false)
    private String lozinka;

    /**
     * Uloga korisnika u sistemu. Moze biti ADMIN ili KORISNIK. Ne sme biti null.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Uloga uloga;

    /**
     * Enumeracija koja predstavlja moguce uloge korisnika u sistemu.
     */
    public enum Uloga {
        /** Administrator sa punim pristupom svim operacijama. */
        ADMIN,
        /** Obican korisnik koji moze da pregleda sadrzaj i kupuje karte. */
        KORISNIK
    }
}