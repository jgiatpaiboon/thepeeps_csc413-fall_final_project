package dto;

import com.google.gson.JsonElement;

public class ResponseDto {

    private StatusResponse status;
    private String message;
    private JsonElement data;

    public ResponseDto(StatusResponse status) {
        this.status = status;
    }
    public ResponseDto(StatusResponse status, String message) {
        this.status = status;
        this.message = message;
    }
    public ResponseDto(StatusResponse status, JsonElement data) {
        this.status = status;
        this.data = data;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}