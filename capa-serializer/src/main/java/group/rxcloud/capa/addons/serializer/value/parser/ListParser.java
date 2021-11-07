package group.rxcloud.capa.addons.serializer.value.parser;

import group.rxcloud.capa.addons.serializer.value.StringValues;

import java.util.ArrayList;
import java.util.List;

public class ListParser implements ValueParser<List<String>> {

    public static final ListParser DEFAULT = new ListParser();

    @Override
    public List<String> parse(String value) {
        if (StringValues.isNullOrWhitespace(value))
            return null;

        List<String> list = new ArrayList<String>();
        String[] array = value.trim().split(",");
        for (String str : array) {
            if (StringValues.isNullOrWhitespace(str))
                continue;

            list.add(str.trim());
        }

        return list.size() == 0 ? null : list;
    }

}
