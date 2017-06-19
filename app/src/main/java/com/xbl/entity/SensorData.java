package com.xbl.entity;

/**
 * Created by April on 2017/6/13.
 */

public class SensorData {

    /**
     * result : ok
     * airHumidity : 48
     * airTemperature : 25
     * soilTemperature : 25
     * co2 : 373
     * soilHumidity : 47
     * light : 6666
     */
    //空气
    private int airHumidity;
    private int airTemperature;
    //土壤
    private int soilTemperature;
    private int soilHumidity;
    //CO2
    private int co2;
    //光照
    private int light;


    public int getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(int airHumidity) {
        this.airHumidity = airHumidity;
    }

    public int getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(int airTemperature) {
        this.airTemperature = airTemperature;
    }

    public int getSoilTemperature() {
        return soilTemperature;
    }

    public void setSoilTemperature(int soilTemperature) {
        this.soilTemperature = soilTemperature;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(int soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }
}
