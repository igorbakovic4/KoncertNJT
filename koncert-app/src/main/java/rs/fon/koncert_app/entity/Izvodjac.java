package rs.fon.koncert_app.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kontakt_email")
    private String kontaktEmail;

    @ManyToMany
    @JoinTable(
            name = "izvodjac_zanr",
            joinColumns = @JoinColumn(name = "izvodjac_id"),
            inverseJoinColumns = @JoinColumn(name = "zanr_id")
    )
    private List<Zanr> zanrovi;
}