package com.minds.great.hueLightProject.core.models;

public class ConnectionError {
    public static int NO_BRIDGE_FOUND_CODE = 1157;

    private int code;

    private ConnectionError(){}

    public int getCode() {
        return code;
    }

    public static class Builder{
        private int code;

        public Builder code(int code){
            this.code = code;
            return this;
        }

        public ConnectionError build(){
            ConnectionError connectionError = new ConnectionError();
            connectionError.code = this.code;
            return connectionError;
        }
    }
}
