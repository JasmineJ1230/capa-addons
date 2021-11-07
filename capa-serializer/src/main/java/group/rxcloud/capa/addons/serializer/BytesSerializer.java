package group.rxcloud.capa.addons.serializer;

public interface BytesSerializer extends Serializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] is, Class<T> clazz);
}
