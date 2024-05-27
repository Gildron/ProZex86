package com.x86.prozex86;

import com.x86.prozex86.components.*;

import java.io.*;

public class SaveStateSingleton implements Serializable {
	private static SaveStateSingleton instance;
	private StorageComponent storageComponent;
	private BytesState bytesState;
	private RAMComponent ramComponent;
	private CPUComponent cpuComponent;
	private double volume;
	private boolean volumeMuted;

	private SaveStateSingleton() {
	}

	public static SaveStateSingleton getInstance() {
		if (instance == null) {
			instance = new SaveStateSingleton();
			instance.load();
		}
		return instance;
	}

	public void save() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("saveState.ser");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(instance);
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		try (FileInputStream fileInputStream = new FileInputStream("saveState.ser"); ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
			instance = (SaveStateSingleton) objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			storageComponent = new StorageComponent();
			bytesState = new BytesState();
			ramComponent = new RAMComponent();
			cpuComponent = new CPUComponent();
			this.volume = 0;
		} catch (IOException e) {
			File file = new File("saveState.ser");
			if (file.delete()) {
				System.out.println("Savefile was deleted");
			} else {
				e.printStackTrace();
			}
			load();
		} catch (ClassNotFoundException cfe) {
			cfe.printStackTrace();
			File file = new File("saveState.ser");
			if (file.delete()) {
				System.out.println("Savefile was deleted");
			} else {
				cfe.printStackTrace();
			}
			load();
		}
	}

	public StorageComponent getStorageComponent() {
		return storageComponent;
	}

	public BytesState getBytesState() {
		return bytesState;
	}

	public RAMComponent getRamComponent() {
		return ramComponent;
	}

	public CPUComponent getCpuComponent() {
		return cpuComponent;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
		save();
	}

	public boolean isVolumeMuted() {
		return volumeMuted;
	}

	public void setVolumeMuted(boolean volumeMuted) {
		this.volumeMuted = volumeMuted;
		save();
	}
}