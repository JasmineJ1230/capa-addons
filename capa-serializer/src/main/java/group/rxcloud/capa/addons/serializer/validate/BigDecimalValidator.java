package group.rxcloud.capa.addons.serializer.validate;
public final class BigDecimalValidator {
    public static final BigDecimalValidator INSTANCE = new BigDecimalValidator();

    private BigDecimalValidator() {
    }

    public TypeValidateResult validateRawValue(String rawValue, int maxBigDecimalLength, int maxSignificandLength, int maxExponentLength) {
        TypeValidateResult typeValidateResult = new TypeValidateResult();
        if (rawValue.length() > maxBigDecimalLength) {
            typeValidateResult.addErrorMessage(String.format("the length of BigDecimal is to long, should not be longer than %d.", maxBigDecimalLength));
            typeValidateResult.setPass(false);
        }

        int index = Math.max(rawValue.indexOf('e'), rawValue.indexOf('E'));

        int significandLength = index == -1 ? rawValue.length() : index;
        if (significandLength > maxSignificandLength) {
            typeValidateResult.addErrorMessage(String.format("the length of significand in BigDecimal is to long, should not be longer than %d.", maxSignificandLength));
            typeValidateResult.setPass(false);
        }

        int exponentLength = index == -1 ? 0 : rawValue.length() - index - 1;
        if (exponentLength > maxExponentLength) {
            typeValidateResult.addErrorMessage(String.format("the length of exponent in BigDecimal is to long, should not be longer than %d.", maxExponentLength));
            typeValidateResult.setPass(false);
        }
        return typeValidateResult;
    }
}
