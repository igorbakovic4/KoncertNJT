package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(
    name = "karta",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"koncert_id", "red", "sediste"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Karta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "koncert_id", nullable = false)
    private Koncert koncert;

    @Column(nullable = false)
    private Integer red;

    @Column(nullable = false)
    private Integer sediste;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cena;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusKarte status;

    @Column(name = "datum_prodaje")
    private LocalDateTime datumProdaje;

    @Column(name = "ime_kupca")
    private String imeKupca;

    @Column(name = "email_kupca")
    private String emailKupca;

    public enum StatusKarte {
        DOSTUPNA,
        REZERVISANA,
        PRODATA,
        STORNIRANA
    }
}
