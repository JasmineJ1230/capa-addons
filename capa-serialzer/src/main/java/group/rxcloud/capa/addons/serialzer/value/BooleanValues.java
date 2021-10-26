package group.rxcloud.capa.addons.serialzer.value;

public final class BooleanValues {

    public static boolean isTrue(Boolean value) {
        return Boolean.TRUE.equals(value);
    }

    public static boolean isFalse(Boolean value) {
        return Boolean.FALSE.equals(value);
    }

    private BooleanValues() {

    }

}
