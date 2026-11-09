package fi.moviemanager.controller;

import fi.moviemanager.model.Elokuva;
import fi.moviemanager.service.ElokuvaPalvelu;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class YllapitoOhjain {
    private final ElokuvaPalvelu elokuvaPalvelu;

    public YllapitoOhjain(ElokuvaPalvelu elokuvaPalvelu) {
        this.elokuvaPalvelu = elokuvaPalvelu;
    }

    @GetMapping
    public String hallintapaneeli(Model malli) {
        malli.addAttribute("elokuvat", elokuvaPalvelu.haeKaikki());
        return "admin/dashboard";
    }

    @GetMapping("/elokuvat/new")
    public String uusiElokuva(Model malli) {
        malli.addAttribute("elokuva", new Elokuva());
        malli.addAttribute("lajityypit", elokuvaPalvelu.haeLajityypit());
        return "admin/movie-form";
    }

    @GetMapping("/elokuvat/{tunnus}/edit")
    public String muokkaaElokuvaa(@PathVariable Long tunnus, Model malli) {
        Elokuva elokuva = elokuvaPalvelu.haeTunnuksella(tunnus);
        malli.addAttribute("elokuva", elokuva);
        malli.addAttribute("lajityypit", elokuvaPalvelu.haeLajityypit());
        malli.addAttribute("valittuLajityyppiTunnus", elokuva.getGenre().getId());
        return "admin/movie-form";
    }

    @PostMapping("/elokuvat/tallenna")
    public String tallennaElokuva(@Valid @ModelAttribute("elokuva") Elokuva elokuva,
                            BindingResult sidontaTulos,
                            @RequestParam(required = false) Long lajityyppiTunnus,
                            Model malli,
                            RedirectAttributes uudelleenohjausTiedot) {
        if (lajityyppiTunnus != null) {
            elokuva.setGenre(elokuvaPalvelu.haeLajityypit().stream()
                    .filter(lajityyppi -> lajityyppi.getId().equals(lajityyppiTunnus))
                    .findFirst().orElse(null));
        }
        if (elokuva.getGenre() == null) {
            sidontaTulos.rejectValue("lajityyppi", "lajityyppi.required", "Valitse lajityyppi.");
        }
        if (sidontaTulos.hasErrors()) {
            malli.addAttribute("lajityypit", elokuvaPalvelu.haeLajityypit());
            malli.addAttribute("valittuLajityyppiTunnus", lajityyppiTunnus);
            return "admin/movie-form";
        }

        elokuvaPalvelu.tallenna(elokuva, lajityyppiTunnus);
        uudelleenohjausTiedot.addFlashAttribute("onnistumisViesti", "Elokuva tallennettiin.");
        return "redirect:/admin";
    }

    @PostMapping("/elokuvat/{tunnus}/poista")
    public String poistaElokuva(@PathVariable Long tunnus, RedirectAttributes uudelleenohjausTiedot) {
        elokuvaPalvelu.poista(tunnus);
        uudelleenohjausTiedot.addFlashAttribute("onnistumisViesti", "Elokuva poistettiin.");
        return "redirect:/admin";
    }
}
