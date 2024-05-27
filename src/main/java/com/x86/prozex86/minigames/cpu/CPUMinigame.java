package com.x86.prozex86.minigames.cpu;

public class CPUMinigame {
	private final Program program;
	private final RegisterFile registerFile;

	private int instructionCounter = 0;

	private static final CPUMinigame[] games = new CPUMinigame[]{
		new CPUMinigame(
			new Program(new Instruction[]{
				new Instruction("mov $1, %AX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 1),
					new GeneralPurposeRegister("BX", 0),
					new GeneralPurposeRegister("CX", 0),
					new GeneralPurposeRegister("DX", 0),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("mov $2, %CX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 1),
					new GeneralPurposeRegister("BX", 0),
					new GeneralPurposeRegister("CX", 2),
					new GeneralPurposeRegister("DX", 0),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("add %DX, %AX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 1),
					new GeneralPurposeRegister("BX", 0),
					new GeneralPurposeRegister("CX", 2),
					new GeneralPurposeRegister("DX", 0),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("mov %AX, %BX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 1),
					new GeneralPurposeRegister("BX", 1),
					new GeneralPurposeRegister("CX", 2),
					new GeneralPurposeRegister("DX", 0),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("add %CX, %BX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 1),
					new GeneralPurposeRegister("BX", 3),
					new GeneralPurposeRegister("CX", 2),
					new GeneralPurposeRegister("DX", 0),
					new GeneralPurposeRegister("IP", 0),
				})),
			}),
			RegisterFile.BASIC_16.clone()
		),
		new CPUMinigame(
			new Program(new Instruction[]{
				new Instruction("mov $4, %CX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 0),
					new GeneralPurposeRegister("BX", 0),
					new GeneralPurposeRegister("CX", 4),
					new GeneralPurposeRegister("DX", 0),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("or %CX, %AX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 4),
					new GeneralPurposeRegister("BX", 0),
					new GeneralPurposeRegister("CX", 4),
					new GeneralPurposeRegister("DX", 0),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("mov $13, %DX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 4),
					new GeneralPurposeRegister("BX", 0),
					new GeneralPurposeRegister("CX", 4),
					new GeneralPurposeRegister("DX", 13),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("and %AX, %CX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 4),
					new GeneralPurposeRegister("BX", 0),
					new GeneralPurposeRegister("CX", 4),
					new GeneralPurposeRegister("DX", 13),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("xor %AX, %AX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 0),
					new GeneralPurposeRegister("BX", 0),
					new GeneralPurposeRegister("CX", 4),
					new GeneralPurposeRegister("DX", 13),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("mov %DX, %CX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 0),
					new GeneralPurposeRegister("BX", 0),
					new GeneralPurposeRegister("CX", 13),
					new GeneralPurposeRegister("DX", 13),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("mov %CX, %BX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 0),
					new GeneralPurposeRegister("BX", 13),
					new GeneralPurposeRegister("CX", 13),
					new GeneralPurposeRegister("DX", 13),
					new GeneralPurposeRegister("IP", 0),
				})),
				new Instruction("xor %BX, %DX", new RegisterFile(new GeneralPurposeRegister[]{
					new GeneralPurposeRegister("AX", 0),
					new GeneralPurposeRegister("BX", 13),
					new GeneralPurposeRegister("CX", 13),
					new GeneralPurposeRegister("DX", 0),
					new GeneralPurposeRegister("IP", 0),
				})),
			}),
			RegisterFile.BASIC_16.clone()
		),
	};

	public static CPUMinigame getCPUMinigame(int stage) {
		stage = Math.min(stage, games.length - 1);
		return games[stage];
	}

	private CPUMinigame(Program program, RegisterFile registerFile) {
		this.program = program;
		this.registerFile = registerFile;
	}

	public RegisterFile getRegisterFile() {
		return this.registerFile;
	}

	public Program getProgram() {
		return this.program;
	}

	public int getCurrentInstructionCounter() {
		return this.instructionCounter;
	}

	/**
	 * Try to step to the next instruction.
	 *
	 * @return false on failure
	 */
	public boolean step() {
		if (this.program.instructions[this.instructionCounter].test(this)) {
			++this.instructionCounter;
			return true;
		} else {
			return false;
		}
	}

	public boolean isDone() {
		return this.instructionCounter == this.program.instructions.length;
	}
}
