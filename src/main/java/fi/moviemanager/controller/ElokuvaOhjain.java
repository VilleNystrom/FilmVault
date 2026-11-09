package fi.moviemanager.controller;

import fi.moviemanager.service.ElokuvaPalvelu;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ElokuvaOhjain {
    private final ElokuvaPalvelu elokuvaPalvelu;

    public ElokuvaOhjain(ElokuvaPalvelu elokuvaPalvelu) {
        this.elokuvaPalvelu = elokuvaPalvelu;
    }

    @GetMapping("/elokuvat")
    public String luetteleElokuvat(@RequestParam(required = false) String q,
                             @RequestParam(required = false) Long lajityyppiTunnus,
                             Model malli) {
        malli.addAttribute("elokuvat", elokuvaPalvelu.hae(q, lajityyppiTunnus));
        malli.addAttribute("lajityypit", elokuvaPalvelu.haeLajityypit());
        malli.addAttribute("q", q == null ? "" : q);
        malli.addAttribute("valittuLajityyppi", lajityyppiTunnus);
        return "movies";
    }

    @GetMapping("/elokuvat/{tunnus}")
    public String elokuvanTiedot(@PathVariable Long tunnus, Model malli) {
        malli.addAttribute("elokuva", elokuvaPalvelu.haeTunnuksella(tunnus));
        return "movie-details";
    }
}
