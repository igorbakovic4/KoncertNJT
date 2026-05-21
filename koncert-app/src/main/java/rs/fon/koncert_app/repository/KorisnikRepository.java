package rs.fon.koncert_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.koncert_app.entity.Korisnik;
import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    Optional<Korisnik> findByEmail(String email);
    boolean existsByEmail(String email);
}