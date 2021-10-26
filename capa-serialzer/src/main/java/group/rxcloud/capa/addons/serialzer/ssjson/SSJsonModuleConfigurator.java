package group.rxcloud.capa.addons.serialzer.ssjson;

public interface SSJsonModuleConfigurator {

    /**
     * @param module The module registered to {@link com.fasterxml.jackson.databind.ObjectMapper}
     */
    void configure(SSJsonSimpleModule module);
}
