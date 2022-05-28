package ar.com.saile.demojwt.exceptions;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResponse {

    public ErrorResponse(String message, Map<String, Object> details) {

        super();
        this.message = message;
        this.details = details;
    }

    private String message;

    private Map<String, Object> details;

}
