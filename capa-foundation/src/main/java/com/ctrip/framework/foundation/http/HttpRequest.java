package com.ctrip.framework.foundation.http;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class HttpRequest {
    private final String m_url;
    private final Object m_requestBody;
    private int m_connectTimeout;
    private int m_readTimeout;

    /**
     * Create the request for the url.
     * @param url the url
     */
    public HttpRequest(String url) {
        this(url, null);
    }

    public HttpRequest(String url, Object m_requestBody) {
        this.m_url = url;
        this.m_requestBody = m_requestBody;
    }

    public String getUrl() {
        return m_url;
    }

    public int getConnectTimeout() {
        return m_connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.m_connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return m_readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.m_readTimeout = readTimeout;
    }

    public Object getRequestBody() {
        return m_requestBody;
    }
}

