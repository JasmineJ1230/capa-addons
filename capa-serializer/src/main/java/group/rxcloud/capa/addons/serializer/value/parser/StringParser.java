package group.rxcloud.capa.addons.serializer.value.parser;

public class StringParser implements ValueParser<String> {

    public static final StringParser DEFAULT = new StringParser();

    @Override
    public String parse(String value) {
        return value;
    }

}
