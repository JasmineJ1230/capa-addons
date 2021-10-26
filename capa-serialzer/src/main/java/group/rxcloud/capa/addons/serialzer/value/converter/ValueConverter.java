package group.rxcloud.capa.addons.serialzer.value.converter;

public interface ValueConverter<S, D> {

    D convert(S source);

}
