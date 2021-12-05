package group.rxcloud.capa.addons.foundation.trip.http;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import group.rxcloud.capa.addons.foundation.trip.exception.FoundationException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Function;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class HttpUtil {

    private static HttpUtil s_instance = new HttpUtil();

    private final Gson gson;
    private final int DEFAULT_CONNECT_TIMEOUT = 1000; // 1 second
    private final int DEFAULT_READ_TIMEOUT = 5000; // 5 seconds

    public static HttpUtil getInstance() {
        return s_instance;
    }

    /**
     * Constructor.
     */
    private HttpUtil() {
        gson = new Gson();
    }

    /**
     * Do get operation for the http request.
     *
     * @param httpRequest the request
     * @param responseType the response type
     * @param <T> T
     * @return the response
     */
    public <T> HttpResponse<T> doGet(HttpRequest httpRequest, final Type responseType) {
        Function<String, T> convertResponse = new Function<String, T>() {
            @Override
            public T apply(String input) {
                return gson.fromJson(input, responseType);
            }
        };

        return requestWithSerializeFunction(httpRequest, "GET", convertResponse);
    }

    /**
     * Do post operation for the http request
     *
     * @param httpRequest the request
     * @param responseType the response type
     * @param <T> T
     * @return the response
     */
    public <T> HttpResponse<T> doPost(HttpRequest httpRequest, final Type responseType) {
        Function<String, T> convertResponse = new Function<String, T>() {
            @Override
            public T apply(String input) {
                return gson.fromJson(input, responseType);
            }
        };

        return requestWithSerializeFunction(httpRequest, "POST", convertResponse);
    }

    private <T> HttpResponse<T> requestWithSerializeFunction(HttpRequest httpRequest, String method,
                                                             Function<String, T> serializeFunction) {
        InputStreamReader isr = null;
        int statusCode;
        try {
            boolean shouldOutput = method.equalsIgnoreCase("POST") && httpRequest.getRequestBody() != null;

            HttpURLConnection conn = (HttpURLConnection) new URL(httpRequest.getUrl()).openConnection();

            conn.setRequestMethod(method);

            int connectTimeout = httpRequest.getConnectTimeout();
            if (connectTimeout < 0) {
                connectTimeout = DEFAULT_CONNECT_TIMEOUT;
            }

            int readTimeout = httpRequest.getReadTimeout();
            if (readTimeout < 0) {
                readTimeout = DEFAULT_READ_TIMEOUT;
            }

            conn.setDoOutput(shouldOutput);
            if (shouldOutput) {
                conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            }
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);

            conn.connect();

            if (shouldOutput) {
                try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
                    out.writeBytes(gson.toJson(httpRequest.getRequestBody()));
                }
            }

            statusCode = conn.getResponseCode();

            if (statusCode == 200) {
                isr = new InputStreamReader(conn.getInputStream());
                String content = CharStreams.toString(isr);
                return new HttpResponse<>(statusCode, serializeFunction.apply(content));
            }

        } catch (Throwable ex) {
            throw new FoundationException(String.format("Http operation failed for %s", httpRequest.getUrl()), ex);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        throw new FoundationException(
                String.format("Http operation failed for %s, status code: %d", httpRequest.getUrl(), statusCode));
    }

}
