package com.ctrip.framework.foundation.provider;

import com.ctrip.framework.foundation.io.BOMInputStream;
import com.ctrip.framework.foundation.spi.provider.ApplicationProvider;
import com.ctrip.framework.foundation.spi.provider.BuildProvider;
import com.ctrip.framework.foundation.spi.provider.Provider;
import com.ctrip.framework.foundation.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class DefaultApplicationProvider extends AbstractProvider implements ApplicationProvider {
    private static final Logger logger = LoggerFactory.getLogger(DefaultApplicationProvider.class);
    private static final String ZEUS_APP_ID_PROPERTY_KEY = "DP_APP_ID";
    public static final String APP_PROPERTIES_CLASSPATH = "/META-INF/app.properties";
    private Properties m_appProperties = new Properties();
    private BuildProvider m_buildProvider;

    private String m_appId;
    private boolean m_appIdConflict = false;

    public DefaultApplicationProvider(BuildProvider buildProvider) {
        m_buildProvider = buildProvider;
    }

    @Override
    public void initialize() {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(APP_PROPERTIES_CLASSPATH);
            if (in == null) {
                in = DefaultApplicationProvider.class.getResourceAsStream(APP_PROPERTIES_CLASSPATH);
            }

            if (in == null) {
                logger.error("ERROR:{} not found from classpath!", APP_PROPERTIES_CLASSPATH);
            }
            initialize(in);
        } catch (Exception ex) {
            logger.error("DefaultApplicationProvider initialize error", ex);
        }
    }

    @Override
    public void initialize(InputStream in) {
        try {
            if (in != null) {
                try {
                    m_appProperties.load(new InputStreamReader(new BOMInputStream(in), StandardCharsets.UTF_8));
                } finally {
                    in.close();
                }
            }

            initAppId();
        } catch (Exception ex) {
            logger.error("DefaultApplicationProvider initialize with inputstream error", ex);
        }
    }

    @Override
    public String getAppId() {
        return m_appId;
    }

//    @Override
//    public String getJdkVersion() {
//        String jdkVersion = getProperty("jdk.version", null);
//        if (jdkVersion == null) {
//            jdkVersion = getProperty("jdkVersion", null);
//        }
//        return jdkVersion;
//    }
//
//    @Override
//    public boolean isAppIdSet() {
//        return !Utils.isBlank(m_appId);
//    }
//
//    @Override
//    public boolean isAppIdConflicted() {
//        return m_appIdConflict;
//    }

    @Override
    public String getProperty(String name, String defaultValue) {
        if ("app.id".equals(name)) {
            String val = getAppId();
            return val == null ? defaultValue : val;
        } else {
            String val = m_appProperties.getProperty(name, defaultValue);
            return val == null ? defaultValue : val;
        }
    }

    @Override
    public Class<? extends Provider> getType() {
        return ApplicationProvider.class;
    }

    private void initAppId() {
        // 1. Try to get app id from env
        String appIdFromEnv = getAppIdFromEnv();

        // 2. Try to get app id from app.properties.
        String appIdFromAppProperties = m_appProperties.getProperty("app.id");

        // 3. Try to get app id from build.properties
        String appIdFromBuildProperties = m_buildProvider.getAppId();

        if (!Utils.isBlank(appIdFromEnv)) {
            m_appId = appIdFromEnv.trim();
            logger.info("App ID is set to {} by {} property from system properties.", m_appId, ZEUS_APP_ID_PROPERTY_KEY);
        } else if (!Utils.isBlank(appIdFromAppProperties)) {
            m_appId = appIdFromAppProperties.trim();
            logger.info("App ID is set to {} by app.id property from {}", m_appId, APP_PROPERTIES_CLASSPATH);

            if (!Utils.isBlank(appIdFromBuildProperties)) {
                appIdFromBuildProperties = appIdFromBuildProperties.trim();
                if (!m_appId.equals(appIdFromBuildProperties)) {
                    m_appIdConflict = true;
                    logger.info("Found inconsistent App Id from build.properties{} v.s. from app.properties{}!", appIdFromBuildProperties, m_appId);
                }
            }
        } else if (!Utils.isBlank(appIdFromBuildProperties)) {
            m_appId = appIdFromBuildProperties.trim();
            logger.info("App ID is set to {} by app.id property from build.properties InputStream.", m_appId);
        } else {
            logger.error("app.id is not available from {}. It is set to null", APP_PROPERTIES_CLASSPATH);
            m_appId = null;
        }
    }

    String getAppIdFromEnv() {
        // TODO: support app env
        String appId = System.getProperty(ZEUS_APP_ID_PROPERTY_KEY);

        if (Utils.isBlank(appId)) {
            appId = System.getProperty(ZEUS_APP_ID_PROPERTY_KEY.toLowerCase());
        }

        return appId;
    }

    @Override
    public String toString() {
        return "appId [" + getAppId() + "] properties: " + m_appProperties + " (DefaultApplicationProvider)";
    }
}
