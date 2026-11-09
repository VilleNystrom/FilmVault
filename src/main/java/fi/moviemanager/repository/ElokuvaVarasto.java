package fi.moviemanager.repository;

import fi.moviemanager.model.Elokuva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ElokuvaVarasto extends JpaRepository<Elokuva, Long> {
    List<Elokuva> findByNimiContainingIgnoreCaseOrOhjaajaContainingIgnoreCase(
            String nimi, String ohjaaja);
    List<Elokuva> findByLajityyppi_Tunnus(Long lajityyppiTunnus);
    List<Elokuva> findAllByOrderByNimiAsc();
}
