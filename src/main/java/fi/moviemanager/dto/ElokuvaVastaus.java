package fi.moviemanager.dto;

public record ElokuvaVastaus(
        Long tunnus,
        String nimi,
        Integer julkaisuvuosi,
        String ohjaaja,
        String kuvaus,
        String lajityyppi
) {}
