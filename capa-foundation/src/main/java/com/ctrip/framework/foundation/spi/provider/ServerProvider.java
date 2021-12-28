package com.ctrip.framework.foundation.spi.provider;

import com.ctrip.framework.foundation.Env;
import com.ctrip.framework.foundation.EnvFamily;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public interface ServerProvider extends Provider{
    /**
     * @return current environment or {@code null} if not set
     *
     * @deprecated use <code>getEnv()</code> and <code>getEnvFamily()</code> instead
     *
     * @see ServerProvider#getEnv()
     * @see ServerProvider#getEnvFamily()
     */
    @Deprecated
    public String getEnvType();
//
//    /**
//     * @return whether current environment is set or not
//     *
//     * @deprecated use <code>isEnvValid()</code> instead
//     *
//     * @see ServerProvider#isEnvValid()
//     */
//    @Deprecated
//    public boolean isEnvTypeSet();

//    /**
//     * @return current sub environment or {@code null} if not set
//     */
//    public String getSubEnv();
//
//    /**
//     * @return whether current sub environment is set or not
//     */
//    public boolean isSubEnvSet();

    /**
     * @return current data center or {@code null} if not set
     */
    public String getDataCenter();
//
//    /**
//     * @return current zone or {@code null} if not set
//     */
//    public String getZone();
//
//    /**
//     * @return whether data center is set or not
//     */
//    public boolean isDataCenterSet();
//
//    /**
//     * @return whether current environment is tooling environment
//     */
//    public boolean isTooling();
//
//    /**
//     * @return whether current environment is pci environment
//     */
//    public boolean isPci();

    /**
     * @return current environment enum or {@code Env.UNKNOWN} if not set
     *
     * @see "http://conf.ctripcorp.com/pages/viewpage.action?pageId=119308692"
     */
    public Env getEnv();

    /**
     * @return whether current environment enum is valid or not
     */
    public boolean isEnvValid();

    /**
     * EnvFamily is an abstraction of environments.
     * For example: FAT, FWS, LPT and DEV will be categorized as one EnvFamily: EnvFamily.FAT.
     *
     * @return the EnvFamily abstraction of current environment or {@code EnvFamily.UNKNOWN} if env is not set
     *
     * @see "http://conf.ctripcorp.com/pages/viewpage.action?pageId=119308692"
     */
    public EnvFamily getEnvFamily();

    /**
     * As with the EnvFamily, each EnvFamily could have different clusters.
     * For example: env LPT is considered as a cluster of EnvFamily.FAT: lpt
     *
     * @return the cluster name, should be lowercase except for those well known idc names(SHAOY, SHAJQ, SHAFQ)
     * Typical return values are:
     * <ul>
     * <li>fat01(fat sub envs are in the format: fatxxx)</li>
     * <li>lpt01(lpt sub envs are in the format: lptxxx)</li>
     * <li>fat</li>
     * <li>lpt</li>
     * <li>fws</li>
     * <li>dev</li>
     * <li>uat</li>
     * <li>pro</li>
     * <li>SHAOY</li>
     * <li>SHAJQ</li>
     * <li>SHAFQ</li>
     * </ul>
     *
     * @see "http://conf.ctripcorp.com/pages/viewpage.action?pageId=119308692"
     */
    public String getClusterName();

//    /**
//     * @return current environment or {@code null} if not set
//     */
//    public HostType getHostType();

    public String getProvider();

    public String getNamespace();

    public String getRegion();

    /**
     * Initialize server provider with the specified input stream
     *
     * @param in inputstream
     * @throws IOException IOException
     */
    public void initialize(InputStream in) throws IOException;

    /**
     * @return all properties
     */
    public Map<String, String> getProperties();
}
