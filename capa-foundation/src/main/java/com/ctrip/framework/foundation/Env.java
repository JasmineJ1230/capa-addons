package com.ctrip.framework.foundation;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public enum Env {
    // Assume you are working at the beach with no network access
    LOCAL("Local Development environment", EnvFamily.LOCAL),

    DEV("Development environment", EnvFamily.FAT),

    FWS("Feature Web Service Test environment", EnvFamily.FAT),

    FAT("Feature Acceptance Test environment", EnvFamily.FAT),

    LPT("Load and Performance Test environment", EnvFamily.FAT),

    UAT("User Acceptance Test environment", EnvFamily.UAT),

    PRO("Production environment", EnvFamily.PRO),

    UNKNOWN("Unknown environment", EnvFamily.UNKNOWN);

    private final static Env[] LOCAL_FAMILY_ENVIRONMENTS = new Env[]{LOCAL};
    private final static Env[] FAT_FAMILY_ENVIRONMENTS = new Env[]{DEV, FWS, FAT, LPT};
    private final static Env[] UAT_FAMILY_ENVIRONMENTS = new Env[]{UAT};
    private final static Env[] PRO_FAMILY_ENVIRONMENTS = new Env[]{PRO};
    private final static Env[] UNKNOWN_FAMILY_ENVIRONMENTS = new Env[0];
    private final String m_description;
    private final EnvFamily m_envFamily;

    Env(String description, EnvFamily envFamily) {
        m_description = description;
        m_envFamily = envFamily;
    }

    public String getName() {
        return name();
    }

    public String getDescription() {
        return m_description;
    }

    public EnvFamily getEnvFamily() {
        return m_envFamily;
    }

    public boolean isValid() {
        return this != UNKNOWN;
    }

    public boolean isLocal() {
        return this == LOCAL;
    }

    public boolean isDEV() {
        return this == DEV;
    }

    public boolean isFWS() {
        return this == FWS;
    }

    public boolean isFAT() {
        return this == FAT;
    }

    public boolean isLPT() {
        return this == LPT;
    }

    public boolean isUAT() {
        return this == UAT;
    }

    public boolean isPRO() {
        return this == PRO;
    }

    public static Env getByName(String name, Env defaultValue) {
        if (name != null) {
            name = name.trim();
            for (Env value : values()) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }
        }

        return defaultValue;
    }

    public static Env[] findByEnvFamily(EnvFamily envFamily) {
        if (envFamily == null) {
            return UNKNOWN_FAMILY_ENVIRONMENTS;
        }
        switch (envFamily) {
            case LOCAL:
                return LOCAL_FAMILY_ENVIRONMENTS;
            case FAT:
                return FAT_FAMILY_ENVIRONMENTS;
            case UAT:
                return UAT_FAMILY_ENVIRONMENTS;
            case PRO:
                return PRO_FAMILY_ENVIRONMENTS;
            default:
                return UNKNOWN_FAMILY_ENVIRONMENTS;
        }
    }
}
