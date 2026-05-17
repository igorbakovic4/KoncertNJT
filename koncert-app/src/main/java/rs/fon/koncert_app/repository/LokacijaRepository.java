package rs.fon.koncert_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.koncert_app.entity.Lokacija;

import java.util.List;

@Repository
public interface LokacijaRepository extends JpaRepository<Lokacija, Long> {

}
