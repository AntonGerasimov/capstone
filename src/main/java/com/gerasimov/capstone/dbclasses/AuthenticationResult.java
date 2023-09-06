package com.gerasimov.capstone.dbclasses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationResult {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
