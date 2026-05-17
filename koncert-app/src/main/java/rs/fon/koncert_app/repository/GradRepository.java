package rs.fon.koncert_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.koncert_app.entity.Grad;

@Repository
public interface GradRepository extends JpaRepository <Grad, Long> {
}
