package group.rxcloud.capa.addons.serializer.value.checker;

import group.rxcloud.capa.addons.serializer.value.StringValues;

public class StringArgumentChecker implements ValueChecker<String> {

    public static final StringArgumentChecker DEFAULT = new StringArgumentChecker();

    @Override
    public void check(String value, String valueName) {
        if (StringValues.isNullOrWhitespace(value))
            throw new IllegalArgumentException("argument " + valueName + " is null or whitespace");
    }

}
