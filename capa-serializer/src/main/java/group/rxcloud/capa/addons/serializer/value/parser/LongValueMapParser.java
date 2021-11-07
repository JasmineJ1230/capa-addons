package group.rxcloud.capa.addons.serializer.value.parser;

import group.rxcloud.capa.addons.serializer.value.converter.MapValueConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LongValueMapParser implements ValueParser<Map<String, Long>> {

    public static final LongValueMapParser DEFAULT = new LongValueMapParser();

    private static final Logger _logger = LoggerFactory.getLogger(LongValueMapParser.class);

    private static final MapValueConverter<String, String, Long> _mapValueConverter = new MapValueConverter<>(
            source -> {
                try {
                    return Long.parseLong(source);
                } catch (Exception ex) {
                    _logger.warn("parse long value failed. long value: {}", source);
                    return null;
                }
            });

    @Override
    public Map<String, Long> parse(String value) {
        Map<String, String> mapValue = MapParser.DEFAULT.parse(value);
        return _mapValueConverter.convert(mapValue);
    }

}
