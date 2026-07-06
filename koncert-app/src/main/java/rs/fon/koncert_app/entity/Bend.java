package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * Klasa Bend predstavlja muzicku grupu koja nastupa na koncertu.
 *
 * Nasledjuje klasu Izvodjac i dodaje specificna polja kao sto su
 * naziv benda i broj clanova.
 *
 * @author igor
 * @see Izvodjac
 */
@Entity
@Table(name = "bend")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Bend extends Izvodjac {

    /**
     * Naziv benda.
     */
    private String naziv;

    /**
     * Broj clanova benda.
     */
    @Column(name = "broj_clanova")
    private Integer brojClanova;

}