package com.minds.great.hueLightProject.core.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

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
