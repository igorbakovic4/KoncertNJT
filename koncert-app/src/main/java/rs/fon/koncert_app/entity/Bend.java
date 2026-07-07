package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Klasa Bend predstavlja muzicku grupu koja nastupa na koncertu.
 * Nasledjuje klasu Izvodjac i dodaje specificna polja.
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
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 100 karaktera.
     */
    @NotBlank(message = "Naziv benda ne sme biti prazan.")
    @Size(max = 100, message = "Naziv benda ne sme biti duzi od 100 karaktera.")
    private String naziv;

    /**
     * Broj clanova benda.
     * Nedozvoljene vrednosti: null, vrednosti manje od 1.
     */
    @NotNull(message = "Broj clanova ne sme biti null.")
    @Min(value = 1, message = "Bend mora imati najmanje jednog clana.")
    @Column(name = "broj_clanova")
    private Integer brojClanova;

}