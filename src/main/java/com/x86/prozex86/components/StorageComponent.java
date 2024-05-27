package com.x86.prozex86.components;

import com.x86.prozex86.SaveStateSingleton;

import java.io.Serializable;

public class StorageComponent extends Level implements Serializable {
    public StorageComponent() {
    }

    @Override
    protected void levelUp() {
        SaveStateSingleton.getInstance().getBytesState().setMaxBytes();
    }

}
