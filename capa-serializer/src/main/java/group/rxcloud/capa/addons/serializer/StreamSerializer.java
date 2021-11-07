package group.rxcloud.capa.addons.serializer;

import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer extends Serializer {

    void serialize(OutputStream os, Object obj);

    <T> T deserialize(InputStream is, Class<T> clazz);
}
