package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Klasa Karta predstavlja ulaznicu za konkretan koncert.
 * Karta je jak objekat sa sopstvenim zivotnim ciklusom.
 * Jedinstvenost je osigurana kombinacijom koncerta, reda i sedista.
 *
 * @author igor
 * @see Koncert
 */
@Entity
@Table(
        name = "karta",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"koncert_id", "red", "sediste"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Karta {

    /**
     * Jedinstveni identifikator karte koji se automatski generise u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Koncert za koji je karta namenjena.
     * Nedozvoljene vrednosti: null.
     * Predstavlja vezu Many-to-One sa klasom Koncert.
     */
    @NotNull(message = "Koncert ne sme biti null.")
    @ManyToOne
    @JoinColumn(name = "koncert_id", nullable = false)
    private Koncert koncert;

    /**
     * Broj reda sedista u sali.
     * Nedozvoljene vrednosti: null, vrednosti manje od 1.
     */
    @NotNull(message = "Red ne sme biti null.")
    @Min(value = 1, message = "Broj reda mora biti veci od 0.")
    @Column(nullable = false)
    private Integer red;

    /**
     * Broj sedista u redu.
     * Nedozvoljene vrednosti: null, vrednosti manje od 1.
     */
    @NotNull(message = "Sediste ne sme biti null.")
    @Min(value = 1, message = "Broj sedista mora biti veci od 0.")
    @Column(nullable = false)
    private Integer sediste;

    /**
     * Cena karte u dinarima. Koristi BigDecimal radi preciznosti.
     * Nedozvoljene vrednosti: null, negativne vrednosti, vrednost nula.
     */
    @NotNull(message = "Cena ne sme biti null.")
    @DecimalMin(value = "0.01", message = "Cena mora biti veca od 0.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cena;

    /**
     * Trenutni status karte.
     * Nedozvoljene vrednosti: null.
     * Dozvoljene vrednosti: DOSTUPNA, REZERVISANA, PRODATA, STORNIRANA.
     */
    @NotNull(message = "Status karte ne sme biti null.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusKarte status;

    /**
     * Datum i vreme kada je karta prodata.
     * Vrednost je null ukoliko karta nije prodata.
     */
    @Column(name = "datum_prodaje")
    private LocalDateTime datumProdaje;

    /**
     * Ime i prezime kupca karte.
     * Nedozvoljene vrednosti: string duzi od 100 karaktera.
     * Vrednost je null ukoliko karta nije prodata.
     */
    @Size(max = 100, message = "Ime kupca ne sme biti duze od 100 karaktera.")
    @Column(name = "ime_kupca")
    private String imeKupca;

    /**
     * Email adresa kupca karte.
     * Nedozvoljene vrednosti: string koji nije u ispravnom formatu email adrese.
     * Vrednost je null ukoliko karta nije prodata.
     */
    @Email(message = "Email kupca mora biti u ispravnom formatu.")
    @Column(name = "email_kupca")
    private String emailKupca;

    /**
     * Enumeracija koja predstavlja moguce statuse karte.
     */
    public enum StatusKarte {
        /** Karta je dostupna za kupovinu. */
        DOSTUPNA,
        /** Karta je rezervisana. */
        REZERVISANA,
        /** Karta je prodata. */
        PRODATA,
        /** Karta je stornirana nakon prodaje. */
        STORNIRANA
    }

}