package com.geoip.iplookup.model;

import com.geoip.iplookup.util.IPUtil;

import java.math.BigInteger;
import java.net.InetAddress;


public class GeoIPRange implements Comparable<GeoIPRange> {
    private final InetAddress start;
    private final InetAddress end;
    private final String country;

    public GeoIPRange(InetAddress start, InetAddress end, String country) {
        this.start = start;
        this.end = end;
        this.country = country;
    }

    public boolean contains(InetAddress ip) {
        BigInteger ipVal = IPUtil.toBigInteger(ip);
        BigInteger startVal = IPUtil.toBigInteger(start);
        BigInteger endVal = IPUtil.toBigInteger(end);

        return (ipVal.compareTo(startVal) >= 0 && ipVal.compareTo(endVal) <= 0);
    }

    @Override
    public int compareTo(GeoIPRange o) {
        return IPUtil.toBigInteger(this.start).compareTo(IPUtil.toBigInteger(o.start));
    }

    public String getCountry() {
        return country;
    }

    public InetAddress getStart() {
        return start;
    }
}
