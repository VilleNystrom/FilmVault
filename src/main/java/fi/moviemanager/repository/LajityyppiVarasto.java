package fi.moviemanager.repository;

import fi.moviemanager.model.Lajityyppi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LajityyppiVarasto extends JpaRepository<Lajityyppi, Long> {
    Optional<Lajityyppi> findByNimiIgnoreCase(String nimi);
}
