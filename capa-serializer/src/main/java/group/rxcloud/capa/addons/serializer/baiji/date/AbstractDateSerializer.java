package group.rxcloud.capa.addons.serializer.baiji.date;

import group.rxcloud.capa.addons.serializer.baiji.DateSerializer;
import group.rxcloud.capa.addons.serializer.baiji.value.DateValues;
import group.rxcloud.capa.addons.serializer.baiji.value.StringValues;
import group.rxcloud.capa.addons.serializer.baiji.value.checker.StringArgumentChecker;
import group.rxcloud.capa.addons.serializer.baiji.value.parser.ValueParser;

import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;

public abstract class AbstractDateSerializer implements DateSerializer {

    private String _dateFormat;
    private ValueParser<TimeZone> _timeZoneParser;
    private Pattern _pattern;

    public AbstractDateSerializer(String dateFormat, String datePattern, ValueParser<TimeZone> timeZoneParser) {
        StringArgumentChecker.DEFAULT.check(dateFormat, "dateFormat");

        _dateFormat = dateFormat;
        _pattern = StringValues.isNullOrWhitespace(datePattern) ? null : Pattern.compile(datePattern);
        _timeZoneParser = timeZoneParser;
    }

    @Override
    public boolean isValid(String date) {
        if (_pattern == null)
            return true;

        return _pattern.matcher(date).matches();
    }

    /**
     * It can be override by children class
     * @param date
     * @return
     */
    protected String getDeserializeDateFormat(final String date) {
        return _dateFormat;
    }

    @Override
    public String serialize(GregorianCalendar calendar) {
        return DateValues.toString(calendar, _dateFormat);
    }

    @Override
    public GregorianCalendar deserialize(String date) {
        return DateValues.parseCalendar(date, getDeserializeDateFormat(date), _timeZoneParser);
    }
}