package com.ctrip.framework.foundation;

import com.ctrip.framework.foundation.provider.DefaultApplicationProvider;
import com.ctrip.framework.foundation.provider.DefaultBuildProvider;
import com.ctrip.framework.foundation.provider.DefaultMetadataProvider;
import com.ctrip.framework.foundation.provider.DefaultNetworkProvider;
import com.ctrip.framework.foundation.provider.DefaultServerProvider;
import com.ctrip.framework.foundation.spi.provider.BuildProvider;
import com.ctrip.framework.foundation.spi.provider.Provider;
import com.ctrip.framework.foundation.spi.provider.ProviderManager;
import com.ctrip.framework.foundation.spi.provider.ServerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class DefaultProviderManager implements ProviderManager {
    private static final Logger logger = LoggerFactory.getLogger(DefaultProviderManager.class);
    private Map<Class<? extends Provider>, Provider> m_providers = new LinkedHashMap<Class<? extends Provider>, Provider>();

    public DefaultProviderManager() {
        // Load build configuration, like app id, from classpath://META-INF/build.properties
        BuildProvider buildProvider = new DefaultBuildProvider();
        buildProvider.initialize();
        register(buildProvider);

        // Load per-application configuration, like app id, from classpath://META-INF/app.properties
        Provider applicationProvider = new DefaultApplicationProvider(buildProvider);
        applicationProvider.initialize();
        register(applicationProvider);

        // Load network parameters
        Provider networkProvider = new DefaultNetworkProvider();
        networkProvider.initialize();
        register(networkProvider);

        // Load environment (fat, fws, uat, prod ...) and dc, from /opt/settings/server.properties, JVM property and/or OS environment variables.
        ServerProvider serverProvider = new DefaultServerProvider();
        serverProvider.initialize();
        register(serverProvider);

        // Load metadata from /opt/settings/region.properties
        Provider metadataProvider = new DefaultMetadataProvider(serverProvider);
        metadataProvider.initialize();
        register(metadataProvider);
    }

    public synchronized void register(Provider provider) {
        m_providers.put(provider.getType(), provider);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Provider> T provider(Class<T> clazz) {
        Provider provider = m_providers.get(clazz);

        if (provider != null) {
            return (T) provider;
        } else {
            logger.error(String.format("No provider [%s] found in DefaultProviderManager, please make sure it is registered in DefaultProviderManager ", clazz.getName()));
            return (T) NullProviderManager.provider;
        }
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        for (Provider provider : m_providers.values()) {
            String value = provider.getProperty(name, null);

            if (value != null) {
                return value;
            }
        }

        return defaultValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(512);
        if (null != m_providers) {
            for (Map.Entry<Class<? extends Provider>, Provider> entry : m_providers.entrySet()) {
                sb.append(entry.getValue()).append("\n");
            }
        }
        sb.append("(DefaultProviderManager)").append("\n");
        return sb.toString();
    }
}

