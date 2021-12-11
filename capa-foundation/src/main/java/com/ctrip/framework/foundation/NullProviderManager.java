package com.ctrip.framework.foundation;

import com.ctrip.framework.foundation.provider.NullProvider;
import com.ctrip.framework.foundation.spi.provider.ProviderManager;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class NullProviderManager implements ProviderManager {
    public static final NullProvider provider = new NullProvider();

    @Override
    public String getProperty(String name, String defaultValue) {
        return defaultValue;
    }

    @Override
    public NullProvider provider(Class clazz) {
        return provider;
    }

    @Override
    public String toString() {
        return provider.toString();
    }
}

