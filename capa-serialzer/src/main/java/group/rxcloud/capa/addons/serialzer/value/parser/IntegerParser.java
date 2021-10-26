package group.rxcloud.capa.addons.serialzer.value.parser;

import group.rxcloud.capa.addons.serialzer.value.StringValues;

public class IntegerParser implements ValueParser<Integer> {

    public static final IntegerParser DEFAULT = new IntegerParser();

    @Override
    public Integer parse(String value) {
        if (StringValues.isNullOrWhitespace(value))
            return null;

        return Integer.parseInt(value.trim());
    }

}
