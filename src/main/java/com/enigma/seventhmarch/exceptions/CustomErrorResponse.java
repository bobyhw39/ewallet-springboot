package com.enigma.seventhmarch.exceptions;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;


public class CustomErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;

    public CustomErrorResponse() {
        // TODO Auto-generated constructor stub
    }

    public CustomErrorResponse(String message) {
        this.message = message;
    }

    public CustomErrorResponse(LocalDateTime timestamp, int status, String error, String path) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.message = error;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String error) {
        this.message = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
