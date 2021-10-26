package group.rxcloud.capa.addons.serialzer.value.parser;

public interface ValueParser<T> {

    T parse(String value);

}
