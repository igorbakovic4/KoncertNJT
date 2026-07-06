package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa Zanr predstavlja muzicki zanr koji moze biti dodeljen izvodjacu.
 *
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
     * Naziv zanra. Ne sme biti null i mora biti jedinstven u bazi podataka.
     */
    @Column(nullable = false, unique = true)
    private String naziv;

}