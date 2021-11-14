package group.rxcloud.capa.addons.serializer.baiji.date;

import com.google.common.base.Strings;
import group.rxcloud.capa.addons.serializer.baiji.value.DateValues;
import group.rxcloud.capa.addons.serializer.baiji.value.parser.StandardDateFormatTimeZoneParser;

import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandardDateSerializer extends AbstractDateSerializer {

    public static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{1,3}(((-|\\+)\\d{2}:\\d{2})|Z)";
    private static final String NOT_NORMALIZED_MILLISECONDS_DATE_PATTERN = "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.)(\\d{1,2})(((-|\\+)\\d{2}:\\d{2})|Z)";

    public static final StandardDateSerializer INSTANCE = new StandardDateSerializer();

    private Pattern _notNormalizedMillisecondsDatePattern;

    private StandardDateSerializer() {
        super(DateValues.STANDARD_DATE_FORMAT, DATE_PATTERN, StandardDateFormatTimeZoneParser.INSTANCE);
        _notNormalizedMillisecondsDatePattern = Pattern.compile(NOT_NORMALIZED_MILLISECONDS_DATE_PATTERN);
    }

    @Override
    public GregorianCalendar deserialize(String date) {
        return super.deserialize(normalizeDateWithMilliseconds(date));
    }

    private String normalizeDateWithMilliseconds(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return date;
        }
        Matcher matcher = _notNormalizedMillisecondsDatePattern.matcher(date);
        if (!matcher.matches()) {
            return date;
        }

        return String.format("%s%s%s", matcher.group(1), Strings.padStart(matcher.group(2), 3, '0'), matcher.group(3));
    }
}