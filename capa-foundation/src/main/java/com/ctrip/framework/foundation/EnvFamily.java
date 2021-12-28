package com.ctrip.framework.foundation;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public enum EnvFamily {
    // Assume you are working at the beach with no network access
    LOCAL("Local Development environment"),

    FAT("Feature Acceptance Test environment"),

    UAT("User Acceptance Test environment"),

    PRO("Production environment"),

    UNKNOWN("Unknown environment");

    private final String m_description;

    EnvFamily(String description) {
        m_description = description;
    }

    public boolean isLocal() {
        return this == LOCAL;
    }

    public boolean isFAT() {
        return this == FAT;
    }

    public boolean isUAT() {
        return this == UAT;
    }

    public boolean isPRO() {
        return this == PRO;
    }

    public String getName() {
        return name();
    }

    public String getDescription() {
        return m_description;
    }

    public boolean isValid() {
        return this != UNKNOWN;
    }

    public static EnvFamily getByName(String name, EnvFamily defaultValue) {
        if (name != null) {
            name = name.trim();
            for (EnvFamily value : values()) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }
            //FAT mappings
            if (name.equalsIgnoreCase("LPT") ||
                    name.equalsIgnoreCase("FWS") ||
                    name.equalsIgnoreCase("DEV")) {
                return EnvFamily.FAT;
            }
        }

        return defaultValue;
    }
}
