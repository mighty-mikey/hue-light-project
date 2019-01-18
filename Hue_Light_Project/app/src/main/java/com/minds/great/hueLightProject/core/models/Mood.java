package com.minds.great.hueLightProject.core.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;

import java.util.List;

@Entity()
public class Mood {

    @PrimaryKey(autoGenerate = true)
    private Integer primaryKey;

    private String name;

    @ColumnInfo(name = "LightPoints")
    @TypeConverters(LightPointConverter.class)
    private List<LightPoint> listOfLights;

    public Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LightPoint> getListOfLights() {
        return listOfLights;
    }

    public void setListOfLights(List<LightPoint> listOfLights) {
        this.listOfLights = listOfLights;
    }
}
