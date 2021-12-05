package group.rxcloud.capa.addons.foundation.trip.http;

/**
 * @author Reckless Xu
 * 2021/12/5
 */
public class ResponseWrapper<T> {
    private int status; // 0: successful, -1: failed
    private String message;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccessful() {
        return status == 0;
    }
}

