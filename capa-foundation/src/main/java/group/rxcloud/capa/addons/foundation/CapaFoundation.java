package group.rxcloud.capa.addons.foundation;

import group.rxcloud.capa.addons.foundation.trip.CapaAppPropertyUtils;

public final class CapaFoundation {

    public static String getAppId(FoundationType foundationType) {
        if (foundationType == FoundationType.TRIP) {
            return CapaAppPropertyUtils.getAppId();
        }
        return "";
    }

    public static String getEnv(FoundationType foundationType) {
        return "";
    }
}
