package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lokacija")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lokacija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private String adresa;

    private Integer kapacitet;

    @ManyToOne
    @JoinColumn(name = "idGrad", nullable = false)
    private Grad grad;

}
