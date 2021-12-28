package com.ctrip.framework.foundation.internals.provider;

import com.ctrip.framework.foundation.HostType;
import com.ctrip.framework.foundation.internals.NetworkInterfaceManager;
import com.ctrip.framework.foundation.Env;
import com.ctrip.framework.foundation.EnvFamily;
import com.ctrip.framework.foundation.internals.io.BOMInputStream;
import com.ctrip.framework.foundation.spi.provider.Provider;
import com.ctrip.framework.foundation.spi.provider.ServerProvider;
import com.ctrip.framework.foundation.internals.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class DefaultServerProvider extends AbstractProvider implements ServerProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServerProvider.class);
    private static final String SERVER_PROPERTIES_LINUX = "/opt/settings/server.properties";
    private static final String SERVER_PROPERTIES_WINDOWS = "C:/opt/settings/server.properties";
    static final String DEFAULT_CLUSTER_NAME = "default";

    // 10.9.0.0/16 is for both prod and QA, and should not be included.
    private String[] CTRIP_PROD_IP_PREFIX = {"10.8.", "10.15.", "10.28."};

    private String m_envType;
    private String m_subEnv;
    private String m_dc;
    private HostType m_hostType;
    private Env m_env;
    private String m_clusterName;
    private String provider;
    private String namespace;
    private String region;

    private Properties m_serverProperties = new Properties();

    @Override
    public void initialize() {
        try {
            File file = new File(SERVER_PROPERTIES_LINUX);
            if (file.exists() && file.canRead()) {
                LOGGER.info("[Capa.Foundation] Loading:{}", file.getAbsolutePath());
                FileInputStream fis = new FileInputStream(file);
                initialize(fis);
                return;
            }

            file = new File(SERVER_PROPERTIES_WINDOWS);
            if (file.exists() && file.canRead()) {
                LOGGER.info("[Capa.Foundation] Loading:{}", file.getAbsolutePath());
                FileInputStream fis = new FileInputStream(file);
                initialize(fis);
                return;
            }
            LOGGER.error(SERVER_PROPERTIES_LINUX + " and " + SERVER_PROPERTIES_WINDOWS + " does not exist or is not readable.");
            initialize(null);
        } catch (Exception ex) {
            LOGGER.error("[Capa.Foundation] server provider initialize error,", ex);
        }
    }

    @Override
    public void initialize(InputStream in) {
        try {
            if (in != null) {
                try {
                    m_serverProperties.load(new InputStreamReader(new BOMInputStream(in), StandardCharsets.UTF_8));
                } finally {
                    in.close();
                }
            }

            initEnvType();
            initSubEnv();
            initDataCenter();
            initEnv();
            initClusterName();
            initHostType();
            initProvider();
            validate();
            initNamespace();
            initRegion();
        } catch (Exception ex) {
            LOGGER.error("[Capa.Foundation] server provider initialize with inpurstream error,", ex);
        }
    }

    @Override
    public String getDataCenter() {
        return m_dc;
    }

//    @Override
//    public String getZone() {
//        return getDataCenter();
//    }
//
//
//    @Override
//    public boolean isDataCenterSet() {
//        return m_dc != null;
//    }
//
//    @Override
//    public boolean isTooling() {
//        return getBooleanProperty("tooling", false);
//    }
//
//    @Override
//    public boolean isPci() {
//        return getBooleanProperty("pci", false);
//    }

    @Override
    public Env getEnv() {
        return m_env;
    }

    @Override
    public boolean isEnvValid() {
        return m_env.isValid();
    }

    @Override
    public EnvFamily getEnvFamily() {
        return m_env.getEnvFamily();
    }

    @Override
    public String getClusterName() {
        return m_clusterName;
    }

    @Override
    @Deprecated
    public String getEnvType() {
        return m_envType;
    }
