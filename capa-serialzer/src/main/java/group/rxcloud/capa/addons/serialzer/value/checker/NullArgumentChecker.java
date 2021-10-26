package group.rxcloud.capa.addons.serialzer.value.checker;

public class NullArgumentChecker implements ValueChecker<Object> {

    public static final NullArgumentChecker DEFAULT = new NullArgumentChecker();

    @Override
    public void check(Object value, String valueName) {
        if (value == null)
            throw new IllegalArgumentException("argument " + valueName + " is null");
    }

}
