package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Klasa Karta predstavlja ulaznicu za konkretan koncert.
 *
 * Karta je jak objekat sa sopstvenim zivotnim ciklusom — moze biti
 * dostupna, rezervisana, prodata ili stornirana. Jedinstvenost karte
 * je osigurana kombinacijom koncerta, reda i sedista.
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
     * Predstavlja vezu Many-to-One sa klasom Koncert.
     */
    @ManyToOne
    @JoinColumn(name = "koncert_id", nullable = false)
    private Koncert koncert;

    /**
     * Broj reda sedista u sali. Ne sme biti null.
     */
    @Column(nullable = false)
    private Integer red;

    /**
     * Broj sedista u redu. Ne sme biti null.
     */
    @Column(nullable = false)
    private Integer sediste;

    /**
     * Cena karte u dinarima. Koristi BigDecimal radi preciznosti pri racunanju.
     * Ne sme biti null.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cena;

    /**
     * Trenutni status karte. Moze biti DOSTUPNA, REZERVISANA, PRODATA ili STORNIRANA.
     * Ne sme biti null.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusKarte status;

    /**
     * Datum i vreme kada je karta prodata. Null ako karta nije prodata.
     */
    @Column(name = "datum_prodaje")
    private LocalDateTime datumProdaje;

    /**
     * Ime i prezime kupca karte. Null ako karta nije prodata.
     */
    @Column(name = "ime_kupca")
    private String imeKupca;

    /**
     * Email adresa kupca karte. Null ako karta nije prodata.
     */
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