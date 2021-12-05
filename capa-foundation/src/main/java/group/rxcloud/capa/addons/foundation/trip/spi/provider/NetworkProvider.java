package group.rxcloud.capa.addons.foundation.trip.spi.provider;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
/**
 * Provider for network related properties
 */
public interface NetworkProvider extends Provider {
    /**
     * @return the host address, i.e. ip
     */
    public String getHostAddress();

    /**
     * @return the host name
     */
    public String getHostName();
}
