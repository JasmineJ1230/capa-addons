package group.rxcloud.capa.addons.serializer.value.converter;

import java.util.HashMap;
import java.util.Map;

public class MapValueConverter<K, S, D> implements ValueConverter<Map<K, S>, Map<K, D>> {

    private ValueConverter<S, D> _valueConverter;

    public MapValueConverter(ValueConverter<S, D> valueConverter) {
        _valueConverter = valueConverter;
    }

    @Override
    public Map<K, D> convert(Map<K, S> source) {
        if (source == null)
            return null;

        Map<K, D> result = new HashMap<>();
        for (Map.Entry<K, S> item : source.entrySet()) {
            result.put(item.getKey(), _valueConverter.convert(item.getValue()));
        }

        return result;
    }

}
