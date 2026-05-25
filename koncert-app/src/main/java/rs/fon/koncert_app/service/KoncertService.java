package rs.fon.koncert_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Izvodjac;
import rs.fon.koncert_app.entity.Karta;
import rs.fon.koncert_app.entity.Koncert;
import rs.fon.koncert_app.repository.IzvodjacRepository;
import rs.fon.koncert_app.repository.KartaRepository;
import rs.fon.koncert_app.repository.KoncertRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KoncertService {

    private final KoncertRepository koncertRepository;
    private final IzvodjacRepository izvodjacRepository;
    private final KartaRepository kartaRepository;
    private final LokacijaService lokacijaService;

    public List<Koncert> findAll() {
        return koncertRepository.findAll();
    }

    public Koncert findById(Long id) {
        return koncertRepository.findById(id).orElseThrow(() -> new RuntimeException("Koncert sa id-em " + id + " ne postoji."));
    }

    public List<Koncert> findByDatum(LocalDate datum) {
        return koncertRepository.findByDatum(datum);
    }

    public List<Koncert> findByStatus(Koncert.StatusKoncerta status) {
        return koncertRepository.findByStatus(status);
    }

    public Koncert save(Koncert koncert) {
        lokacijaService.findById(koncert.getLokacija().getId());

        boolean terminZauzet = koncertRepository.findByLokacijaAndDatumAndVremePocetka(
                koncert.getLokacija(), koncert.getDatum(), koncert.getVremePocetka()).isPresent();

        if(terminZauzet) {
            throw new RuntimeException("Lokacija je vec zauzeta u ovom terminu.");
        }

        return koncertRepository.save(koncert);
    }

    public Koncert update(Long id, Koncert koncert) {
        Koncert postojeci = findById(id);
        postojeci.setNaziv(koncert.getNaziv());
        postojeci.setDatum(koncert.getDatum());
        postojeci.setVremePocetka(koncert.getVremePocetka());
        postojeci.setVremeTrajanja(koncert.getVremeTrajanja());
        postojeci.setStatus(koncert.getStatus());
        postojeci.setLokacija(koncert.getLokacija());
        postojeci.setIzvodjaci(koncert.getIzvodjaci());
        return koncertRepository.save(postojeci);
    }

    public Koncert updateIzvodjaci(Long id, List<Long> izvodjacIds) {
        Koncert koncert = findById(id);
        List<Izvodjac> izvodjaci = izvodjacRepository.findAllById(izvodjacIds);
        koncert.setIzvodjaci(izvodjaci);
        return koncertRepository.save(koncert);
    }

    public void delete(Long id) {
        Koncert koncert = findById(id);
        koncertRepository.delete(koncert);
    }

    public List<Karta> generisiKarte(Long koncertId, BigDecimal cena) {
        Koncert koncert = findById(koncertId);

        List<Karta> postojeceKarte = kartaRepository.findByKoncertId(koncertId);
        if (!postojeceKarte.isEmpty()) {
            throw new RuntimeException("Karte za ovaj koncert već postoje.");
        }

        int kapacitet = koncert.getLokacija().getKapacitet();
        int redovi = (int) Math.sqrt(kapacitet);
        int sedistaPoRedu = kapacitet / redovi;

        List<Karta> karte = new ArrayList<>();
        for (int red = 1; red <= redovi; red++) {
            for (int sediste = 1; sediste <= sedistaPoRedu; sediste++) {
                Karta karta = new Karta();
                karta.setKoncert(koncert);
                karta.setRed(red);
                karta.setSediste(sediste);
                karta.setCena(cena);
                karta.setStatus(Karta.StatusKarte.DOSTUPNA);
                karte.add(karta);
            }
        }

        return kartaRepository.saveAll(karte);
    }

}
