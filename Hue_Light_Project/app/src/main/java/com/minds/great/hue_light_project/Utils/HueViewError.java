package com.minds.great.hue_light_project.Utils;

/**
 * Class to handle all errors
 */
public class HueViewError {
    public static int NO_BRIDGE_FOUND = 1157;

    private int code;
    private String message;

    public HueViewError(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
