package rs.fon.koncert_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.koncert_app.entity.Koncert;
import rs.fon.koncert_app.entity.Lokacija;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface KoncertRepository extends JpaRepository<Koncert, Long> {

    List<Koncert> findByDatum(LocalDate datum);
    List<Koncert> findByStatus(Koncert.StatusKoncerta status);
    Optional<Koncert> findByLokacijaAndDatumAndVremePocetka(Lokacija lokacija, LocalDate datum, LocalTime vremePocetka);

}
