package group.rxcloud.capa.addons.serializer.date;

import group.rxcloud.capa.addons.serializer.value.DateValues;

public class StandardSimpleDateSerializer extends AbstractDateSerializer {

    public static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}{2}:\\d{2}{2}:\\d{2}{2}";

    public static final StandardSimpleDateSerializer INSTANCE = new StandardSimpleDateSerializer();

    public StandardSimpleDateSerializer() {
        super(DateValues.STANDARD_SIMPLE_DATE_FORMAT, DATE_PATTERN, null);
    }
}
