package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa Grad predstavlja grad u kome se nalazi lokacija koncerta.
 * Svaki grad ima jedinstveni identifikator i naziv.
 *
 * @author igor
 */
@Entity
@Table(name = "grad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grad {

    /**
     * Jedinstveni identifikator grada koji se automatski generise u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Naziv grada.
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 100 karaktera.
     */
    @NotBlank(message = "Naziv grada ne sme biti prazan.")
    @Size(max = 100, message = "Naziv grada ne sme biti duzi od 100 karaktera.")
    @Column(nullable = false)
    private String naziv;

}