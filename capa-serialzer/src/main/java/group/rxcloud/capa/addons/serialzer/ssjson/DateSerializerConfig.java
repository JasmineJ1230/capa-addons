package group.rxcloud.capa.addons.serialzer.ssjson;


import group.rxcloud.capa.addons.serialzer.DateSerializer;

import java.util.List;

public interface DateSerializerConfig {

    void setCalendarSerializer(DateSerializer calendarSerializer);

    DateSerializer getCalendarSerializer();

    void setCalendarDeserializers(List<DateSerializer> calendarDeserializers);

    List<DateSerializer> getCalendarDeserializers();
}
