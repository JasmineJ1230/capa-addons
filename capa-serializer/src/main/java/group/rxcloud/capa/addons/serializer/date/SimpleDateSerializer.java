package group.rxcloud.capa.addons.serializer.date;

import group.rxcloud.capa.addons.serializer.value.DateValues;

public class SimpleDateSerializer extends AbstractDateSerializer {

    public static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}{2}:\\d{2}{2}:\\d{2}{2}";

    public static final SimpleDateSerializer INSTANCE = new SimpleDateSerializer();

    private SimpleDateSerializer() {
        super(DateValues.SIMPLE_DATE_FORMAT, DATE_PATTERN, null);
    }

}
