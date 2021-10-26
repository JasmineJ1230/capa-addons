package group.rxcloud.capa.addons.serialzer.value.parser;


import group.rxcloud.capa.addons.serialzer.collect.KeyValuePair;
import group.rxcloud.capa.addons.serialzer.value.StringValues;

import java.util.HashMap;
import java.util.Map;

public class MapParser implements ValueParser<Map<String, String>> {

    public static final MapParser DEFAULT = new MapParser();

    @Override
    public Map<String, String> parse(String value) {
        if (StringValues.isNullOrWhitespace(value))
            return null;

        Map<String, String> map = new HashMap<String, String>();
        String[] pairValues = value.trim().split(",");
        for (String pairValue : pairValues) {
            KeyValuePair<String, String> pair = StringValues.toKeyValuePair(pairValue);
            if (pair == null)
                continue;

            map.put(pair.getKey(), pair.getValue());
        }

        return map.size() == 0 ? null : map;
    }

}
