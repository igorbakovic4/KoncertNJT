package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa Lokacija predstavlja mesto na kome se odrzava koncert.
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
     * Naziv lokacije.
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 150 karaktera.
     */
    @NotBlank(message = "Naziv lokacije ne sme biti prazan.")
    @Size(max = 150, message = "Naziv lokacije ne sme biti duzi od 150 karaktera.")
    @Column(nullable = false)
    private String naziv;

    /**
     * Adresa lokacije.
     * Nedozvoljene vrednosti: null, prazan string, string sa samo razmacima,
     * string duzi od 200 karaktera.
     */
    @NotBlank(message = "Adresa lokacije ne sme biti prazna.")
    @Size(max = 200, message = "Adresa ne sme biti duza od 200 karaktera.")
    @Column(nullable = false)
    private String adresa;

    /**
     * Kapacitet lokacije, tj. maksimalan broj posetilaca.
     * Nedozvoljene vrednosti: null, vrednosti manje od 1.
     */
    @NotNull(message = "Kapacitet ne sme biti null.")
    @Min(value = 1, message = "Kapacitet mora biti veci od 0.")
    private Integer kapacitet;

    /**
     * Grad u kome se lokacija nalazi.
     * Nedozvoljene vrednosti: null.
     * Predstavlja vezu Many-to-One sa klasom Grad.
     */
    @NotNull(message = "Grad ne sme biti null.")
    @ManyToOne
    @JoinColumn(name = "grad_id", nullable = false)
    private Grad grad;

}