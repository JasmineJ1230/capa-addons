package group.rxcloud.capa.addons.foundation.trip.provider;

import group.rxcloud.capa.addons.foundation.trip.HostType;
import group.rxcloud.capa.addons.foundation.trip.enums.Env;
import group.rxcloud.capa.addons.foundation.trip.enums.EnvFamily;
import group.rxcloud.capa.addons.foundation.trip.spi.provider.ApplicationProvider;
import group.rxcloud.capa.addons.foundation.trip.spi.provider.BuildProvider;
import group.rxcloud.capa.addons.foundation.trip.spi.provider.MetadataProvider;
import group.rxcloud.capa.addons.foundation.trip.spi.provider.NetworkProvider;
import group.rxcloud.capa.addons.foundation.trip.spi.provider.Provider;
import group.rxcloud.capa.addons.foundation.trip.spi.provider.ServerProvider;

import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class NullProvider extends AbstractProvider implements ApplicationProvider, NetworkProvider, ServerProvider,
        BuildProvider, MetadataProvider {
    @Override
    public String getAppId() {
        return null;
    }

    @Override
    public String getBuildId() {
        return null;
    }

    @Override
    public Date getBuildTime() {
        return null;
    }

    @Override
    public String getJdkVersion() {
        return null;
    }

    @Override
    public boolean isAppIdSet() {
        return false;
    }

    @Override
    public boolean isAppIdConflicted() {
        return false;
    }

    @Override
    public String getEnvType() {
        return null;
    }

    @Override
    public boolean isEnvTypeSet() {
        return false;
    }

    @Override
    public String getSubEnv() {
        return null;
    }

    @Override
    public boolean isSubEnvSet() {
        return false;
    }

    @Override
    public String getDataCenter() {
        return null;
    }

    @Override
    public String getZone() {
        return null;
    }

    @Override
    public boolean isDataCenterSet() {
        return false;
    }

    @Override
    public boolean isTooling() {
        return false;
    }

    @Override
    public boolean isPci() {
        return false;
    }

    @Override
    public Env getEnv() {
        return Env.UNKNOWN;
    }

    @Override
    public boolean isEnvValid() {
        return false;
    }

    @Override
    public EnvFamily getEnvFamily() {
        return EnvFamily.UNKNOWN;
    }

    @Override
    public String getClusterName() {
        return null;
    }

    @Override
    public HostType getHostType() {
        return HostType.UNKNOWN;
    }

    @Override
    public String getProvider() {
        return null;
    }

    @Override
    public String getNamespace() {
        return null;
    }

    @Override
    public String getRegion() {
        return null;
    }

    @Override
    public void initialize(InputStream in) {

    }

    @Override
    public Map<String, String> getProperties() {
        return Collections.emptyMap();
    }

    @Override
    public String getHostAddress() {
        return null;
    }

    @Override
    public String getHostName() {
        return null;
    }

    @Override
    public Class<? extends Provider> getType() {
        return null;
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        return null;
    }

    @Override
    public void initialize() {

    }

    @Override
    public String toString() {
        return "(NullProvider)";
    }
}

