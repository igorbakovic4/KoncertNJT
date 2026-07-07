package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa Zanr predstavlja muzicki zanr koji moze biti dodeljen izvodjacu.
 * Svaki zanr ima jedinstveni identifikator i jedinstven naziv.
 *
 * @author igor
 */
@Entity
@Table(name = "zanr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zanr {

    /**
     * Jedinstveni identifikator zanra koji se automatski generise u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Naziv zanra.
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 50 karaktera.
     */
    @NotBlank(message = "Naziv zanra ne sme biti prazan.")
    @Size(max = 50, message = "Naziv zanra ne sme biti duzi od 50 karaktera.")
    @Column(nullable = false, unique = true)
    private String naziv;

}