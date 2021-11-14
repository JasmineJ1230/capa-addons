package group.rxcloud.capa.addons.serializer.baiji.ssjson;


import group.rxcloud.capa.addons.serializer.baiji.DateSerializer;

import java.util.List;

public interface DateSerializerConfig {

    void setCalendarSerializer(DateSerializer calendarSerializer);

    DateSerializer getCalendarSerializer();

    void setCalendarDeserializers(List<DateSerializer> calendarDeserializers);

    List<DateSerializer> getCalendarDeserializers();
}
