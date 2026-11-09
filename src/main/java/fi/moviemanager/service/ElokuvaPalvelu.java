package fi.moviemanager.service;

import fi.moviemanager.model.Lajityyppi;
import fi.moviemanager.model.Elokuva;
import fi.moviemanager.repository.LajityyppiVarasto;
import fi.moviemanager.repository.ElokuvaVarasto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ElokuvaPalvelu {
    private final ElokuvaVarasto elokuvaVarasto;
    private final LajityyppiVarasto lajityyppiVarasto;

    public ElokuvaPalvelu(ElokuvaVarasto elokuvaVarasto, LajityyppiVarasto lajityyppiVarasto) {
        this.elokuvaVarasto = elokuvaVarasto;
        this.lajityyppiVarasto = lajityyppiVarasto;
    }

    @Transactional(readOnly = true)
    public List<Elokuva> hae(String hakusana, Long lajityyppiTunnus) {
        if (lajityyppiTunnus != null) {
            return elokuvaVarasto.findByLajityyppi_Tunnus(lajityyppiTunnus);
        }
        if (hakusana != null && !hakusana.isBlank()) {
            String hakutermi = hakusana.trim();
            return elokuvaVarasto.findByNimiContainingIgnoreCaseOrOhjaajaContainingIgnoreCase(
                    hakutermi, hakutermi);
        }
        return elokuvaVarasto.findAllByOrderByNimiAsc();
    }

    @Transactional(readOnly = true)
    public List<Elokuva> haeKaikki() {
        return elokuvaVarasto.findAllByOrderByNimiAsc();
    }

    @Transactional(readOnly = true)
    public Elokuva haeTunnuksella(Long tunnus) {
        return elokuvaVarasto.findById(tunnus)
                .orElseThrow(() -> new IllegalArgumentException("Elokuvaa ei löytynyt: " + tunnus));
    }

    public Elokuva tallenna(Elokuva elokuva, Long lajityyppiTunnus) {
        Lajityyppi lajityyppi = lajityyppiVarasto.findById(lajityyppiTunnus)
                .orElseThrow(() -> new IllegalArgumentException("Genreä ei löytynyt."));
        elokuva.setGenre(lajityyppi);
        return elokuvaVarasto.save(elokuva);
    }

    public void poista(Long tunnus) {
        elokuvaVarasto.deleteById(tunnus);
    }

    @Transactional(readOnly = true)
    public List<Lajityyppi> haeLajityypit() {
        return lajityyppiVarasto.findAll();
    }
}
