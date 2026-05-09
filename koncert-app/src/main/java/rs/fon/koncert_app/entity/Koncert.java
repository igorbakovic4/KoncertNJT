package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private LocalDate datum;

    @Column(name = "vreme_pocetka", nullable = false)
    private LocalTime vremePocetka;

    @Column(name = "vreme_trajanja")
    private Integer vremeTrajanja;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusKoncerta status;

    @ManyToOne
    @JoinColumn(name = "lokacija_id", nullable = false)
    private Lokacija lokacija;

    @ManyToMany
    @JoinTable(
            name = "koncert_izvodjac",
            joinColumns = @JoinColumn(name = "koncert_id"),
            inverseJoinColumns = @JoinColumn(name = "izvodjac_id")
    )
    private List<Izvodjac> izvodjaci;

    public enum StatusKoncerta {
        PLANIRAN,
        AKTIVAN,
        OTKAZAN,
        ZAVRSEN
    }

}
