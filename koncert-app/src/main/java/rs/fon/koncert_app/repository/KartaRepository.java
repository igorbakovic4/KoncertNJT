package rs.fon.koncert_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.koncert_app.entity.Karta;

@Repository
public interface KartaRepository extends JpaRepository<Karta, Long> {
}
