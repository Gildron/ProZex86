package com.x86.prozex86.minigames.cpu;

public class Instruction {
	public final String instructionText;
	private final RegisterFile expectedResult;

	public Instruction(String instructionText, RegisterFile expectedResult) {
		this.instructionText = instructionText;
		this.expectedResult = expectedResult;
	}

	public boolean test(CPUMinigame game) {
		return game.getRegisterFile().equals(this.expectedResult);
	}
}
