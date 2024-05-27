package com.x86.prozex86.minigames.cpu;

public enum BitWidth {
	Bits16,
	Bits32,
	Bits64;

	public int numericBitWidth() {
		return switch(this) {
			case Bits16 -> 16;
			case Bits32 -> 32;
			case Bits64 -> 64;
		};
	}

	public long maxValue() {
		return switch (this) {
			case Bits16, Bits32 -> (1L << this.numericBitWidth()) - 1;
			case Bits64 -> -1L;
		};
	}
}
