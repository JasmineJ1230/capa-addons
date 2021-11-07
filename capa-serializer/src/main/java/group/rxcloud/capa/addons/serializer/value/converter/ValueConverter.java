package group.rxcloud.capa.addons.serializer.value.converter;

public interface ValueConverter<S, D> {

    D convert(S source);

}
