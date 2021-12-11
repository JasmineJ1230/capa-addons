package com.ctrip.framework.foundation.spi.provider;

/**
 * @author Reckless Xu
 * 2021/12/5
 */

import java.io.InputStream;

/**
 * Provider for application related properties
 */
public interface ApplicationProvider extends Provider {
    /**
     * @return the application's app id
     */
    public String getAppId();

//    /**
//     * @return the application's runtime requirement for jdk version
//     */
//    public String getJdkVersion();
//
//    /**
//     * @return whether the application's app id is set or not
//     */
//    public boolean isAppIdSet();
//
//    /**
//     * @return whether the application's app id is conflicted with the one provided by build system
//     */
//    public boolean isAppIdConflicted();

    /**
     * Initialize the application provider with the specified input stream
     * @param in in
     */
    public void initialize(InputStream in);
}