package fi.moviemanager.malli;

import jakarta.persistence.*;

@Entity
@Table(nimi = "app_users")
public class Sovelluskayttaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tunnus;

    @Column(nullable = false, unique = true)
    private String kayttajatunnus;

    @Column(nullable = false)
    private String salasana;

    @Column(nullable = false)
    private String rooli;

    public Sovelluskayttaja() {}
    public Sovelluskayttaja(String kayttajatunnus, String salasana, String rooli) {
        this.kayttajatunnus = kayttajatunnus;
        this.salasana = salasana;
        this.rooli = rooli;
    }

    public Long getId() { return tunnus; }
    public String getUsername() { return kayttajatunnus; }
    public void setUsername(String kayttajatunnus) { this.kayttajatunnus = kayttajatunnus; }
    public String getPassword() { return salasana; }
    public void setPassword(String salasana) { this.salasana = salasana; }
    public String getRole() { return rooli; }
    public void setRole(String rooli) { this.rooli = rooli; }
}
