package com.geoip.iplookup.service;

import com.geoip.iplookup.dto.GeoIPResponse;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.InetAddress;

@Service
public class GeoIPService {

    private DatabaseReader dbReader;

    @PostConstruct
    public void init() throws Exception {
        InputStream dbStream = getClass().getResourceAsStream("/GeoLite2-City.mmdb");
        dbReader = new DatabaseReader.Builder(dbStream).build();
    }

    public GeoIPResponse lookup(String ip) {
        try {
            Thread.sleep(20);

            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = dbReader.city(ipAddress);

            String country = response.getCountry().getIsoCode();
            String city = response.getCity().getName();
            String timezone = response.getLocation().getTimeZone();
            String continent = response.getContinent().getName();
            Double latitude = response.getLocation().getLatitude();
            Double longitude = response.getLocation().getLongitude();

            return new GeoIPResponse(
                    country != null ? country : "Unknown",
                    city != null ? city : "Unknown",
                    timezone != null ? timezone : "Unknown",
                    continent != null ? continent : "Unknown",
                    latitude,
                    longitude
            );

        } catch (GeoIp2Exception e) {
            return new GeoIPResponse("Not found", "", "", "", null, null);
        } catch (Exception e) {
            return new GeoIPResponse("Error", "", "", "", null, null);
        }
    }
}