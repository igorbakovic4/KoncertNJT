package rs.fon.koncert_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.koncert_app.entity.Koncert;

public interface KoncertRepository extends JpaRepository<Koncert, Long> {
}
