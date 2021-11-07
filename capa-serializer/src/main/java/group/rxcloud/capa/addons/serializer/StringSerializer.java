package group.rxcloud.capa.addons.serializer;

public interface StringSerializer extends Serializer {

    String serialize(Object obj);

    <T> T deserialize(String s, Class<T> clazz);
}
