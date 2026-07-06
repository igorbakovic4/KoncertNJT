package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa Grad predstavlja grad u kome se nalazi lokacija koncerta.
 *
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
     * Naziv grada. Ne sme biti null.
     */
    @Column(nullable = false)
    private String naziv;

}