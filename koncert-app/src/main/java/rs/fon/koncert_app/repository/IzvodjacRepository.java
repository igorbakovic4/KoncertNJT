package rs.fon.koncert_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.koncert_app.entity.Izvodjac;

@Repository
public interface IzvodjacRepository extends JpaRepository<Izvodjac, Long> {
}
