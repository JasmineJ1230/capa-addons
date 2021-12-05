package group.rxcloud.capa.addons.foundation.trip.spi.provider;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public interface MetadataProvider extends Provider {

    void initialize(InputStream in) throws IOException;

}
