package io.github.scarletsky.bangumi.api.responses;

/**
 * Created by scarlex on 15-7-10.
 */
public class BaseResponse {
    private String request;
    private int code;
    private String error;

    public String getRequest() {
        return request;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
