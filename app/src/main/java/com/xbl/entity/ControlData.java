package com.xbl.entity;

/**
 * Created by April on 2017/6/14.
 */

public class ControlData {

    private int buzzer=0;
    private int waterPump=0;
    private int roadlamp=0;
    private int blower=0;

    public int getBuzzer() {
        return buzzer;
    }

    public void setBuzzer(int buzzer) {
        this.buzzer = buzzer;
    }

    public int getWaterPump() {
        return waterPump;
    }

    public void setWaterPump(int waterPump) {
        this.waterPump = waterPump;
    }

    public int getRoadlamp() {
        return roadlamp;
    }

    public void setRoadlamp(int roadlamp) {
        this.roadlamp = roadlamp;
    }

    public int getBlower() {
        return blower;
    }

    public void setBlower(int blower) {
        this.blower = blower;
    }
}
