package com.geoip.iplookup.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class CityGeoEnricher {

    static class GeoInfo {
        String country;
        double latitude;
        double longitude;

        public GeoInfo(String country, double latitude, double longitude) {
            this.country = country;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    public static void main(String[] args) throws Exception {
        String inputFeedback = "customer_feedback.csv";
        String cityGeoFile = "geonames_cities.csv";
        String outputFile = "enriched_feedback.csv";

        Map<String, GeoInfo> cityGeoMap = loadCityGeoMap(cityGeoFile);
        enrichFeedback(inputFeedback, outputFile, cityGeoMap);
        System.out.println("Enrichment complete. Output written to: " + outputFile);
    }

    private static Map<String, GeoInfo> loadCityGeoMap(String cityCsvFile) throws Exception {
        Map<String, GeoInfo> map = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(cityCsvFile))) {
            String[] header = reader.readNext(); // Skip header
            String[] row;

            while ((row = reader.readNext()) != null) {
                if (row.length < 5) continue;
                String city = row[0].trim().toLowerCase(); // city
                String country = row[4].trim();            // country
                double lat = Double.parseDouble(row[2]);   // lat
                double lng = Double.parseDouble(row[3]);   // lng

                map.put(city, new GeoInfo(country, lat, lng));
            }
        }
        return map;
    }

    private static void enrichFeedback(String inputCsv, String outputCsv, Map<String, GeoInfo> geoMap) throws Exception {
        try (
                CSVReader reader = new CSVReader(new FileReader(inputCsv));
                CSVWriter writer = new CSVWriter(new FileWriter(outputCsv))
        ) {
            String[] header = reader.readNext();
            List<String> newHeader = new ArrayList<>(Arrays.asList(header));
            newHeader.addAll(List.of("country", "latitude", "longitude"));
            writer.writeNext(newHeader.toArray(new String[0]));

            String[] row;
            while ((row = reader.readNext()) != null) {
                Map<String, String> rowMap = toMap(header, row);
                String location = rowMap.get("location").toLowerCase();
                GeoInfo geo = geoMap.get(location);

                List<String> enrichedRow = new ArrayList<>(Arrays.asList(row));
                if (geo != null) {
                    enrichedRow.addAll(List.of(geo.country, String.valueOf(geo.latitude), String.valueOf(geo.longitude)));
                } else {
                    enrichedRow.addAll(List.of("Unknown", "", ""));
                }

                writer.writeNext(enrichedRow.toArray(new String[0]));
            }
        }
    }

    private static Map<String, String> toMap(String[] headers, String[] row) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < headers.length && i < row.length; i++) {
            map.put(headers[i].trim(), row[i].trim());
        }
        return map;
    }
}
