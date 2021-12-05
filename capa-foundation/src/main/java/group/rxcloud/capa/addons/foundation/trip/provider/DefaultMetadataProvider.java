package group.rxcloud.capa.addons.foundation.trip.provider;

/**
 * @author Reckless Xu
 * 2021/12/5
 */

import com.google.gson.reflect.TypeToken;
import group.rxcloud.capa.addons.foundation.trip.http.HttpRequest;
import group.rxcloud.capa.addons.foundation.trip.http.HttpResponse;
import group.rxcloud.capa.addons.foundation.trip.http.HttpUtil;
import group.rxcloud.capa.addons.foundation.trip.http.ResponseWrapper;
import group.rxcloud.capa.addons.foundation.trip.io.BOMInputStream;
import group.rxcloud.capa.addons.foundation.trip.spi.provider.MetadataProvider;
import group.rxcloud.capa.addons.foundation.trip.spi.provider.Provider;
import group.rxcloud.capa.addons.foundation.trip.spi.provider.ServerProvider;
import group.rxcloud.capa.addons.foundation.trip.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dongcao on 2018/10/25.
 */
public class DefaultMetadataProvider extends AbstractProvider implements MetadataProvider {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMetadataProvider.class);

    private static final String META_PROPERTIES_LINUX = "/opt/settings/region.properties";

    private static final String META_PROPERTIES_WINDOWS = "C:/opt/settings/region.properties";

    private static final String METADATA_DOMAIN_KEY = "metadata_domain";

    private static final String HTTP_PROTOCOL = "http://";

    private Properties properties = new Properties();

    private ServerProvider server;

    public DefaultMetadataProvider(ServerProvider serverProvider) {
        this.server = serverProvider;
    }

    @Override
    public Class<? extends Provider> getType() {
        return MetadataProvider.class;
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    @Override
    public void initialize() {
        String filename = META_PROPERTIES_LINUX;
        if (Utils.isOSWindows()) {
            filename = META_PROPERTIES_WINDOWS;
        }

        File file = new File(filename);
        try {
            if (!file.exists() || !file.canRead()) {
                logger.error("fileName:{} not exist nor no read permission", filename);
                return;
            }

            logger.info("Loading {}", file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            initialize(fis);
        } catch (IOException e) {
            logger.error("DefaultMetadataProvider initialize metadata provider failed: ", e);
        }
    }

    @Override
    public void initialize(InputStream in) throws IOException {
        try {
            properties.load(new InputStreamReader(new BOMInputStream(in), StandardCharsets.UTF_8));
        } finally {
            in.close();
        }

        String domain = (String) properties.get(METADATA_DOMAIN_KEY);
        if (Utils.isBlank(domain)) {
            logger.error("/opt/settings/region.properties no {}kv", METADATA_DOMAIN_KEY);
            return;
        }

        if (!loadMetadataFromRemote(domain)) {
            throw new RuntimeException("load metadata from remote failed");
        }
    }

    private HttpRequest buildRequest(String domain) {
        String remoteUrl = domain + "/api/ctrip/metadata.json";
        remoteUrl = remoteUrl.startsWith(HTTP_PROTOCOL) ? remoteUrl : HTTP_PROTOCOL + remoteUrl;
        MetadataRequestBody body = new MetadataRequestBody();
        HttpRequest request = new HttpRequest(remoteUrl, body);

        return request;
    }

    private boolean loadMetadataFromRemote(String domain) {
        HttpRequest request = buildRequest(domain);
        Type type = new TypeToken<ResponseWrapper<Map<String, String>>>() {
        }.getType();
        HttpResponse<ResponseWrapper<Map<String, String>>> httpResponse = HttpUtil.getInstance().doPost(request, type);
        ResponseWrapper<Map<String, String>> metadataResponse = httpResponse.getBody();
        if (!metadataResponse.isSuccessful()) {
            logger.error("load metadata from remote failed:{}", metadataResponse.getMessage());
            return false;
        }

        Map<String, String> metadata = metadataResponse.getData();
        for (Map.Entry<String, String> entry : metadata.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String oldValue = System.getProperty(key);
            if (oldValue != null) {
                properties.put(key, oldValue);
                logger.info("metadata: {}={}", key, oldValue);
            } else {
                properties.put(key, value);
                System.setProperty(key, value);
                logger.info("metadata: {}={}", key, value);
            }
        }

        logger.info("success load metadata from remote!");
        return true;
    }

    private class MetadataRequestBody {

        private String env;
        private String subEnv;
        private String idc;
        private String region;
        private String hostType;
        private boolean pci;
        private boolean tooling;
        private boolean bigdata;

        MetadataRequestBody() {
            this.env = server.getEnv().getName();
            this.subEnv = server.getSubEnv();
            this.idc = server.getDataCenter();
            this.region = server.getProperty("region", idc);
            this.pci = server.isPci();
            this.tooling = server.isTooling();
            this.hostType = server.getHostType().getName();
            this.bigdata = server.getBooleanProperty("bigdata", false);
        }

        public String getEnv() {
            return env;
        }

        public String getSubEnv() {
            return subEnv;
        }

        public String getIdc() {
            return idc;
        }

        public String getRegion() {
            return region;
        }

        public String getHostType() {
            return hostType;
        }

        public boolean isPci() {
            return pci;
        }

        public boolean isTooling() {
            return tooling;
        }

        public boolean isBigdata() {
            return bigdata;
        }
    }
}
