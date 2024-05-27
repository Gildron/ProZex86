package com.x86.prozex86.minigames.cpu;

import com.almasb.fxgl.core.collection.Array;

import java.util.ArrayList;
import java.util.List;

public class RegisterFile {
	public static final RegisterFile BASIC_16 = new RegisterFile(new GeneralPurposeRegister[]{
		new GeneralPurposeRegister("AX"),
		new GeneralPurposeRegister("BX"),
		new GeneralPurposeRegister("CX"),
		new GeneralPurposeRegister("DX"),
		new GeneralPurposeRegister("IP"),
	});

	public static final RegisterFile BASIC_FULL_16 = new RegisterFile(new GeneralPurposeRegister[]{
		new GeneralPurposeRegister("AX"),
		new GeneralPurposeRegister("BX"),
		new GeneralPurposeRegister("CX"),
		new GeneralPurposeRegister("DX"),
		new GeneralPurposeRegister("DI"),
		new GeneralPurposeRegister("SI"),
		new GeneralPurposeRegister("SP"),
		new GeneralPurposeRegister("BP"),
		new GeneralPurposeRegister("IP"),
	});

	private final ArrayList<GeneralPurposeRegister> registers;

	public RegisterFile(GeneralPurposeRegister[] registers) {
		this(new ArrayList<>(List.of(registers)));
	}

	public RegisterFile(ArrayList<GeneralPurposeRegister> registers) {
		this.registers = registers;
	}

	public ArrayList<GeneralPurposeRegister> getRegisters() {
		return this.registers;
	}

	public GeneralPurposeRegister get(String register) {
		for (var reg : this.registers) {
			if (reg.name.equals(register)) {
				return reg;
			}
		}

		return null;
	}

	public boolean equals(RegisterFile other) {
		if (this.getRegisters().size() != other.getRegisters().size()) {
			return false;
		}

		outer:
		for (var expectedRegister : other.getRegisters()) {
			for (var register : this.getRegisters()) {
				if (expectedRegister.name.equals(register.name)) {
					if (expectedRegister.valueEquals(register)) {
						continue outer;
					} else {
						System.out.println("Register value of " + expectedRegister.name + " does not match: " + expectedRegister.toString(NumberEncoding.Hexadecimal) + " expected, got " + register.toString(NumberEncoding.Hexadecimal));
						// Register values don't match.
						return false;
					}
				}
			}

			// False if no matching register name in the game register file is found.
			return false;
		}

		return true;
	}

	public RegisterFile clone() {
		var registers = new ArrayList<GeneralPurposeRegister>();
		for (var register : this.registers) {
			registers.add(register.clone());
		}

		return new RegisterFile(registers);
	}
}
