package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa Lokacija predstavlja mesto na kome se odrzava koncert.
 *
 * Svaka lokacija ima naziv, adresu, kapacitet i pripada jednom gradu.
 *
 * @author igor
 */
@Entity
@Table(name = "lokacija")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lokacija {

    /**
     * Jedinstveni identifikator lokacije koji se automatski generise u bazi podataka.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Naziv lokacije. Ne sme biti null.
     */
    @Column(nullable = false)
    private String naziv;

    /**
     * Adresa lokacije. Ne sme biti null.
     */
    @Column(nullable = false)
    private String adresa;

    /**
     * Kapacitet lokacije, tj. maksimalan broj posetilaca.
     */
    private Integer kapacitet;

    /**
     * Grad u kome se lokacija nalazi.
     * Predstavlja vezu Many-to-One sa klasom Grad.
     */
    @ManyToOne
    @JoinColumn(name = "grad_id", nullable = false)
    private Grad grad;

}