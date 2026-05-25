package rs.fon.koncert_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.fon.koncert_app.entity.Grad;
import rs.fon.koncert_app.service.GradService;

import java.util.List;

@RestController
@RequestMapping("/api/gradovi")
@RequiredArgsConstructor
public class GradController {

    private final GradService gradService;

    @GetMapping
    public ResponseEntity<List<Grad>> findAll() {
        return ResponseEntity.ok(gradService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grad> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gradService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Grad> save(@RequestBody Grad grad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradService.save(grad));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Grad> update(@PathVariable Long id, @RequestBody Grad grad) {
        return ResponseEntity.ok(gradService.update(id, grad));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gradService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
