package group.rxcloud.capa.addons.serializer.baiji.validate;
public final class BigIntegerValidator {
    public static final BigIntegerValidator INSTANCE = new BigIntegerValidator();

    private BigIntegerValidator() {
    }

    public TypeValidateResult validateRawValue(String rawValue, int bigIntMaxLength) {
        TypeValidateResult typeValidateResult = new TypeValidateResult();
        if (rawValue.length() > bigIntMaxLength) {
            typeValidateResult.addErrorMessage(String.format("the length of BigInteger is to long, should not be longer than %d.", bigIntMaxLength));
            typeValidateResult.setPass(false);
        }
        return typeValidateResult;
    }
}
