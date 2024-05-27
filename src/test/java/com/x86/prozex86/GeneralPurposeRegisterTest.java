package com.x86.prozex86;

import com.x86.prozex86.minigames.cpu.*;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

public class GeneralPurposeRegisterTest {
	@Test
	void testGenerationByName() {
		var ax = new GeneralPurposeRegister("AX");
		assertEquals("AX", ax.name);
		assertEquals(BitWidth.Bits16, ax.bitWidth);
		assertEquals(0, ax.getValue());

		var r9d = new GeneralPurposeRegister("R9D");
		assertEquals("R9D", r9d.name);
		assertEquals(BitWidth.Bits32, r9d.bitWidth);
		assertEquals(0, r9d.getValue());

		var rdi = new GeneralPurposeRegister("rdi");
		assertEquals("rdi", rdi.name);
		assertEquals(BitWidth.Bits64, rdi.bitWidth);
		assertEquals(0, rdi.getValue());
	}

	@Test
	void testZeroAssignment() {
		var rax = new GeneralPurposeRegister("RAX");
		assertEquals(0, rax.getValue());

		rax.setValue("0", NumberEncoding.Binary);
		assertEquals(0, rax.getValue());

		rax.setValue("0", NumberEncoding.Decimal);
		assertEquals(0, rax.getValue());

		rax.setValue("0", NumberEncoding.Hexadecimal);
		assertEquals(0, rax.getValue());
	}

	@Test
	void testBinaryAssignment() {
		var ax = new GeneralPurposeRegister("AX");
		var eax = new GeneralPurposeRegister("EAX");
		var rax = new GeneralPurposeRegister("RAX");

		ax.setValue("1100", NumberEncoding.Binary);
		assertEquals(0b1100L, ax.getValue());
		eax.setValue("1100", NumberEncoding.Binary);
		assertEquals(0b1100L, eax.getValue());
		rax.setValue("1100", NumberEncoding.Binary);
		assertEquals(0b1100L, rax.getValue());

		// max values
		ax.setValue("1111111111111111", NumberEncoding.Binary);
		assertEquals(0b1111111111111111L, ax.getValue());
		eax.setValue("11111111111111111111111111111111", NumberEncoding.Binary);
		assertEquals(0b11111111111111111111111111111111L, eax.getValue());
		rax.setValue("1111111111111111111111111111111111111111111111111111111111111111", NumberEncoding.Binary);
		assertEquals(0b1111111111111111111111111111111111111111111111111111111111111111L, rax.getValue());

		assertThrows(InvalidParameterException.class, () -> ax.setValue("10000000000000000", NumberEncoding.Binary));
		assertThrows(InvalidParameterException.class, () -> eax.setValue("100000000000000000000000000000000", NumberEncoding.Binary));
		assertThrows(InvalidParameterException.class, () -> rax.setValue("10000000000000000000000000000000000000000000000000000000000000000", NumberEncoding.Binary));
	}

	@Test
	void testDecimalAssignment() {
		var ax = new GeneralPurposeRegister("AX");
		var eax = new GeneralPurposeRegister("EAX");
		var rax = new GeneralPurposeRegister("RAX");

		ax.setValue("20", NumberEncoding.Decimal);
		assertEquals(20, ax.getValue());
		eax.setValue("20", NumberEncoding.Decimal);
		assertEquals(20, eax.getValue());
		rax.setValue("20", NumberEncoding.Decimal);
		assertEquals(20, rax.getValue());

		// max values
		ax.setValue("65535", NumberEncoding.Decimal);
		assertEquals((1L << 16) - 1, ax.getValue());
		eax.setValue("4294967295", NumberEncoding.Decimal);
		assertEquals((1L << 32) - 1, eax.getValue());
		rax.setValue("18446744073709551615", NumberEncoding.Decimal);
		assertEquals(-1L, rax.getValue());

		assertThrows(InvalidParameterException.class, () -> ax.setValue("65536", NumberEncoding.Decimal));
		assertThrows(InvalidParameterException.class, () -> eax.setValue("4294967296", NumberEncoding.Decimal));
		assertThrows(InvalidParameterException.class, () -> rax.setValue("18446744073709551616", NumberEncoding.Decimal));
	}

	@Test
	void testHexadecimalAssignment() {
		var ax = new GeneralPurposeRegister("AX");
		var eax = new GeneralPurposeRegister("EAX");
		var rax = new GeneralPurposeRegister("RAX");

		ax.setValue("AB", NumberEncoding.Hexadecimal);
		assertEquals(0xABL, ax.getValue());
		eax.setValue("AB", NumberEncoding.Hexadecimal);
		assertEquals(0xABL, eax.getValue());
		rax.setValue("AB", NumberEncoding.Hexadecimal);
		assertEquals(0xABL, rax.getValue());

		// max values
		ax.setValue("FFFF", NumberEncoding.Hexadecimal);
		assertEquals(0xFFFFL, ax.getValue());
		eax.setValue("FFFFFFFF", NumberEncoding.Hexadecimal);
		assertEquals(0xFFFFFFFFL, eax.getValue());
		rax.setValue("FFFFFFFFFFFFFFFF", NumberEncoding.Hexadecimal);
		assertEquals(0xFFFFFFFFFFFFFFFFL, rax.getValue());

		assertThrows(InvalidParameterException.class, () -> ax.setValue("10000", NumberEncoding.Hexadecimal));
		assertThrows(InvalidParameterException.class, () -> eax.setValue("100000000", NumberEncoding.Hexadecimal));
		assertThrows(InvalidParameterException.class, () -> rax.setValue("10000000000000000", NumberEncoding.Hexadecimal));
	}
}
