package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Klasa Muzicar predstavlja pojedinacnog muzicara koji nastupa na koncertu.
 *
 * Nasledjuje klasu Izvodjac i dodaje specificna polja kao sto su
 * ime, prezime i pol muzicara.
 *
 * @author igor
 * @see Izvodjac
 */
@Entity
@Table(name = "muzicar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Muzicar extends Izvodjac {

    /**
     * Ime muzicara.
     */
    private String ime;

    /**
     * Prezime muzicara.
     */
    private String prezime;

    /**
     * Pol muzicara. Vrednosti: "M" za muski, "Z" za zenski.
     */
    private String pol;

}