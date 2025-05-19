package com.geoip.iplookup.controller;

import com.geoip.iplookup.dto.GeoIPResponse;
import com.geoip.iplookup.service.GeoIPService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/geoip")
public class GeoIPController {

    private final GeoIPService geoIPService;

    public GeoIPController(GeoIPService geoIPService) {
        this.geoIPService = geoIPService;
    }

    @GetMapping
    public GeoIPResponse lookup(@RequestParam String ip) {
        return geoIPService.lookup(ip);
    }
}
