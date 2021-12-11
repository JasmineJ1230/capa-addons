package com.ctrip.framework.foundation;

import com.ctrip.framework.foundation.spi.provider.ApplicationProvider;
import com.ctrip.framework.foundation.spi.provider.ProviderManager;
import com.ctrip.framework.foundation.spi.provider.ServerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public abstract class Foundation {
    private static final Logger logger = LoggerFactory.getLogger(Foundation.class);
    private static Object lock = new Object();

    private volatile static ProviderManager s_manager;

    // yj.huang Encourage early initialization and fail early if it happens.
    static {
        getManager();
    }

    private static ProviderManager getManager() {
        if (s_manager == null) {
            // yj.huang Double locking to make sure only one thread initializes ProviderManager.
            synchronized (lock) {
                if (s_manager == null) {
                    s_manager = ServiceBootstrap.loadFirst(ProviderManager.class);
                }
            }
        }

        return s_manager;
    }

    public static String getProperty(String name, String defaultValue) {
        try {
            return getManager().getProperty(name, defaultValue);
        } catch (Exception ex) {
            logger.error("Foundation.getProperty() error", ex);
            return defaultValue;
        }
    }

//    public static NetworkProvider net() {
//        try {
//            return getManager().provider(NetworkProvider.class);
//        } catch (Exception ex) {
//            logger.error("Foundation.net() error", ex);
//            return NullProviderManager.provider;
//        }
//    }

    public static ServerProvider server() {
        try {
            return getManager().provider(ServerProvider.class);
        } catch (Exception ex) {
            logger.error("Foundation.server() error", ex);
            return NullProviderManager.provider;
        }
    }

//    public static MetadataProvider metadata() {
//        try {
//            return getManager().provider(MetadataProvider.class);
//        } catch (Exception ex) {
//            logger.error("Foundation.metadata() error", ex);
//            return NullProviderManager.provider;
//        }
//    }

    public static ApplicationProvider app() {
        try {
            return getManager().provider(ApplicationProvider.class);
        } catch (Exception ex) {
            logger.error("Foundation.app() error", ex);
            return NullProviderManager.provider;
        }
    }

//    public static BuildProvider build() {
//        try {
//            return getManager().provider(BuildProvider.class);
//        } catch (Exception ex) {
//            logger.error("Foundation.build() error", ex);
//            return NullProviderManager.provider;
//        }
//    }

    public static String asString() {
        try {
            return getManager().toString();
        } catch (Exception ex) {
            logger.error("Foundation.asString() error", ex);
            return NullProviderManager.provider.toString();
        }
    }
}