//
//    @Override
//    @Deprecated
//    public boolean isEnvTypeSet() {
//        return !Utils.isBlank(m_envType);
//    }
//
//    @Override
//    public String getSubEnv() {
//        return m_subEnv;
//    }
//
//    @Override
//    public boolean isSubEnvSet() {
//        return m_subEnv != null;
//    }
//
//    @Override
//    public HostType getHostType() {
//        return m_hostType;
//    }

    @Override
    public String getProvider() {
        return provider;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        if ("env".equalsIgnoreCase(name)) {
            String val = getEnvType();
            return val == null ? defaultValue : val;
        }
//        else if ("subenv".equalsIgnoreCase(name)) {
//            String val = getSubEnv();
//            return val == null ? defaultValue : val;
//        }
        else if ("idc".equalsIgnoreCase(name)) {
            String val = getDataCenter();
            return val == null ? defaultValue : val;
        } else {
            String val = m_serverProperties.getProperty(name, defaultValue);
            return val == null ? defaultValue : val.trim();
        }
    }

    @Override
    public Map<String, String> getProperties() {
        Map<String, String> propertyMap = new HashMap<>(8);
        for (Map.Entry<Object, Object> entry : m_serverProperties.entrySet()) {
            propertyMap.put(((String) entry.getKey()).trim(), ((String) entry.getValue()).trim());
        }
        return propertyMap;
    }

    @Override
    public Class<? extends Provider> getType() {
        return ServerProvider.class;
    }

    private void initEnvType() {
        String envFromServerProperties = m_serverProperties.getProperty("env");
        if (Utils.isBlank(envFromServerProperties)) {
            envFromServerProperties = m_serverProperties.getProperty("environment", null);
        }

        boolean isLocal = getBooleanProperty("local", false);
        String envFromSystemProperty = System.getProperty("env");
        String envFromOSVariable = System.getenv("ENV");

        //according to http://conf.ctripcorp.com/pages/viewpage.action?pageId=126133133#id-%E9%83%A8%E7%BD%B2%E7%9B%B8%E5%85%B3-2017/05/10
        if (isLocal || Utils.isBlank(envFromServerProperties)) {
            // 1. Try to get environment from JVM system property
            if (!Utils.isBlank(envFromSystemProperty)) {
                m_envType = envFromSystemProperty.trim();
                LOGGER.info("Environment is set to {} by JVM system property 'env'.", m_envType);
                return;
            }

            // 2. Try to get environment from OS environment variable
            if (!Utils.isBlank(envFromOSVariable)) {
                m_envType = envFromOSVariable.trim();
                LOGGER.info("Environment is set to {} by OS env variable 'ENV'.", m_envType);
                return;
            }
        } else if (!Utils.isBlank(envFromSystemProperty) || !Utils.isBlank(envFromOSVariable)) {
            //TODO maybe warn is more appropriate
            LOGGER.warn("local != true, env %s is not allowed to be overridden by JVM system property or OS env variable 'ENV'!{}", envFromServerProperties);
            LOGGER.warn("For more information, please refer http://conf.ctripcorp.com/pages/viewpage.action?pageId=126133133#id-%E9%83%A8%E7%BD%B2%E7%9B%B8%E5%85%B3-2017/05/10");
        }

        // 3. Try to get environment from file "server.properties"
        if (!Utils.isBlank(envFromServerProperties)) {
            m_envType = envFromServerProperties.trim();
            LOGGER.info("Environment is set to {} by property 'env' in server.properties.", m_envType);
            return;
        }

        // 4. If IP belongs to one of the prod network zones, set environment to prod
        m_envType = identifyProdByIP();
        if (!Utils.isBlank(m_envType)) {
            LOGGER.info("Environment is automatically set to {} by IP zone identification. IP:{}", m_envType, NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
            return;
        }

        // 5. Set environment to null.
        if (Utils.isBlank(m_envType)) {
            m_envType = null;
            LOGGER.error("Environment is set to null. " +
                    "Because it is not available in either (1) JVM system property 'env', (2) OS env variable 'ENV' nor (3) property 'env' from the properties InputStream.");
        }
    }

    private void initSubEnv() {
        //1. Try to get sub env from server.properties
        m_subEnv = m_serverProperties.getProperty("subenv", null);
        if (!Utils.isBlank(m_subEnv)) {
            m_subEnv = m_subEnv.trim();
            LOGGER.info("Sub env is set to {} by property 'subenv' in  server.properties.", m_subEnv);
            return;
        }

        //2. Set sub env to null.
        if (Utils.isBlank(m_subEnv)) {
            m_subEnv = null;
        }
    }

    private void initProvider() {
        //1. Try to get provider from server.properties
        provider = m_serverProperties.getProperty("provider", null);
        if (!Utils.isBlank(provider)) {
            provider = provider.trim();
            LOGGER.info("Sub env is set to {} by property 'provider' in  server.properties.", provider);
        } else {
            provider = null;
        }
    }

    private void initNamespace() {
        namespace = m_serverProperties.getProperty("namespace", null);
        if (!Utils.isBlank(namespace)) {
            namespace = namespace.trim();
            LOGGER.info("Sub env is set to {} by property 'namespace' in  server.properties.", namespace);
        } else {
            namespace = null;
        }
    }

    private void initRegion() {
        region = m_serverProperties.getProperty("region", null);
        if (!Utils.isBlank(region)) {
            region = region.trim();
            LOGGER.info("Sub env is set to {} by property 'region' in  server.properties.", region);
        } else {
            region = null;
        }
    }

    private void initDataCenter() {
        // idc in server.properties
        m_dc = m_serverProperties.getProperty("idc");
        if (!Utils.isBlank(m_dc)) {
            m_dc = m_dc.trim();
            LOGGER.info("Data Center is set to {} by property 'idc' in server.properties.", m_dc);
            return;
        }

        // Linux
        m_dc = System.getenv("ci_located_code");
        if (!Utils.isBlank(m_dc)) {
            m_dc = m_dc.trim();
            LOGGER.info("Data Center is set to {} by OS environment variable ci_located_code.", m_dc);
            return;
        }

        // Windows
        m_dc = System.getenv("CI_located_code");
        if (!Utils.isBlank(m_dc)) {
            m_dc = m_dc.trim();
            LOGGER.info("Data Center is set to {} by OS environment variable CI_located_code.", m_dc);
        } else {
            m_dc = null;
        }
    }

    private void initEnv() {
        // m_env is never null
        m_env = Env.getByName(m_envType, Env.UNKNOWN);
    }

    private void initClusterName() {
        // 1. Set to data center(PRO environment) or sub env(Others)
        // getEnvFamily always returns a non-null value
        if (getEnvFamily().isPRO()) {
            m_clusterName = m_dc; //no case conversion
        } else if (!Utils.isBlank(m_subEnv)) {
            m_clusterName = m_subEnv.toLowerCase(); //lower case
        }

        // 2. Set to environment
        if (Utils.isBlank(m_clusterName) && !Utils.isBlank(m_envType)) {
            m_clusterName = m_envType.toLowerCase(); //lower case
        }

        // 3. Set to default
        if (Utils.isBlank(m_clusterName)) {
            m_clusterName = DEFAULT_CLUSTER_NAME;
        }
    }

    private void initHostType() {
        //1. Try to get host type from server.properties
        String hostType = m_serverProperties.getProperty("host_type", null);
        if (!Utils.isBlank(hostType)) {
            hostType = hostType.trim();
            LOGGER.info("Host type is set to {} by property 'host_type' in  server.properties.", hostType);
            m_hostType = HostType.getByName(hostType, HostType.UNKNOWN);
            return;
        }

        //2. Set host type to null.
        if (Utils.isBlank(hostType)) {
            hostType = null;
        }
        m_hostType = HostType.getByName(hostType, HostType.UNKNOWN);
    }

    private String identifyProdByIP() {
        String ip = NetworkInterfaceManager.INSTANCE.getLocalHostAddress();
        for (String prefix : CTRIP_PROD_IP_PREFIX) {
            if (ip.startsWith(prefix)) {
                LOGGER.info("IP {} matches with prod network zone {}. Environment is set to PRO.", ip, prefix);
                return "PRO";
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "environment [" + getEnv() + "] data center [" + getDataCenter() + "] properties: " + m_serverProperties + " (DefaultServerProvider)";
    }

    private void validate() {
        // 1. validate environments
        if (!Utils.isBlank(m_envType) && !m_env.isValid()) {
            LOGGER.error("Env {} is invalid, please check whether it is correct!", m_envType);
        }

    }
}
