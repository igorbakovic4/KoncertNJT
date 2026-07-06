package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Klasa Koncert predstavlja muzicki dogadjaj koji se odrzava na odredjenoj lokaciji.
 *
 * Svaki koncert ima naziv, datum, vreme pocetka, trajanje, status i lokaciju.
 * Jedinstvenost koncerta je osigurana kombinacijom lokacije, datuma i vremena pocetka,
 * sto znaci da na istoj lokaciji u isto vreme ne mogu biti dva razlicita koncerta.
 *
 * @author igor
 * @see Lokacija
 * @see Izvodjac
 */
@Entity
@Table(
        name = "koncert",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"lokacija_id", "datum", "vreme_pocetka"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Koncert {

    /**
     * Jedinstveni identifikator koncerta koji se automatski generise u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Naziv koncerta. Ne sme biti null.
     */
    @Column(nullable = false)
    private String naziv;

    /**
     * Datum odrzavanja koncerta. Ne sme biti null.
     */
    @Column(nullable = false)
    private LocalDate datum;

    /**
     * Vreme pocetka koncerta. Ne sme biti null.
     */
    @Column(name = "vreme_pocetka", nullable = false)
    private LocalTime vremePocetka;

    /**
     * Trajanje koncerta u minutima.
     */
    @Column(name = "vreme_trajanja")
    private Integer vremeTrajanja;

    /**
     * Status koncerta. Moze biti PLANIRAN, AKTIVAN, OTKAZAN ili ZAVRSEN.
     * Ne sme biti null.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusKoncerta status;

    /**
     * Lokacija na kojoj se odrzava koncert.
     * Predstavlja vezu Many-to-One sa klasom Lokacija.
     */
    @ManyToOne
    @JoinColumn(name = "lokacija_id", nullable = false)
    private Lokacija lokacija;

    /**
     * Lista izvodjaca koji nastupaju na koncertu.
     * Predstavlja vezu Many-to-Many sa klasom Izvodjac kroz spojnu tabelu koncert_izvodjac.
     */
    @ManyToMany
    @JoinTable(
            name = "koncert_izvodjac",
            joinColumns = @JoinColumn(name = "koncert_id"),
            inverseJoinColumns = @JoinColumn(name = "izvodjac_id")
    )
    private List<Izvodjac> izvodjaci;

    /**
     * Enumeracija koja predstavlja moguce statuse koncerta.
     */
    public enum StatusKoncerta {
        /** Koncert je planiran ali jos nije potvrdjen. */
        PLANIRAN,
        /** Koncert je aktivan i karte su dostupne. */
        AKTIVAN,
        /** Koncert je otkazan. */
        OTKAZAN,
        /** Koncert je odrzan i zavrsen. */
        ZAVRSEN
    }

}