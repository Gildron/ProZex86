package com.x86.prozex86.components;

import com.x86.prozex86.HomeScreenController;

import java.io.Serializable;

public class CPUComponent extends Level implements Serializable {
    public CPUComponent() {
    }

    protected void levelUp() {
        HomeScreenController.currentHomeScreenControllerNOTSingleton.updateProgressBarSpeed();
    }

    public double getTickRate() {
        double level = this.getLevel();
        return Math.ceil(Math.pow(2.0, ((20.00 / 63.00) * level)) / ((0.05 * Math.pow(level - 10.00, 2.0) + 0.1 * level + 25) * (Math.pow(2.0, 0.133 * (level - 1.0))) * (1.0 / 16.00)));
    }
}