package group.rxcloud.capa.addons.serializer.baiji.ssjson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import group.rxcloud.capa.addons.serializer.baiji.DateSerializer;
import group.rxcloud.capa.addons.serializer.baiji.value.checker.NullArgumentChecker;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;

public class XMLGregorianCalendarSerializer extends StdSerializer<XMLGregorianCalendar> {

    private static final long serialVersionUID = 1L;

    private DateSerializer _serializer;

    public XMLGregorianCalendarSerializer(DateSerializer serializer) {
        super(XMLGregorianCalendar.class);

        NullArgumentChecker.DEFAULT.check(serializer, "serializer");
        _serializer = serializer;
    }

    @Override
    @SuppressWarnings("all")
    public void serialize(XMLGregorianCalendar value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        gen.writeString(_serializer.serialize(value.toGregorianCalendar()));
    }

}

