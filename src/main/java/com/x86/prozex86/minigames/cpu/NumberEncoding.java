package com.x86.prozex86.minigames.cpu;

public enum NumberEncoding {
	Binary,
	Decimal,
	Hexadecimal;

	public long parseValue(String value) {
		return Long.parseUnsignedLong(value, this.radix());
	}

	public int radix() {
		return switch (this) {
			case Binary -> 2;
			case Decimal -> 10;
			case Hexadecimal -> 16;
		};
	}
}
