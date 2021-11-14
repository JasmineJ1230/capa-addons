package group.rxcloud.capa.addons.serializer.baiji.value.converter;

public interface ValueConverter<S, D> {

    D convert(S source);

}
