package com.x86.prozex86.components;

import java.io.Serializable;

public class RAMComponent extends Level implements Serializable {
    private double byteRate;

    public RAMComponent() {
        this.calculateByteRate();
    }

    @Override
    protected void levelUp() {
        calculateByteRate();
    }

    private void calculateByteRate() {
        this.byteRate = Math.pow(2.0, (0.133 * ((double) this.getLevel() - 1)));
    }

    public double getByteRate() {
        return byteRate;
    }
}
