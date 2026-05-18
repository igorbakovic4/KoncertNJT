package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "bend")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Bend extends Izvodjac {

    private String naziv;

    @Column(name = "broj_clanova")
    private Integer brojClanova;

}
