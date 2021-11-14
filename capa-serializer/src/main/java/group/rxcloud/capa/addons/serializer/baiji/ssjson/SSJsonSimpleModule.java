package group.rxcloud.capa.addons.serializer.baiji.ssjson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public final class SSJsonSimpleModule extends SimpleModule {

    private volatile SSJsonDeserializers ssJsonDeserializers = null;

    SSJsonSimpleModule() {
    }

    public void addDeserializer(JavaType javaType, JsonDeserializer<?> deserializer) {
        if (ssJsonDeserializers == null) {
            synchronized (this) {
                if (ssJsonDeserializers == null) {
                    ssJsonDeserializers = new SSJsonDeserializers();
                }
            }
        }
        ssJsonDeserializers.addDeserializer(javaType, deserializer);
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);

        if (ssJsonDeserializers != null) {
            context.addDeserializers(ssJsonDeserializers);
        }
    }
}
