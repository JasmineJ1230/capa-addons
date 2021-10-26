package group.rxcloud.capa.addons.serialzer.date;

import group.rxcloud.capa.addons.serialzer.value.DateValues;

public class ShortDateSerializer extends AbstractDateSerializer {

    public static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    public static final ShortDateSerializer INSTANCE = new ShortDateSerializer();

    public ShortDateSerializer() {
        super(DateValues.SHORT_DATE_FORMAT, DATE_PATTERN, null);
    }
}
