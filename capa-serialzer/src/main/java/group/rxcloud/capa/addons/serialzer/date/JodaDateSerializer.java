package group.rxcloud.capa.addons.serialzer.date;

import group.rxcloud.capa.addons.serialzer.DateSerializer;
import group.rxcloud.capa.addons.serialzer.value.DateValues;
import group.rxcloud.capa.addons.serialzer.value.StringValues;
import org.joda.time.DateTime;

import java.util.GregorianCalendar;

public class JodaDateSerializer implements DateSerializer {

    public static final JodaDateSerializer INSTANCE = new JodaDateSerializer();

    @Override
    public boolean isValid(String date) {
        if (StringValues.isNullOrWhitespace(date))
            return false;

        try {
            this.deserialize(date);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    @Override
    public String serialize(GregorianCalendar calendar) {
        return DateValues.toString(calendar, DateValues.STANDARD_DATE_FORMAT);
    }

    @Override
    public GregorianCalendar deserialize(String date) {
        return DateTime.parse(date).toGregorianCalendar();
    }
}