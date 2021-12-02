package group.rxcloud.capa.addons.id.generator;

import group.rxcloud.capa.addons.foundation.CapaFoundation;
import group.rxcloud.capa.addons.foundation.FoundationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Generate trace log id for aws trip application.
 */
public final class TripTraceIdGeneratePolicy {

    private static final Logger log = LoggerFactory.getLogger(TripTraceIdGeneratePolicy.class);

    private static final double FACTOR = Math.pow(10, 10);

    private static final char SEPARATOR = '-';

    private static final String UNKNOWN = "UNKNOWN";

    private static final NumberFormat NUMBER_FORMAT = new DecimalFormat();

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(0);
        NUMBER_FORMAT.setMinimumIntegerDigits(10);
        NUMBER_FORMAT.setGroupingUsed(false);
    }

    private TripTraceIdGeneratePolicy() {
    }

    public static String generate() {
        String appId = getAppId();
        String ip = getIp();
        if (ip != null && ip.contains(".")) {
            String[] v = ip.split("\\.");
            StringBuilder ipBuilder = new StringBuilder();
            for (String s : v) {
                String hex = Integer.toHexString(Integer.parseInt(s));
                if (hex.length() == 1) {
                    ipBuilder.append('0');
                }
                ipBuilder.append(hex);
            }
            ip = ipBuilder.toString();
        }
        long second = System.currentTimeMillis();
        return appId + SEPARATOR + ip + SEPARATOR + second + SEPARATOR + NUMBER_FORMAT.format(Math.random() * FACTOR);
    }

    private static String getAppId() {
        try {
            String appId = CapaFoundation.getAppId(FoundationType.TRIP);
            if (appId != null && !appId.isEmpty()) {
                return appId;
            }
        } catch (Throwable e) {
            log.warn("Fail to get appId.", e);
        }
        return UNKNOWN;
    }

    private static String getIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress address = inetAddresses.nextElement();
                   if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                       return address.getHostAddress();
                   }
                }
            }
        } catch (SocketException e) {
            log.warn("Fail to get ip address.", e);
        }
        return UNKNOWN;
    }

}
