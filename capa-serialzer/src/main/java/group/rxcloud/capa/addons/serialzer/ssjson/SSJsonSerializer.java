package group.rxcloud.capa.addons.serialzer.ssjson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import group.rxcloud.capa.addons.serialzer.JavaTypeDeserializer;
import group.rxcloud.capa.addons.serialzer.StreamSerializer;
import group.rxcloud.capa.addons.serialzer.StringSerializer;
import group.rxcloud.capa.addons.serialzer.TypeReferenceDeserializer;
import group.rxcloud.capa.addons.serialzer.exception.SerializationException;
import group.rxcloud.capa.addons.serialzer.value.CollectionValues;
import group.rxcloud.capa.addons.serialzer.value.XMLValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SSJsonSerializer implements StreamSerializer, StringSerializer, TypeReferenceDeserializer, JavaTypeDeserializer {

    public static final SSJsonSerializer DEFAULT = new SSJsonSerializer();

    private ObjectMapper _mapper = new ObjectMapper();
    private SSJsonSerializerConfig _config;
    private Logger logger = LoggerFactory.getLogger(SSJsonSerializer.class);

    public SSJsonSerializer() {
        this(null);
    }

    public SSJsonSerializer(SSJsonSerializerConfig config) {
        _config = config;

        setDefaultConfigValues();
        registerCustomModule();
        configMapper();
    }

    @Override
    public String contentType() {
        return "application/json";
    }

    @Override
    public void serialize(OutputStream os, Object obj) {
        try {
            _mapper.writeValue(os, obj);
        } catch (RuntimeException | Error ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @Override
    public <T> T deserialize(InputStream is, Class<T> clazz) {
        try {
            return _mapper.readValue(is, clazz);
        } catch (RuntimeException | Error ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        } finally {
            closeInputStream(is);
        }
    }

    @Override
    public String serialize(Object obj) {
        try {
            return _mapper.writeValueAsString(obj);
        } catch (RuntimeException | Error ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @Override
    public <T> T deserialize(String s, Class<T> clazz) {
        try {
            return _mapper.readValue(s, clazz);
        } catch (RuntimeException | Error ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @Override
    public <T> T deserialize(String s, JavaType javaType) {
        try {
            return _mapper.readValue(s, javaType);
        } catch (RuntimeException | Error ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @Override
    public <T> T deserialize(InputStream is, JavaType javaType) {
        try {
            return _mapper.readValue(is, javaType);
        } catch (RuntimeException | Error ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        } finally {
            closeInputStream(is);
        }
    }

    @Override
    public <T> T deserialize(String s, TypeReference<T> typeReference) {
        try {
            return _mapper.readValue(s, typeReference);
        } catch (RuntimeException | Error ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @Override
    public <T> T deserialize(InputStream is, TypeReference<T> typeReference) {
        try {
            return _mapper.readValue(is, typeReference);
        } catch (RuntimeException | Error ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SerializationException(ex);
        } finally {
            closeInputStream(is);
        }
    }

    private void closeInputStream(InputStream is) {
        if (is != null && _config.isAutoCloseInput()) {
            try {
                is.close();
            } catch (IOException e) {
                logger.warn("Error occurred while closing InputStream.", e);
            }
        }
    }

    private void setDefaultConfigValues() {
        if (_config == null)
            _config = SSJsonSerializerConfig.createDefault();

        if (_config.getCalendarSerializer() == null)
            _config.setCalendarSerializer(SSJsonSerializerConfig.DEFAULT_CALENDAR_SERIALIZER);

        if (CollectionValues.isNullOrEmpty(_config.getCalendarDeserializers()))
            _config.setCalendarDeserializers(new ArrayList<>(SSJsonSerializerConfig.DEFAULT_CALENDAR_DESERIALIZERS));
    }

    private void registerCustomModule() {
        SSJsonSimpleModule module = new SSJsonSimpleModule();
        registerAbstractTypeMappings(module);
        registerSerializers(module);

        ModuleConfigurator moduleConfigurator = _config.getModuleConfigurator();
        if (moduleConfigurator != null) {
            moduleConfigurator.configure(module);
        }

        SSJsonModuleConfigurator ssJsonModuleConfigurator = _config.getSSJsonModuleConfigurator();
        if (ssJsonModuleConfigurator != null) {
            ssJsonModuleConfigurator.configure(module);
        }

        _mapper.registerModule(module);
    }

    private void configMapper() {
        _mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        _mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        _mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        _mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        _mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        // FIXME unknowd usege _mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        _mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        _mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        _mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        _mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        _mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        if (null != _config && _config.isIncludeNonNullValues()) {
            _mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    }

    private void registerAbstractTypeMappings(SimpleModule module) {
        module.addAbstractTypeMapping(XMLGregorianCalendar.class, XMLValues.xmlGregorianCalendarType());
        module.addAbstractTypeMapping(Calendar.class, GregorianCalendar.class);
    }

    private void registerSerializers(SimpleModule module) {
        registerCalendarSerializer(module);
        registerBigNumberSerializer(module);
    }

    private void registerCalendarSerializer(SimpleModule module) {
        module.addSerializer(GregorianCalendar.class, new GregorianCalendarSerializer(_config.getCalendarSerializer(), _config.isKeepDateSerializeTimeZone()));
        module.addDeserializer(GregorianCalendar.class, new GregorianCalendarDeserializer(_config.getCalendarDeserializers()));
        module.addSerializer(XMLValues.xmlGregorianCalendarType(), new XMLGregorianCalendarSerializer(_config.getCalendarSerializer()));
        module.addDeserializer(XMLValues.xmlGregorianCalendarType(), new XMLGregorianCalendarDeserializer(_config.getCalendarDeserializers()));
    }

    private void registerBigNumberSerializer(SimpleModule module) {
        if (_config.isBigNumberCheckEnabled()) {
            module.addDeserializer(BigDecimal.class, new BigDecimalDeserializerWrapper(_config.getBigNumberMaxLength(), _config.getBigDecimalMaxSignificandLength(), _config.getBigDecimalMaxExponentLength()));
            module.addDeserializer(BigInteger.class, new BigIntegerDeserializerWrapper(_config.getBigNumberMaxLength()));
        }
    }
}
