package com.xbl.entity;

/**
 * Created by April on 2017/6/15.
 * 传递最大值最小值所需要的VO类
 */

public class SoilVo {

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

    private int maxSoilHumidity;
    private int minSoilHumidity;
    private int maxSoilTemperature;
    private int minSoilTemperature;

    public SoilVo(int minSoilHumidity,int maxSoilHumidity,  int minSoilTemperature, int maxSoilTemperature) {
        this.maxSoilHumidity = maxSoilHumidity;
        this.minSoilHumidity = minSoilHumidity;
        this.maxSoilTemperature = maxSoilTemperature;
        this.minSoilTemperature = minSoilTemperature;
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
