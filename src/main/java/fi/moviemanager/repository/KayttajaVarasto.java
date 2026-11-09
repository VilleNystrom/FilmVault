package fi.moviemanager.repository;

import fi.moviemanager.model.Sovelluskayttaja;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KayttajaVarasto extends JpaRepository<Sovelluskayttaja, Long> {
    Optional<Sovelluskayttaja> findByKayttajatunnus(String kayttajatunnus);
}
