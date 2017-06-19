package com.xbl.entity;

/**
 * Created by April on 2017/6/13.
 */

public class ConfigData {

    /**
     * maxCo2 : 25
     * result : ok
     * maxLight : 25
     * minCo2 : 18
     * minLight : 18
     * maxSoilHumidity : 25
     * minSoilHumidity : 18
     * minAirHumidity : 18
     * minAirTemperature : 18
     * maxAirHumidity : 25
     * maxAirTemperature : 25
     * controlAuto : 0
     * maxSoilTemperature : 25
     * minSoilTemperature : 18
     */
    //CO2
    private int maxCo2;
    private int minCo2;
    //光照
    private int maxLight;
    private int minLight;
    //土壤
    private int maxSoilHumidity;
    private int minSoilHumidity;
    //空气
    private int minAirHumidity;
    private int minAirTemperature;
    private int maxAirHumidity;
    private int maxAirTemperature;
    private int controlAuto;
    private int maxSoilTemperature;
    private int minSoilTemperature;
    private String result;

    public int getMaxCo2() {
        return maxCo2;
    }

    public void setMaxCo2(int maxCo2) {
        this.maxCo2 = maxCo2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getMaxLight() {
        return maxLight;
    }

    public void setMaxLight(int maxLight) {
        this.maxLight = maxLight;
    }

    public int getMinCo2() {
        return minCo2;
    }

    public void setMinCo2(int minCo2) {
        this.minCo2 = minCo2;
    }

    public int getMinLight() {
        return minLight;
    }

    public void setMinLight(int minLight) {
        this.minLight = minLight;
    }

    public int getMaxSoilHumidity() {
        return maxSoilHumidity;
    }

    public void setMaxSoilHumidity(int maxSoilHumidity) {
        this.maxSoilHumidity = maxSoilHumidity;
    }

    public int getMinSoilHumidity() {
        return minSoilHumidity;
    }

    public void setMinSoilHumidity(int minSoilHumidity) {
        this.minSoilHumidity = minSoilHumidity;
    }

    public int getMinAirHumidity() {
        return minAirHumidity;
    }

    public void setMinAirHumidity(int minAirHumidity) {
        this.minAirHumidity = minAirHumidity;
    }

    public int getMinAirTemperature() {
        return minAirTemperature;
    }

    public void setMinAirTemperature(int minAirTemperature) {
        this.minAirTemperature = minAirTemperature;
    }

    public int getMaxAirHumidity() {
        return maxAirHumidity;
    }

    public void setMaxAirHumidity(int maxAirHumidity) {
        this.maxAirHumidity = maxAirHumidity;
    }

    public int getMaxAirTemperature() {
        return maxAirTemperature;
    }

    public void setMaxAirTemperature(int maxAirTemperature) {
        this.maxAirTemperature = maxAirTemperature;
    }

    public int getControlAuto() {
        return controlAuto;
    }

    public void setControlAuto(int controlAuto) {
        this.controlAuto = controlAuto;
    }

    public int getMaxSoilTemperature() {
        return maxSoilTemperature;
    }

    public void setMaxSoilTemperature(int maxSoilTemperature) {
        this.maxSoilTemperature = maxSoilTemperature;
    }

    public int getMinSoilTemperature() {
        return minSoilTemperature;
    }

    public void setMinSoilTemperature(int minSoilTemperature) {
        this.minSoilTemperature = minSoilTemperature;
    }
}
