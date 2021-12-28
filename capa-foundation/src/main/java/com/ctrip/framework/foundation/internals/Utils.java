package com.ctrip.framework.foundation.internals;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class Utils {
    private static final Pattern PID_PATTERN = Pattern.compile("^([0-9]+)@.+$", Pattern.CASE_INSENSITIVE);

    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        int length = str.length();
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);

            if (!Character.isWhitespace(ch)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOSWindows() {
        String osName = System.getProperty("os.name");
        if (Utils.isBlank(osName)) {
            return false;
        }
        return osName.startsWith("Windows");
    }

    public static boolean isOSMac() {
        String osName = System.getProperty("os.name");
        if (Utils.isBlank(osName)) {
            return false;
        }
        return osName.startsWith("Mac OS");
    }

    public static int getPid() {
        try {
            RuntimeMXBean rtb = ManagementFactory.getRuntimeMXBean();
            String processName = rtb.getName();
            Matcher matcher = PID_PATTERN.matcher(processName);
            if (matcher.matches()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Throwable ex) {
            //ignore
        }
        return 0;
    }
}
