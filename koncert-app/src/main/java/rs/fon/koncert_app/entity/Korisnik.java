package rs.fon.koncert_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "korisnik")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}