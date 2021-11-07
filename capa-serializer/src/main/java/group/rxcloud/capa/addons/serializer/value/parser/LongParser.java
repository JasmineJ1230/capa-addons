package group.rxcloud.capa.addons.serializer.value.parser;

import group.rxcloud.capa.addons.serializer.value.StringValues;

public class LongParser implements ValueParser<Long> {

    public static final LongParser DEFAULT = new LongParser();

    @Override
    public Long parse(String value) {
        if (StringValues.isNullOrWhitespace(value))
            return null;

        return Long.parseLong(value.trim());
    }

}
