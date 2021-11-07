package group.rxcloud.capa.addons.serializer.ssjson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.google.common.annotations.VisibleForTesting;
import group.rxcloud.capa.addons.serializer.validate.BigDecimalValidator;
import group.rxcloud.capa.addons.serializer.validate.TypeValidateResult;
import group.rxcloud.capa.addons.serializer.validate.ValidateValueException;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalDeserializerWrapper extends NumberDeserializers.BigDecimalDeserializer {
    private int bigNumberMaxLength;
    private int bigDecimalMaxSignificandLength;
    private int bigDecimalMaxExponentLength;
    private BigDecimalValidator bigDecimalValidator = BigDecimalValidator.INSTANCE;
    private NumberDeserializers.BigDecimalDeserializer delegate;

    public BigDecimalDeserializerWrapper(int bigNumberMaxLength, int bigDecimalMaxSignificandLength, int bigDecimalMaxExponentLength) {
        this.bigNumberMaxLength = bigNumberMaxLength;
        this.bigDecimalMaxSignificandLength = bigDecimalMaxSignificandLength;
        this.bigDecimalMaxExponentLength = bigDecimalMaxExponentLength;
        this.delegate = new NumberDeserializers.BigDecimalDeserializer();
    }

    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        TypeValidateResult result = validateRawValue(p.getText());
        if (!result.isPass()) {
            if (!result.isPass()) {
                throw new ValidateValueException("validate BigDecimal failed:" + result.getErrorMessages());
            }
        }
        return delegate.deserialize(p, ctxt);
    }

    @VisibleForTesting
    TypeValidateResult validateRawValue(String rawValue)  {
        return bigDecimalValidator.validateRawValue(rawValue, bigNumberMaxLength, bigDecimalMaxSignificandLength, bigDecimalMaxExponentLength);
    }
}
