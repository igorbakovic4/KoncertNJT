package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Klasa Koncert predstavlja muzicki dogadjaj koji se odrzava na odredjenoj lokaciji.
 * Jedinstvenost je osigurana kombinacijom lokacije, datuma i vremena pocetka.
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
     * Naziv koncerta.
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 150 karaktera.
     */
    @NotBlank(message = "Naziv koncerta ne sme biti prazan.")
    @Size(max = 150, message = "Naziv koncerta ne sme biti duzi od 150 karaktera.")
    @Column(nullable = false)
    private String naziv;

    /**
     * Datum odrzavanja koncerta.
     * Nedozvoljene vrednosti: null.
     */
    @NotNull(message = "Datum koncerta ne sme biti null.")
    @Column(nullable = false)
    private LocalDate datum;

    /**
     * Vreme pocetka koncerta.
     * Nedozvoljene vrednosti: null.
     */
    @NotNull(message = "Vreme pocetka ne sme biti null.")
    @Column(name = "vreme_pocetka", nullable = false)
    private LocalTime vremePocetka;

    /**
     * Trajanje koncerta u minutima.
     * Nedozvoljene vrednosti: vrednosti manje od 1.
     */
    @Min(value = 1, message = "Trajanje koncerta mora biti vece od 0 minuta.")
    @Column(name = "vreme_trajanja")
    private Integer vremeTrajanja;

    /**
     * Status koncerta.
     * Nedozvoljene vrednosti: null.
     * Dozvoljene vrednosti: PLANIRAN, AKTIVAN, OTKAZAN, ZAVRSEN.
     */
    @NotNull(message = "Status koncerta ne sme biti null.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusKoncerta status;

    /**
     * Lokacija na kojoj se odrzava koncert.
     * Nedozvoljene vrednosti: null.
     * Predstavlja vezu Many-to-One sa klasom Lokacija.
     */
    @NotNull(message = "Lokacija koncerta ne sme biti null.")
    @ManyToOne
    @JoinColumn(name = "lokacija_id", nullable = false)
    private Lokacija lokacija;

    /**
     * Lista izvodjaca koji nastupaju na koncertu.
     * Predstavlja vezu Many-to-Many sa klasom Izvodjac.
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