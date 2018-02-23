package com.minds.great.hueLightProject.hueImpl;

import android.graphics.Color;

import com.minds.great.hueLightProject.core.models.LightSystem;
import com.philips.lighting.hue.sdk.wrapper.connection.BridgeConnection;

public class HueUtil {

    static LightSystem convertBridgeConnectionToLightSystem(BridgeConnection bridgeConnection) {
        return new LightSystem.Builder()
                .ipAddress(bridgeConnection.getBridge().getBridgeConfiguration().getNetworkConfiguration().getIpAddress())
                .bridge(bridgeConnection.getBridge())
                .build();
    }

    public static int getRGBFromColorTemperature(int ct, int brightness) {
        // Used this: https://gist.github.com/paulkaplan/5184275 at the beginning
        // based on http://stackoverflow.com/questions/7229895/display-temperature-as-a-color-with-c
        // this answer: http://stackoverflow.com/a/24856307
        // (so, just interpretation of pseudocode in Java)

        int temperature = (1000000 / ct) / 100;

        double red;
        double green;
        double blue;

        // R
        if (temperature <= 66) {
            red = 255;
        } else {
            red = temperature - 60;
            red = 329.698727446 * Math.pow(red, -0.1332047592);
            if (red < 0) {
                red = 0;
            } else if (red > 255) {
                red = 255;
            }
        }
        // G
        if (temperature <= 66) {
            green = temperature;
            green = 99.4708025861 * Math.log(green) - 161.1195681661;
        } else {
            green = temperature - 60;
            green = 288.1221695283 * Math.pow(green, -0.0755148492);
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        // B
        if (temperature >= 66) {
            blue = 255;
        } else if (temperature <= 19){
            blue = 0;
        } else {
            blue = temperature - 10;
            blue = 138.5177312231 * Math.log(blue) - 305.0447927307;
            if (blue < 0) {
                blue = 0;
            } else if (blue > 255) {
                blue = 255;
            }
        }
        return Color.argb(brightness, (int) red, (int) green, (int) blue);
    }

}
