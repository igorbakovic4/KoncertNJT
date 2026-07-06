package rs.fon.koncert_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.koncert_app.entity.Karta;

import java.util.List;
import java.util.Optional;

@Repository
public interface KartaRepository extends JpaRepository<Karta, Long> {
    List<Karta> findByKoncertId(Long koncertId);
    long countByKoncertIdAndStatus(Long koncertId, Karta.StatusKarte status);
    Optional<Karta> findFirstByKoncertIdAndStatus(Long koncertId, Karta.StatusKarte statusKarte);
}
