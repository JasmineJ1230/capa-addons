package group.rxcloud.capa.addons.serialzer;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;

public interface TypeReferenceDeserializer {

    <T> T deserialize(String s, TypeReference<T> typeReference);

    <T> T deserialize(InputStream is, TypeReference<T> typeReference);
}
