package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa Korisnik predstavlja registrovanog korisnika aplikacije.
 * Korisnik moze imati ulogu ADMIN ili KORISNIK.
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
     * Ime korisnika.
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 50 karaktera.
     */
    @NotBlank(message = "Ime korisnika ne sme biti prazno.")
    @Size(max = 50, message = "Ime ne sme biti duze od 50 karaktera.")
    @Column(nullable = false)
    private String ime;

    /**
     * Prezime korisnika.
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 50 karaktera.
     */
    @NotBlank(message = "Prezime korisnika ne sme biti prazno.")
    @Size(max = 50, message = "Prezime ne sme biti duze od 50 karaktera.")
    @Column(nullable = false)
    private String prezime;

    /**
     * Email adresa korisnika. Mora biti jedinstvena u bazi podataka.
     * Nedozvoljene vrednosti: null, prazan string, string koji nije u ispravnom
     * formatu email adrese, string duzi od 100 karaktera.
     */
    @NotBlank(message = "Email ne sme biti prazan.")
    @Email(message = "Email mora biti u ispravnom formatu.")
    @Size(max = 100, message = "Email ne sme biti duzi od 100 karaktera.")
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Lozinka korisnika sacuvana kao BCrypt hash.
     * Nedozvoljene vrednosti: null, prazan string.
     */
    @NotBlank(message = "Lozinka ne sme biti prazna.")
    @Column(nullable = false)
    private String lozinka;

    /**
     * Uloga korisnika u sistemu.
     * Nedozvoljene vrednosti: null.
     * Dozvoljene vrednosti: ADMIN, KORISNIK.
     */
    @NotNull(message = "Uloga korisnika ne sme biti null.")
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