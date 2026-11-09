package fi.moviemanager.service;

import fi.moviemanager.malli.Lajityyppi;
import fi.moviemanager.malli.Elokuva;
import fi.moviemanager.varasto.LajityyppiVarasto;
import fi.moviemanager.varasto.ElokuvaVarasto;
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
            return elokuvaVarasto.findByGenre_Id(lajityyppiTunnus);
        }
        if (hakusana != null && !hakusana.isBlank()) {
            String hakutermi = hakusana.trim();
            return elokuvaVarasto.findByTitleContainingIgnoreCaseOrDirectorContainingIgnoreCase(
                    hakutermi, hakutermi);
        }
        return elokuvaVarasto.findAllByOrderByTitleAsc();
    }

    @Transactional(readOnly = true)
    public List<Elokuva> haeKaikki() {
        return elokuvaVarasto.findAllByOrderByTitleAsc();
    }

    @Transactional(readOnly = true)
    public Elokuva haeTunnuksella(Long tunnus) {
        return elokuvaVarasto.haeTunnuksella(tunnus)
                .orElseThrow(() -> new IllegalArgumentException("Elokuvaa ei löytynyt: " + tunnus));
    }

    public Elokuva tallenna(Elokuva elokuva, Long lajityyppiTunnus) {
        Lajityyppi lajityyppi = lajityyppiVarasto.haeTunnuksella(lajityyppiTunnus)
                .orElseThrow(() -> new IllegalArgumentException("Genreä ei löytynyt."));
        elokuva.setGenre(lajityyppi);
        return elokuvaVarasto.tallenna(elokuva);
    }

    public void poista(Long tunnus) {
        elokuvaVarasto.deleteById(tunnus);
    }

    @Transactional(readOnly = true)
    public List<Lajityyppi> haeLajityypit() {
        return lajityyppiVarasto.haeKaikki();
    }
}
