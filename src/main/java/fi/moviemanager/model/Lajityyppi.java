package fi.moviemanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lajityypit")
public class Lajityyppi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tunnus;

    @NotBlank(message = "Genren nimi on pakollinen.")
    @Size(max = 60, message = "Genren nimi voi olla enintään 60 merkkiä.")
    @Column(nullable = false, unique = true, length = 60)
    private String nimi;

    @OneToMany(mappedBy = "lajityyppi")
    private List<Elokuva> elokuvat = new ArrayList<>();

    public Lajityyppi() {}
    public Lajityyppi(String nimi) { this.nimi = nimi; }

    public Long getId() { return tunnus; }
    public void setId(Long tunnus) { this.tunnus = tunnus; }
    public String getName() { return nimi; }
    public void setName(String nimi) { this.nimi = nimi; }
    public List<Elokuva> haeElokuvat() { return elokuvat; }
    public Long getTunnus() { return tunnus; }
    public String getNimi() { return nimi; }
    public void setNimi(String nimi) { this.nimi = nimi; }
}
