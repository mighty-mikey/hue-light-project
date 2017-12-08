package com.minds.great.hueLightProject.core.models;

public class ConnectionError {
    public static int NO_BRIDGE_FOUND_CODE = 1157;

    private int code;


    public ConnectionError(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
