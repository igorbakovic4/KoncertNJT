package rs.fon.koncert_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.koncert_app.entity.Karta;

public interface KartaRepository extends JpaRepository<Karta, Long> {
}
