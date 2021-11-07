package group.rxcloud.capa.addons.serializer.date;

import group.rxcloud.capa.addons.serializer.DateSerializer;
import group.rxcloud.capa.addons.serializer.value.DateValues;
import group.rxcloud.capa.addons.serializer.value.StringValues;
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