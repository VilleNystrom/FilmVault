package fi.moviemanager.varasto;

import fi.moviemanager.malli.Sovelluskayttaja;
import org.springframework.data.jpa.varasto.JpaRepository;
import java.util.Optional;

public interface KayttajaVarasto extends JpaRepository<Sovelluskayttaja, Long> {
    Optional<Sovelluskayttaja> findByUsername(String kayttajatunnus);
}
