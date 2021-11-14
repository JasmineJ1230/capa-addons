package group.rxcloud.capa.addons.serializer.baiji.ssjson;

import com.google.common.collect.ImmutableList;
import group.rxcloud.capa.addons.serializer.baiji.DateSerializer;
import group.rxcloud.capa.addons.serializer.baiji.date.*;

import java.util.ArrayList;
import java.util.List;

public class SSJsonSerializerConfig implements DateSerializerConfig {

    public static final DateSerializer DEFAULT_CALENDAR_SERIALIZER = WcfDateSerializer.INSTANCE;
    public static final List<DateSerializer> DEFAULT_CALENDAR_DESERIALIZERS = ImmutableList.of(
            WcfDateSerializer.INSTANCE,
            SimpleDateSerializer.INSTANCE,
            StandardDateSerializer.INSTANCE,
            ShortDateSerializer.INSTANCE,
            StandardSimpleDateSerializer.INSTANCE,
            StandardWithoutMillisecondDateSerializer.INSTANCE,
            JodaDateSerializer.INSTANCE);

    public static SSJsonSerializerConfig createDefault() {
        SSJsonSerializerConfig config = new SSJsonSerializerConfig();
        config.setCalendarSerializer(DEFAULT_CALENDAR_SERIALIZER);
        config.setCalendarDeserializers(new ArrayList<>(DEFAULT_CALENDAR_DESERIALIZERS));
        return config;
    }

    private DateSerializer calendarSerializer;
    private List<DateSerializer> calendarDeserializers;
    private boolean includeNonNullValues;
    private boolean bigNumberCheckEnabled;
    private int bigNumberMaxLength;
    private int bigDecimalMaxSignificandLength;
    private int bigDecimalMaxExponentLength;
    private ModuleConfigurator moduleConfigurator;
    private SSJsonModuleConfigurator ssJsonModuleConfigurator;
    private boolean keepDateSerializeTimeZone;
    private boolean autoCloseInput;

    @Override
    public DateSerializer getCalendarSerializer() {
        return calendarSerializer;
    }

    @Override
    public void setCalendarSerializer(DateSerializer calendarSerializer) {
        this.calendarSerializer = calendarSerializer;
    }

    @Override
    public List<DateSerializer> getCalendarDeserializers() {
        return calendarDeserializers;
    }

    @Override
    public void setCalendarDeserializers(List<DateSerializer> calendarDeserializers) {
        this.calendarDeserializers = calendarDeserializers;
    }

    public boolean isIncludeNonNullValues() {
        return includeNonNullValues;
    }

    public void setIncludeNonNullValues(boolean includeNonNullValues) {
        this.includeNonNullValues = includeNonNullValues;
    }

    public boolean isBigNumberCheckEnabled() {
        return bigNumberCheckEnabled;
    }

    public void setBigNumberCheckEnabled(boolean bigNumberCheckEnabled) {
        this.bigNumberCheckEnabled = bigNumberCheckEnabled;
    }

    public int getBigNumberMaxLength() {
        return bigNumberMaxLength;
    }

    public void setBigNumberMaxLength(int bigNumberMaxLength) {
        this.bigNumberMaxLength = bigNumberMaxLength;
    }

    public int getBigDecimalMaxSignificandLength() {
        return bigDecimalMaxSignificandLength;
    }

    public void setBigDecimalMaxSignificandLength(int bigDecimalMaxSignificandLength) {
        this.bigDecimalMaxSignificandLength = bigDecimalMaxSignificandLength;
    }

    public int getBigDecimalMaxExponentLength() {
        return bigDecimalMaxExponentLength;
    }

    public void setBigDecimalMaxExponentLength(int bigDecimalMaxExponentLength) {
        this.bigDecimalMaxExponentLength = bigDecimalMaxExponentLength;
    }

    public ModuleConfigurator getModuleConfigurator() {
        return moduleConfigurator;
    }

    /**
     * use {@link #setSSJsonModuleConfigurator(SSJsonModuleConfigurator)}
     */
    @Deprecated
    public void setModuleConfigurator(ModuleConfigurator moduleConfigurator) {
        this.moduleConfigurator = moduleConfigurator;
    }

    public SSJsonModuleConfigurator getSSJsonModuleConfigurator() {
        return ssJsonModuleConfigurator;
    }

    public void setSSJsonModuleConfigurator(SSJsonModuleConfigurator ssJsonModuleConfigurator) {
        this.ssJsonModuleConfigurator = ssJsonModuleConfigurator;
    }

    public boolean isKeepDateSerializeTimeZone() {
        return keepDateSerializeTimeZone;
    }

    public void setKeepDateSerializeTimeZone(boolean keepDateSerializeTimeZone) {
        this.keepDateSerializeTimeZone = keepDateSerializeTimeZone;
    }

    public boolean isAutoCloseInput() {
        return autoCloseInput;
    }

    public void setAutoCloseInput(boolean autoCloseInput) {
        this.autoCloseInput = autoCloseInput;
    }
}