package group.rxcloud.capa.addons.serialzer.ssjson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.google.common.annotations.VisibleForTesting;
import group.rxcloud.capa.addons.serialzer.validate.BigIntegerValidator;
import group.rxcloud.capa.addons.serialzer.validate.TypeValidateResult;
import group.rxcloud.capa.addons.serialzer.validate.ValidateValueException;

import java.io.IOException;
import java.math.BigInteger;

public class BigIntegerDeserializerWrapper extends NumberDeserializers.BigIntegerDeserializer {
    private final int bigNumberMaxLength;
    private final NumberDeserializers.BigIntegerDeserializer delegate = NumberDeserializers.BigIntegerDeserializer.instance;
    private final BigIntegerValidator bigIntegerValidator = BigIntegerValidator.INSTANCE;

    public BigIntegerDeserializerWrapper(int bigNumberMaxLength) {
        this.bigNumberMaxLength = bigNumberMaxLength;
    }

    @Override
    public BigInteger deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        TypeValidateResult result = validateRawValue(p.getText());
        if (!result.isPass()) {
            throw new ValidateValueException("validate BigInteger failed:" + result.getErrorMessages());
        }
        return delegate.deserialize(p, ctxt);
    }

    @VisibleForTesting
    TypeValidateResult validateRawValue(String rawValue) {
        return bigIntegerValidator.validateRawValue(rawValue, bigNumberMaxLength);
    }
}
