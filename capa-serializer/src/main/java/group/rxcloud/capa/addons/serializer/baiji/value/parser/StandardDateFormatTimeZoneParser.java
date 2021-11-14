package group.rxcloud.capa.addons.serializer.baiji.value.parser;

import group.rxcloud.capa.addons.serializer.baiji.TimeZoneGetter;
import group.rxcloud.capa.addons.serializer.baiji.value.StringValues;

import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandardDateFormatTimeZoneParser implements ValueParser<TimeZone> {

    private static final String DEFAULT_TIME_ZONE_FORMAT = "((\\+|-)\\d{2}:\\d{2})|Z";
    private static final Pattern DEFAULT_TIME_ZONE_PATTERN = Pattern.compile(DEFAULT_TIME_ZONE_FORMAT);
    private static final int VALUE_LENGTH = "yyyy-MM-ddTHH:mm:ss.SSS".length();

    public static final StandardDateFormatTimeZoneParser INSTANCE = new StandardDateFormatTimeZoneParser();

    private StandardDateFormatTimeZoneParser() {

    }

    @Override
    public TimeZone parse(String value) {
        if (StringValues.isNullOrWhitespace(value))
            return TimeZone.getDefault();

        if (value.length() <= VALUE_LENGTH)
            return TimeZone.getDefault();

        value = value.substring(VALUE_LENGTH);

        Matcher matcher = DEFAULT_TIME_ZONE_PATTERN.matcher(value);
        if (!matcher.matches())
            return TimeZoneGetter.INSTANCE.getTimeZone(value);

        if (value.equals("Z") || value.equals("+00:00") || value.equals("-00:00"))
            return TimeZoneGetter.INSTANCE.getTimeZone("UTC");

        return TimeZoneGetter.INSTANCE.getTimeZone("GMT" + value);
    }

}
