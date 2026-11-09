# MovieManager

Spring Boot -harjoitustyö elokuvien ylläpitoon.

## Vaatimukset

- Java 17 (tai uudempi)
- Maven 3.6+ (tai VS Coden Java Extension Packin Maven-tuki)
- Visual Studio Code

## Käynnistäminen VS Codessa

1. Pura ZIP-tiedosto esimerkiksi `C:\projektit\MovieManager`-kansioon.
2. Avaa VS Codessa **File → Open Folder** ja valitse `MovieManager`.
3. Asenna tarvittaessa VS Code -laajennus **Extension Pack for Java**.
4. Avaa terminaali (Terminal → New Terminal).
5. Suorita projektin juuressa:

   ```bash
   mvn spring-boot:run
   ```

6. Avaa selaimessa http://localhost:8080

Jos Maven-komentoa ei löydy, asenna Maven tai suorita sovellus VS Coden Spring Boot Dashboardista.

## Osoitteet

- Julkinen elokuvalista: http://localhost:8080/elokuvat
- Kirjautuminen: http://localhost:8080/login
- Ylläpito: http://localhost:8080/admin
- REST-listaus: http://localhost:8080/api/elokuvat
- H2 Console: http://localhost:8080/h2-console

## Testikäyttäjä

- Käyttäjätunnus: `admin`
- Salasana: `admin123`

Vaihda testitunnukset ennen oikeaa käyttöönottoa. Esimerkkitunnus luodaan käynnistyksessä vain, jos käyttäjää ei vielä ole tietokannassa.

## H2-yhteys

H2 Console -kirjautumisen tiedot:

- JDBC URL: `jdbc:h2:file:./data/moviemanager`
- User Name: `sa`
- Password: (tyhjä)

Tietokanta tallentuu projektin `data`-hakemistoon. Älä poista hakemistoa, jos haluat säilyttää tiedot.

## Toteutetut vaatimukset

- MVC + Thymeleaf-näkymät
- JPA-entiteetit ja relaatiot (`Elokuva` → `Lajityyppi`, `Sovelluskayttaja`)
- H2-tietokanta
- Spring Security: julkinen katselu, ADMIN-roolin suojaama ylläpito
- Bean Validation lomakkeissa
- REST API JSON-muodossa
- JUnit 5 -testit
- JPA-hakutoiminnot (nimi/ohjaaja ja lajityyppi)
