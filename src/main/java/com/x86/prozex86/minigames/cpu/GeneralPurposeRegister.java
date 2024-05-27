package com.x86.prozex86.minigames.cpu;

import java.security.InvalidParameterException;

public class GeneralPurposeRegister {
	public final String name;
	public final BitWidth bitWidth;
	private long value = 0;

	public GeneralPurposeRegister(String name) {
		this(name, switch (name.toUpperCase()) {
			case "AX", "BX", "CX", "DX", "SI", "DI", "SP", "BP", "IP", "R8W", "R9W", "R10W", "R11W", "R12W", "R13W", "R14W", "R15W" ->
				BitWidth.Bits16;
			case "EAX", "EBX", "ECX", "EDX", "ESI", "EDI", "ESP", "EBP", "EIP", "R8D", "R9D", "R10D", "R11D", "R12D", "R13D", "R14D", "R15D" ->
				BitWidth.Bits32;
			case "RAX", "RBX", "RCX", "RDX", "RSI", "RDI", "RSP", "RBP", "RIP", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15" ->
				BitWidth.Bits64;
			default -> throw new InvalidParameterException();
		}, 0);
	}

	public GeneralPurposeRegister(String name, long value) {
		this(name);
		this.value = value;
	}

	public GeneralPurposeRegister(String name, BitWidth bitWidth, long value) {
		this.name = name;
		this.bitWidth = bitWidth;
		this.value = value;
	}

	public void setValue(String value, NumberEncoding encoding) {
		long newVal;
		try {
			newVal = encoding.parseValue(value);
		} catch (NumberFormatException nfe) {
			throw new InvalidParameterException();
		}

		// Only accept values that fit in the register.
		if ((this.bitWidth == BitWidth.Bits16 || this.bitWidth == BitWidth.Bits32) && (0 > newVal || newVal > this.bitWidth.maxValue())) {
			throw new InvalidParameterException();
		}

		this.value = newVal;
	}

	public long getValue() {
		return this.value;
	}

	/**
	 * Tests if two registers have the same value.
	 *
	 * @param other A register to compare with.
	 *
	 * @return `true` if both registers have the same value, `false` otherwise
	 */
	public boolean valueEquals(GeneralPurposeRegister other) {
		return other.value == this.value;
	}

	public String toString(NumberEncoding encoding) {
		return switch (encoding) {
			case Binary -> {
				String str = Long.toBinaryString(this.value);
				String zeroPad = "0".repeat(this.bitWidth.numericBitWidth() - str.length());
				yield zeroPad + str;
			}
			case Decimal -> Long.toString(this.value);
			case Hexadecimal ->
				String.format("%0" + this.bitWidth.numericBitWidth() / 4 + "x", this.value);
		};
	}

	public GeneralPurposeRegister clone() {
		return new GeneralPurposeRegister(this.name, this.bitWidth, this.value);
	}
}
