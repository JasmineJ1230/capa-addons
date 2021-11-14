package group.rxcloud.capa.addons.serializer.baiji;

public interface StringSerializer extends Serializer {

    String serialize(Object obj);

    <T> T deserialize(String s, Class<T> clazz);
}
