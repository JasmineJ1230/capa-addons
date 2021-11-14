package group.rxcloud.capa.addons.serializer.baiji.ssjson;

import com.fasterxml.jackson.databind.module.SimpleModule;

@Deprecated
public interface ModuleConfigurator {

    /**
     * @param module The module registered to {@link com.fasterxml.jackson.databind.ObjectMapper}
     */
    void configure(SimpleModule module);
}
