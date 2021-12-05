package group.rxcloud.capa.addons.foundation.trip.spi.provider;

/**
 * @author Reckless Xu
 * 2021/12/5
 */

import java.io.InputStream;
import java.util.Date;

/**
 * Provider for build related properties
 */
public interface BuildProvider extends Provider {
    /**
     * @return the application's app id from build.properties
     */
    public String getAppId();

    /**
     * @return the build id
     */
    public String getBuildId();

    /**
     * @return the build time
     */
    public Date getBuildTime();

    /**
     * Initialize the build provider with the specified input stream
     * @param in inputstream
     */
    public void initialize(InputStream in);
}
