package group.rxcloud.capa.addons.serializer;

import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public final class TimeZoneGetter {

    private static final int SIZE_LIMITATION = 1000;

    private TimeZoneGetter() {
    }

    public static final TimeZoneGetter INSTANCE = new TimeZoneGetter();

    private final ConcurrentHashMap<String, TimeZone> timeZonesMap = new ConcurrentHashMap<>();

    public TimeZone getTimeZone(final String id) {
        if (timeZonesMap.size() >= SIZE_LIMITATION && !timeZonesMap.containsKey(id)) {
            return TimeZone.getTimeZone(id);
        }

        return timeZonesMap.computeIfAbsent(id, TimeZone::getTimeZone);
    }
}
