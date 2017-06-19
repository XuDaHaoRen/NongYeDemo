package com.xbl.entity;

/**
 * Created by April on 2017/6/15.
 */

public class AirVo {

    /**
     * maxCo2 : 17
     * maxLight : 18
     * minCo2 : 19
     * minLight : 20
     * maxSoilHumidity : 21
     * minSoilHumidity : 22
     * minAirHumidity : 23
     * minAirTemperature : 24
     * maxAirHumidity : 25
     * maxAirTemperature : 26
     * controlAuto : 1
     * maxSoilTemperature : 27
     * minSoilTemperature : 28
     */

    private int minAirHumidity;
    private int minAirTemperature;
    private int maxAirHumidity;
    private int maxAirTemperature;

    public AirVo(int minAirHumidity, int maxAirHumidity, int minAirTemperature, int maxAirTemperature) {
        this.minAirHumidity = minAirHumidity;
        this.minAirTemperature = minAirTemperature;
        this.maxAirHumidity = maxAirHumidity;
        this.maxAirTemperature = maxAirTemperature;
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
}
