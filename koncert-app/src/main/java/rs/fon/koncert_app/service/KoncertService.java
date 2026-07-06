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

/**
 * Servis koji implementira sistemske operacije za upravljanje koncertima.
 *
 * Pruza CRUD operacije nad domenskom klasom Koncert, kao i specificne
 * operacije kao sto su provera zauzetosti termina i generisanje karata.
 *
 * @author igor
 * @see Koncert
 * @see Karta
 */
@Service
@RequiredArgsConstructor
public class KoncertService {

    private final KoncertRepository koncertRepository;
    private final IzvodjacRepository izvodjacRepository;
    private final KartaRepository kartaRepository;
    private final LokacijaService lokacijaService;

    /**
     * Vraca listu svih koncerata iz baze podataka.
     *
     * @return lista svih koncerata, ili prazna lista ako nema koncerata.
     */
    public List<Koncert> findAll() {
        return koncertRepository.findAll();
    }

    /**
     * Vraca koncert sa prosledjenim identifikatorom.
     *
     * @param id Identifikator koncerta koji se trazi.
     * @return koncert sa prosledjenim identifikatorom.
     * @throws java.lang.RuntimeException Ukoliko koncert sa prosledjenim id-em ne postoji.
     */
    public Koncert findById(Long id) {
        return koncertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Koncert sa id-em " + id + " ne postoji."));
    }

    /**
     * Vraca listu koncerata koji se odrzavaju na prosledjeni datum.
     *
     * @param datum Datum za koji se traze koncerti.
     * @return lista koncerata na prosledjeni datum, ili prazna lista.
     */
    public List<Koncert> findByDatum(LocalDate datum) {
        return koncertRepository.findByDatum(datum);
    }

    /**
     * Vraca listu koncerata sa prosledjenim statusom.
     *
     * @param status Status po kome se filtriraju koncerti.
     * @return lista koncerata sa prosledjenim statusom, ili prazna lista.
     */
    public List<Koncert> findByStatus(Koncert.StatusKoncerta status) {
        return koncertRepository.findByStatus(status);
    }

    /**
     * Cuva prosledjeni koncert u bazi podataka.
     * Pre cuvanja proverava da li lokacija postoji i da li je termin slobodan.
     *
     * @param koncert Koncert koji treba sacuvati.
     * @return sacuvani koncert sa dodeljenim identifikatorom.
     * @throws java.lang.RuntimeException Ukoliko lokacija ne postoji.
     * @throws java.lang.RuntimeException Ukoliko je termin vec zauzet na toj lokaciji.
     */
    public Koncert save(Koncert koncert) {
        lokacijaService.findById(koncert.getLokacija().getId());

        boolean terminZauzet = koncertRepository.findByLokacijaAndDatumAndVremePocetka(
                koncert.getLokacija(), koncert.getDatum(), koncert.getVremePocetka()).isPresent();

        if (terminZauzet) {
            throw new RuntimeException("Lokacija je vec zauzeta u ovom terminu.");
        }

        return koncertRepository.save(koncert);
    }

    /**
     * Azurira podatke o koncertu sa prosledjenim identifikatorom.
     *
     * @param id Identifikator koncerta koji treba azurirati.
     * @param koncert Koncert sa novim podacima.
     * @return azurirani koncert.
     * @throws java.lang.RuntimeException Ukoliko koncert sa prosledjenim id-em ne postoji.
     */
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

    /**
     * Azurira listu izvodjaca na koncertu sa prosledjenim identifikatorom.
     *
     * @param id Identifikator koncerta.
     * @param izvodjacIds Lista identifikatora izvodjaca koji nastupaju na koncertu.
     * @return koncert sa azuriranom listom izvodjaca.
     * @throws java.lang.RuntimeException Ukoliko koncert sa prosledjenim id-em ne postoji.
     */
    public Koncert updateIzvodjaci(Long id, List<Long> izvodjacIds) {
        Koncert koncert = findById(id);
        List<Izvodjac> izvodjaci = izvodjacRepository.findAllById(izvodjacIds);
        koncert.setIzvodjaci(izvodjaci);
        return koncertRepository.save(koncert);
    }

    /**
     * Brise koncert sa prosledjenim identifikatorom iz baze podataka.
     *
     * @param id Identifikator koncerta koji treba obrisati.
     * @throws java.lang.RuntimeException Ukoliko koncert sa prosledjenim id-em ne postoji.
     */
    public void delete(Long id) {
        Koncert koncert = findById(id);
        koncertRepository.delete(koncert);
    }

    /**
     * Generise karte za koncert sa prosledjenim identifikatorom.
     * Broj generisanih karata odgovara kapacitetu lokacije na kojoj se odrzava koncert.
     * Sve generisane karte imaju status DOSTUPNA i prosledjenu cenu.
     *
     * @param koncertId Identifikator koncerta za koji se generisu karte.
     * @param cena Cena svake generisane karte.
     * @return lista generisanih karata.
     * @throws java.lang.RuntimeException Ukoliko koncert sa prosledjenim id-em ne postoji.
     * @throws java.lang.RuntimeException Ukoliko karte za ovaj koncert vec postoje.
     */
    public List<Karta> generisiKarte(Long koncertId, BigDecimal cena) {
        Koncert koncert = findById(koncertId);

        List<Karta> postojeceKarte = kartaRepository.findByKoncertId(koncertId);
        if (!postojeceKarte.isEmpty()) {
            throw new RuntimeException("Karte za ovaj koncert vec postoje.");
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