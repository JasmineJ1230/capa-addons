package group.rxcloud.capa.addons.foundation.trip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class CapaAppPropertyUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CapaAppPropertyUtils.class);

    private static final Properties APP_PROPERTIES = new Properties();

    public static final String APP_PROPERTIES_CLASSPATH = "/META-INF/app.properties";
    public static final String APP_ID_PROPERTY = "app.id";

    static {
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(APP_PROPERTIES_CLASSPATH);
        if (in == null) {
            in = CapaAppPropertyUtils.class.getResourceAsStream(APP_PROPERTIES_CLASSPATH);
        }
        if (in == null) {
            LOGGER.error("ERROR: " + APP_PROPERTIES_CLASSPATH + " not found from classpath!");
        }
        try {
            APP_PROPERTIES.load(in);
        } catch (IOException e) {
            LOGGER.error("ERROR: load appProperty error, ", e);
        }
    }

    private CapaAppPropertyUtils() {
    }

    private static Properties getAppProperties() {
        return APP_PROPERTIES;
    }

    public static String getAppId() {
        return getAppProperties().getProperty(APP_ID_PROPERTY);
    }
}
