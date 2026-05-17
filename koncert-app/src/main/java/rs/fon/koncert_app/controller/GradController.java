package rs.fon.koncert_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.koncert_app.entity.Grad;
import rs.fon.koncert_app.service.GradService;

import java.util.List;

@RestController
@RequestMapping("/api/gradovi")
@RequiredArgsConstructor
public class GradController {

    private final GradService gradService;

    public ResponseEntity<List<Grad>> findAll() {
        return ResponseEntity.ok(gradService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grad> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gradService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Grad> save(@RequestBody Grad grad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradService.save(grad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grad> update(@PathVariable Long id, @RequestBody Grad grad) {
        return ResponseEntity.ok(gradService.update(id, grad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Grad> delete(@PathVariable Long id) {
        gradService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
