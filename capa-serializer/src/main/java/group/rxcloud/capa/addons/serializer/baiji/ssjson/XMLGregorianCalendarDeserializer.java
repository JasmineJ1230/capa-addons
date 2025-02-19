package group.rxcloud.capa.addons.serializer.baiji.ssjson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import group.rxcloud.capa.addons.serializer.baiji.DateSerializer;
import group.rxcloud.capa.addons.serializer.baiji.value.StringValues;
import group.rxcloud.capa.addons.serializer.baiji.value.XMLValues;
import group.rxcloud.capa.addons.serializer.baiji.value.checker.CollectionArgumentChecker;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLGregorianCalendarDeserializer extends StdDeserializer<XMLGregorianCalendar> {

    private static final long serialVersionUID = 1L;

    private List<DateSerializer> _serializers = new ArrayList<>();

    public XMLGregorianCalendarDeserializer(List<DateSerializer> serializers) {
        super(XMLGregorianCalendar.class);

        CollectionArgumentChecker.DEFAULT.check(serializers, "serializers");
        _serializers.addAll(serializers);
    }

    @Override
    public XMLGregorianCalendar deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String date = p.readValueAs(String.class);
        if (StringValues.isNullOrWhitespace(date))
            return null;

        for (DateSerializer serializer : _serializers) {
            if (serializer.isValid(date))
                return XMLValues.toXMLGregorianCalendar(serializer.deserialize(date));
        }

        throw new JsonParseException(p, "date value cannot be deserialized: " + date);
    }

}
