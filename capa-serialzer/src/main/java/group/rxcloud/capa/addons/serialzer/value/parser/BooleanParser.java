package group.rxcloud.capa.addons.serialzer.value.parser;

import group.rxcloud.capa.addons.serialzer.value.StringValues;

public class BooleanParser implements ValueParser<Boolean> {

    public static final BooleanParser DEFAULT = new BooleanParser();

    @Override
    public Boolean parse(String value) {
        if (StringValues.isNullOrWhitespace(value))
            return null;

        return Boolean.parseBoolean(value.trim());
    }

}
