package group.rxcloud.capa.addons.serializer.baiji.ssjson;

public interface SSJsonModuleConfigurator {

    /**
     * @param module The module registered to {@link com.fasterxml.jackson.databind.ObjectMapper}
     */
    void configure(SSJsonSimpleModule module);
}
