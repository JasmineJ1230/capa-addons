package com.ctrip.framework.foundation;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public enum HostType {
    BM,

    VM,

    DOCKER,

    UNKNOWN;

    public boolean isValid(){
        return this != UNKNOWN;
    }

    public String getName() {
        return name();
    }

    public static HostType getByName(String name, HostType defaultValue) {
        if (name != null) {
            name = name.trim();
            for (HostType value : values()) {
                if (value.name().equalsIgnoreCase(name)) {
                    return value;
                }
            }
        }

        return defaultValue;
    }
}
