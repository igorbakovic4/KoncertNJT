package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Klasa Muzicar predstavlja pojedinacnog muzicara koji nastupa na koncertu.
 * Nasledjuje klasu Izvodjac i dodaje specificna polja.
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
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 50 karaktera.
     */
    @NotBlank(message = "Ime muzicara ne sme biti prazno.")
    @Size(max = 50, message = "Ime ne sme biti duze od 50 karaktera.")
    private String ime;

    /**
     * Prezime muzicara.
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 50 karaktera.
     */
    @NotBlank(message = "Prezime muzicara ne sme biti prazno.")
    @Size(max = 50, message = "Prezime ne sme biti duze od 50 karaktera.")
    private String prezime;

    /**
     * Pol muzicara.
     * Dozvoljene vrednosti: "M" za muski pol, "Z" za zenski pol.
     * Nedozvoljene vrednosti: null, bilo koji string koji nije "M" ili "Z".
     */
    @Pattern(regexp = "^[MZ]$", message = "Pol mora biti 'M' ili 'Z'.")
    private String pol;

}