package fi.moviemanager.controller;

import fi.moviemanager.dto.ElokuvaVastaus;
import fi.moviemanager.model.Elokuva;
import fi.moviemanager.service.ElokuvaPalvelu;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/elokuvat")
public class ElokuvaRajapintaOhjain {
    private final ElokuvaPalvelu elokuvaPalvelu;

    public ElokuvaRajapintaOhjain(ElokuvaPalvelu elokuvaPalvelu) {
        this.elokuvaPalvelu = elokuvaPalvelu;
    }

    @GetMapping
    public List<ElokuvaVastaus> haeElokuvat(@RequestParam(required = false) String q,
                                         @RequestParam(required = false) Long lajityyppiTunnus) {
        return elokuvaPalvelu.hae(q, lajityyppiTunnus).stream().map(this::muunnaVastaukseksi).toList();
    }

    @GetMapping("/{tunnus}")
    public ElokuvaVastaus haeElokuva(@PathVariable Long tunnus) {
        return muunnaVastaukseksi(elokuvaPalvelu.haeTunnuksella(tunnus));
    }

    private ElokuvaVastaus muunnaVastaukseksi(Elokuva elokuva) {
        return new ElokuvaVastaus(elokuva.getId(), elokuva.getTitle(), elokuva.getReleaseYear(),
                elokuva.getDirector(), elokuva.getDescription(), elokuva.getGenre().getName());
    }
}
