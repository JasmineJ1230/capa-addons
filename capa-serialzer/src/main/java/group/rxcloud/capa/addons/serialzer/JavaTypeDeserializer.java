package group.rxcloud.capa.addons.serialzer;

import com.fasterxml.jackson.databind.JavaType;

import java.io.InputStream;

public interface JavaTypeDeserializer {

    <T> T deserialize(String s, JavaType javaType);

    <T> T deserialize(InputStream is, JavaType javaType);
}
