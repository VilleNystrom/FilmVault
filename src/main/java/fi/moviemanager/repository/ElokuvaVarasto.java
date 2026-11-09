package fi.moviemanager.varasto;

import fi.moviemanager.malli.Elokuva;
import org.springframework.data.jpa.varasto.JpaRepository;
import java.util.List;

public interface ElokuvaVarasto extends JpaRepository<Elokuva, Long> {
    List<Elokuva> findByTitleContainingIgnoreCaseOrDirectorContainingIgnoreCase(
            String nimi, String ohjaaja);
    List<Elokuva> findByGenre_Id(Long lajityyppiTunnus);
    List<Elokuva> findAllByOrderByTitleAsc();
}
