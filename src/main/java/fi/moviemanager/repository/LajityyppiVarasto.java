package fi.moviemanager.varasto;

import fi.moviemanager.malli.Lajityyppi;
import org.springframework.data.jpa.varasto.JpaRepository;
import java.util.Optional;

public interface LajityyppiVarasto extends JpaRepository<Lajityyppi, Long> {
    Optional<Lajityyppi> findByNameIgnoreCase(String nimi);
}
