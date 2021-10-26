package group.rxcloud.capa.addons.serialzer.value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

public final class CloseableValues {

    private static final Logger _logger = LoggerFactory.getLogger(CloseableValues.class);

    private CloseableValues() {

    }

    public static void close(Closeable closeable) {
        try {
            if (closeable == null)
                return;

            closeable.close();
        } catch (Throwable ex) {
            _logger.warn("Close closeable object failed", ex);
        }
    }

}
