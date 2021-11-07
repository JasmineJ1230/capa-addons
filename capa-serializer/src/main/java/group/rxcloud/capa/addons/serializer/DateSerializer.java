package group.rxcloud.capa.addons.serializer;

import java.util.GregorianCalendar;

public interface DateSerializer {

    boolean isValid(String date);

    String serialize(GregorianCalendar calendar);

    GregorianCalendar deserialize(String date);
}
