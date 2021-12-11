package com.ctrip.framework.foundation.http;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class HttpResponse<T> {
    private final int m_statusCode;
    private final T m_body;

    public HttpResponse(int statusCode, T body) {
        this.m_statusCode = statusCode;
        this.m_body = body;
    }

    public int getStatusCode() {
        return m_statusCode;
    }

    public T getBody() {
        return m_body;
    }
}
