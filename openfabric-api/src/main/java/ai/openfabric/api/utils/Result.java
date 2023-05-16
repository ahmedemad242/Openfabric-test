package ai.openfabric.api.utils;

public class Result {
    private boolean isSuccessful;
    private String message;
    private Object data;

    private int statusCode;

    public static Result success(int statusCode, Object data) {
        Result result = new Result();
        result.setSuccessful(true);
        result.setData(data);
        result.setStatusCode(statusCode);
        return result;
    }

    public static Result failure(int statusCode, String message) {
        Result result = new Result();
        result.setSuccessful(false);
        result.setMessage(message);
        result.setStatusCode(statusCode);
        return result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
            return "Result{" +
                    "isSuccessful=" + isSuccessful +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    ", statusCode=" + statusCode +
                    '}';
    }
}
