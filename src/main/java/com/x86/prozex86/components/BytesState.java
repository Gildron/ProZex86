package com.x86.prozex86.components;

import com.x86.prozex86.SaveStateSingleton;

import java.io.Serializable;
import java.util.Random;

public class BytesState implements Serializable {

	private int bytes;
	public int maxBytes;

	public BytesState() {
		this.bytes = 0;
		setMaxBytes();
	}

	public String displayConvertedBytes() {
		String[] prefixes = new String[]{"B", "KiB", "MiB", "GiB", "TiB", "PiB"};
		int bytes = SaveStateSingleton.getInstance().getBytesState().getCurrentBytes();
		int maxBytes = SaveStateSingleton.getInstance().getBytesState().getMaxBytes();

		String res = bytes + " / " + maxBytes + " B";
		for (int i = 1; i < prefixes.length; ++i) {
			int factor = 1 << (10 * i);
			if (maxBytes / factor > 0) {
				res = String.format("%6.3f / %6.3f %s", ((double) bytes) / factor, ((double) maxBytes) / factor, prefixes[i]);
			} else {
				break;
			}
		}

		return res;
	}

	private int randomRound(double num) {
		var rng = new Random();
		int wholePart = (int) Math.floor(num);
		boolean increased = rng.nextDouble() <= (num - (double) wholePart);
		return wholePart + (increased ? 1 : 0);
	}

	public void increaseCurrentBytes() {
		RAMComponent rC = SaveStateSingleton.getInstance().getRamComponent();
		if ((this.bytes += this.randomRound(rC.getByteRate())) > this.maxBytes) {
			this.bytes = this.maxBytes;
		}
		SaveStateSingleton.getInstance().save();
	}

	public boolean decreaseCurrentBytes(int value) {
		if ((this.bytes - value) >= 0) {
			this.bytes -= value;
			SaveStateSingleton.getInstance().save();
			return true;
		} else {
			return false;
		}
	}

	// Bytes
	public int getCurrentBytes() {
		return this.bytes;
	}

	public int getMaxBytes() {
		return this.maxBytes;
	}

	public void setMaxBytes() {
		double level = SaveStateSingleton.getInstance().getStorageComponent().getLevel();
		double value = Math.ceil(Math.pow(2., 5. + (10. / 63.) * (level - 1)));
		this.maxBytes = (int) value;
	}
}