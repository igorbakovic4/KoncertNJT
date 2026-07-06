package rs.fon.koncert_app.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Apstraktna klasa Izvodjac predstavlja osobu ili grupu koja nastupa na koncertu.
 *
 * Izvodjac moze biti Muzicar ili Bend. Koristi se JOINED strategija nasledjivanja,
 * sto znaci da svaki podtip ima svoju tabelu u bazi podataka sa zajednickim ID-em.
 *
 * @author igor
 * @see Muzicar
 * @see Bend
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tip")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Muzicar.class, name = "MUZICAR"),
        @JsonSubTypes.Type(value = Bend.class, name = "BEND")
})
@Entity
@Table(name = "izvodjac")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Izvodjac {

    /**
     * Jedinstveni identifikator izvodjaca koji se automatski generise u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Kontakt email izvodjaca.
     */
    @Column(name = "kontakt_email")
    private String kontaktEmail;

    /**
     * Lista zanrova kojima izvodjac pripada.
     * Predstavlja vezu Many-to-Many sa klasom Zanr kroz spojnu tabelu izvodjac_zanr.
     */
    @ManyToMany
    @JoinTable(
            name = "izvodjac_zanr",
            joinColumns = @JoinColumn(name = "izvodjac_id"),
            inverseJoinColumns = @JoinColumn(name = "zanr_id")
    )
    private List<Zanr> zanrovi;
}