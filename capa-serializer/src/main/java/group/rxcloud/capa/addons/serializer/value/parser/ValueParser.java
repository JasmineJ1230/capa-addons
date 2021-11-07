package group.rxcloud.capa.addons.serializer.value.parser;

public interface ValueParser<T> {

    T parse(String value);

}
