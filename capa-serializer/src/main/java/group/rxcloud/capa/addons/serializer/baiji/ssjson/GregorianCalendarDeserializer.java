package group.rxcloud.capa.addons.serializer.baiji.ssjson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import group.rxcloud.capa.addons.serializer.baiji.DateSerializer;
import group.rxcloud.capa.addons.serializer.baiji.date.JodaDateSerializer;
import group.rxcloud.capa.addons.serializer.baiji.value.StringValues;
import group.rxcloud.capa.addons.serializer.baiji.value.checker.CollectionArgumentChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class GregorianCalendarDeserializer extends StdDeserializer<GregorianCalendar> {

    private static final long serialVersionUID = 1L;

    private List<DateSerializer> _serializers = new ArrayList<>();

    private List<DateSerializer> _jodaSerializers = new ArrayList<>();

    public GregorianCalendarDeserializer(List<DateSerializer> serializers) {
        super(Calendar.class);

        CollectionArgumentChecker.DEFAULT.check(serializers, "serializers");

        for (DateSerializer dateSerializer : serializers) {
            if (dateSerializer instanceof JodaDateSerializer)
                _jodaSerializers.add(dateSerializer);
            else
                _serializers.add(dateSerializer);
        }
    }

    @Override
    public GregorianCalendar deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String date = p.readValueAs(String.class);
        if (StringValues.isNullOrWhitespace(date))
            return null;

        for (DateSerializer serializer : _serializers) {
            if (serializer.isValid(date))
                return serializer.deserialize(date);
        }

        for (DateSerializer serializer : _jodaSerializers) {
            try {
                return serializer.deserialize(date);
            } catch (Exception e) {
                // ignore it
            }
        }

        throw new JsonParseException(p, String.format("date value cannot be deserialized: %s", date));
    }
}
