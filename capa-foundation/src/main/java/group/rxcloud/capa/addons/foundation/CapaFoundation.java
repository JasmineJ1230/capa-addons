package group.rxcloud.capa.addons.foundation;

import com.ctrip.framework.foundation.Foundation;

public final class CapaFoundation {

    public static String getAppId(FoundationType foundationType) {
        if (foundationType == FoundationType.TRIP) {
            return Foundation.app().getAppId();
        }
        return "";
    }

    public static String getEnv(FoundationType foundationType) {
        return "";
    }
}
