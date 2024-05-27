package com.x86.prozex86;

import com.x86.prozex86.minigames.cpu.CPUMinigame;
import com.x86.prozex86.minigames.cpu.NumberEncoding;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPUMinigameTest {
	@Test
	void testGame0() {
		var game = CPUMinigame.getCPUMinigame(0);

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("AX").setValue("1", NumberEncoding.Binary);
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("CX").setValue("10", NumberEncoding.Binary);
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("BX").setValue("1", NumberEncoding.Binary);
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("BX").setValue("11", NumberEncoding.Binary);
		assertTrue(game.step());

		assertTrue(game.isDone());
	}

	@Test
	void testGame1() {
		var game = CPUMinigame.getCPUMinigame(1);

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("CX").setValue("4", NumberEncoding.Decimal);
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("AX").setValue("4", NumberEncoding.Decimal);
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("DX").setValue("13", NumberEncoding.Decimal);
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("AX").setValue("0", NumberEncoding.Decimal);
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("CX").setValue("13", NumberEncoding.Decimal);
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("BX").setValue("13", NumberEncoding.Decimal);
		assertTrue(game.step());

		assertFalse(game.isDone());
		assertFalse(game.step());
		game.getRegisterFile().get("DX").setValue("0", NumberEncoding.Decimal);
		assertTrue(game.step());

		assertTrue(game.isDone());
	}
}
