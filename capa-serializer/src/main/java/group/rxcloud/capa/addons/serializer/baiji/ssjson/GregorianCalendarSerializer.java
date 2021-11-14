package group.rxcloud.capa.addons.serializer.baiji.ssjson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import group.rxcloud.capa.addons.serializer.baiji.DateSerializer;
import group.rxcloud.capa.addons.serializer.baiji.value.DateValues;
import group.rxcloud.capa.addons.serializer.baiji.value.checker.NullArgumentChecker;

import java.io.IOException;
import java.util.GregorianCalendar;

public class GregorianCalendarSerializer extends StdSerializer<GregorianCalendar> {

    private static final long serialVersionUID = 1L;

    private DateSerializer _serializer;
    private boolean keepDateSerializeTimeZone;

    public GregorianCalendarSerializer(DateSerializer serializer) {
        this(serializer, false);
    }

    public GregorianCalendarSerializer(DateSerializer serializer, boolean keepDateSerializeTimeZone) {
        super(GregorianCalendar.class);

        NullArgumentChecker.DEFAULT.check(serializer, "serializer");
        _serializer = serializer;
        this.keepDateSerializeTimeZone = keepDateSerializeTimeZone;
    }

    @Override
    @SuppressWarnings("all")
    public void serialize(GregorianCalendar value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        if (keepDateSerializeTimeZone) {
            gen.writeString(_serializer.serialize(value));
        } else {
            gen.writeString(_serializer.serialize(DateValues.toGregorianCalendar(value.getTime())));
        }
    }
}