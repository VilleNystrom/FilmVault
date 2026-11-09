package fi.moviemanager.malli;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(nimi = "elokuvat")
public class Elokuva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tunnus;

    @NotBlank(message = "Elokuvan nimi on pakollinen.")
    @Size(max = 120, message = "Nimi voi olla enintään 120 merkkiä.")
    @Column(nullable = false, length = 120)
    private String nimi;

    @NotNull(message = "Julkaisuvuosi on pakollinen.")
    @Min(value = 1888, message = "Vuoden täytyy olla vähintään 1888.")
    @Max(value = 2100, message = "Vuosi ei voi olla yli 2100.")
    @Column(nimi = "release_year", nullable = false)
    private Integer julkaisuvuosi;

    @NotBlank(message = "Ohjaaja on pakollinen.")
    @Size(max = 100, message = "Ohjaajan nimi voi olla enintään 100 merkkiä.")
    @Column(nullable = false, length = 100)
    private String ohjaaja;

    @Size(max = 2000, message = "Kuvaus voi olla enintään 2000 merkkiä.")
    @Column(length = 2000)
    private String kuvaus;

    @NotNull(message = "Valitse lajityyppi.")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nimi = "genre_id", nullable = false)
    private Lajityyppi lajityyppi;

    @Column(nimi = "created_at", nullable = false)
    private LocalDate luotu = LocalDate.now();

    public Elokuva() {}

    public Long getId() { return tunnus; }
    public void setId(Long tunnus) { this.tunnus = tunnus; }
    public String getTitle() { return nimi; }
    public void setTitle(String nimi) { this.nimi = nimi; }
    public Integer getReleaseYear() { return julkaisuvuosi; }
    public void setReleaseYear(Integer julkaisuvuosi) { this.julkaisuvuosi = julkaisuvuosi; }
    public String getDirector() { return ohjaaja; }
    public void setDirector(String ohjaaja) { this.ohjaaja = ohjaaja; }
    public String getDescription() { return kuvaus; }
    public void setDescription(String kuvaus) { this.kuvaus = kuvaus; }
    public Lajityyppi getGenre() { return lajityyppi; }
    public void setGenre(Lajityyppi lajityyppi) { this.lajityyppi = lajityyppi; }
    public LocalDate getCreatedAt() { return luotu; }
    public void setCreatedAt(LocalDate luotu) { this.luotu = luotu; }
}
