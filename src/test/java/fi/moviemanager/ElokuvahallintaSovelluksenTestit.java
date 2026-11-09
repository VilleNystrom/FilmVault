package fi.moviemanager;

import fi.moviemanager.malli.Lajityyppi;
import fi.moviemanager.malli.Elokuva;
import fi.moviemanager.varasto.LajityyppiVarasto;
import fi.moviemanager.varasto.ElokuvaVarasto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ElokuvahallintaSovelluksenTestit {

    @Autowired
    private ElokuvaVarasto elokuvaVarasto;

    @Autowired
    private LajityyppiVarasto lajityyppiVarasto;

    @Test
    void sovelluksenKontekstiLatautuu() {
        assertThat(lajityyppiVarasto.count()).isGreaterThanOrEqualTo(5);
    }

    @Test
    void elokuvanTallennusLajityypillaOnnistuu() {
        Lajityyppi lajityyppi = lajityyppiVarasto.findByNameIgnoreCase("Draama").orElseThrow();

        Elokuva elokuva = new Elokuva();
        elokuva.setTitle("Testielokuva");
        elokuva.setReleaseYear(2024);
        elokuva.setDirector("Testiohjaaja");
        elokuva.setDescription("Testikuvaus");
        elokuva.setGenre(lajityyppi);

        Elokuva tallennettu = elokuvaVarasto.tallenna(elokuva);

        assertThat(tallennettu.getId()).isNotNull();
        assertThat(elokuvaVarasto.haeTunnuksella(tallennettu.getId()).orElseThrow().getGenre().getName())
                .isEqualTo("Draama");
    }
}
