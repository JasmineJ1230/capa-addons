package com.ctrip.framework.foundation.spi;

import com.ctrip.framework.foundation.spi.provider.Provider;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public interface ProviderManager {
    public String getProperty(String name, String defaultValue);

    public <T extends Provider> T provider(Class<T> clazz);
}
