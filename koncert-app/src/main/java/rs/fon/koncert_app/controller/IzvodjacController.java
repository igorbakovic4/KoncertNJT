package rs.fon.koncert_app.controller;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.koncert_app.entity.Bend;
import rs.fon.koncert_app.entity.Izvodjac;
import rs.fon.koncert_app.entity.Muzicar;
import rs.fon.koncert_app.service.IzvodjacService;

import java.util.List;

@RestController
@RequestMapping("/api/izvodjaci")
@RequiredArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tip")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Muzicar.class, name = "MUZICAR"),
        @JsonSubTypes.Type(value = Bend.class, name = "BEND")
})
public class IzvodjacController {

    private final IzvodjacService izvodjacService;

    @GetMapping
    public ResponseEntity<List<Izvodjac>> dohvatiSve() {
        return ResponseEntity.ok(izvodjacService.dohvatiSve());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Izvodjac> dohvatiPoId(@PathVariable Long id) {
        return ResponseEntity.ok(izvodjacService.dohvatiPoId(id));
    }

    @PostMapping("/muzicar")
    public ResponseEntity<Muzicar> sacuvajMuzicara(@RequestBody Muzicar muzicar) {
        return ResponseEntity.ok(izvodjacService.sacuvajMuzicara(muzicar));
    }

    @PostMapping("/bend")
    public ResponseEntity<Bend> sacuvajBend(@RequestBody Bend bend) {
        return ResponseEntity.ok(izvodjacService.sacuvajBend(bend));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> obrisiIzvodjaca(@PathVariable Long id) {
        izvodjacService.obrisiIzvodjaca(id);
        return ResponseEntity.noContent().build();
    }
} 