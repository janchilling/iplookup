package com.geoip.iplookup.dto;

public class GeoIPResponse {
    private String country;
    private String city;
    private String timezone;
    private String continent;
    private Double latitude;
    private Double longitude;

    public GeoIPResponse(String country, String city, String timezone,
                         String continent, Double latitude, Double longitude) {
        this.country = country;
        this.city = city;
        this.timezone = timezone;
        this.continent = continent;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getContinent() {
        return continent;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}