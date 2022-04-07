package com.minds.great.hueLightProject.core.models;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class LightPointConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<LightPoint> stringToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<LightPoint>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(List<LightPoint> someObjects) {
        return gson.toJson(someObjects);
    }

}
