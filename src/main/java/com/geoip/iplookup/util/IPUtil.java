package com.geoip.iplookup.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.math.BigInteger;

public class IPUtil {

    public static InetAddress parseIP(String ipStr) {
        try {
            return InetAddress.getByName(ipStr);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Invalid IP: " + ipStr);
        }
    }

    public static BigInteger toBigInteger(InetAddress ip) {
        return new BigInteger(1, ip.getAddress());
    }
}
