package fi.moviemanager.config;

import fi.moviemanager.malli.*;
import fi.moviemanager.varasto.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.salasana.PasswordEncoder;

@Configuration
public class AloitustietojenAlustaja {
    @Bean
    CommandLineRunner alustaTiedot(LajityyppiVarasto lajityyppiVarasto,
                                     ElokuvaVarasto elokuvaVarasto,
                                     KayttajaVarasto kayttajaVarasto,
                                     PasswordEncoder salasananKoodaaja) {
        return parametrit -> {
            if (kayttajaVarasto.findByUsername("admin").isEmpty()) {
                kayttajaVarasto.tallenna(new Sovelluskayttaja("admin",
                        salasananKoodaaja.encode("admin123"), "ADMIN"));
            }

            lisaaLajityyppi(lajityyppiVarasto, "Draama");
            lisaaLajityyppi(lajityyppiVarasto, "Toiminta");
            lisaaLajityyppi(lajityyppiVarasto, "Komedia");
            lisaaLajityyppi(lajityyppiVarasto, "Sci-Fi");
            lisaaLajityyppi(lajityyppiVarasto, "Jännitys");

            if (elokuvaVarasto.count() == 0) {
                lisaaElokuva(elokuvaVarasto, lajityyppiVarasto, "Inception", 2010,
                        "Christopher Nolan", "Unien ja todellisuuden rajoja tutkiva scifi-trilleri.", "Sci-Fi");
                lisaaElokuva(elokuvaVarasto, lajityyppiVarasto, "The Shawshank Redemption", 1994,
                        "Frank Darabont", "Tarina ystävyydestä ja toivosta vankilassa.", "Draama");
                lisaaElokuva(elokuvaVarasto, lajityyppiVarasto, "The Dark Knight", 2008,
                        "Christopher Nolan", "Gotham Cityn sankarin ja Jokerin välinen taistelu.", "Toiminta");
            }
        };
    }

    private void lisaaLajityyppi(LajityyppiVarasto varasto, String nimi) {
        if (varasto.findByNameIgnoreCase(nimi).isEmpty()) {
            varasto.tallenna(new Lajityyppi(nimi));
        }
    }

    private void lisaaElokuva(ElokuvaVarasto elokuvat, LajityyppiVarasto lajityypit, String nimi,
                          int vuosi, String ohjaaja, String kuvaus, String lajityypinNimi) {
        Lajityyppi lajityyppi = lajityypit.findByNameIgnoreCase(lajityypinNimi).orElseThrow();
        Elokuva elokuva = new Elokuva();
        elokuva.setTitle(nimi);
        elokuva.setReleaseYear(vuosi);
        elokuva.setDirector(ohjaaja);
        elokuva.setDescription(kuvaus);
        elokuva.setGenre(lajityyppi);
        elokuvat.tallenna(elokuva);
    }
}
