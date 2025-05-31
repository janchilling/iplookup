package com.geoip.iplookup.dto;

public class GeoCityResponse {

    private String city;
    private String country;
    private String iso2;
    private String iso3;
    private Double latitude;
    private Double longitude;
    private String continent;

    public GeoCityResponse(String city, String country, String iso2, String iso3,
                           Double latitude, Double longitude, String continent) {
        this.city = city;
        this.country = country;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.latitude = latitude;
        this.longitude = longitude;
        this.continent = continent;
    }

    // Getters and setters (or use Lombok if preferred)
}
