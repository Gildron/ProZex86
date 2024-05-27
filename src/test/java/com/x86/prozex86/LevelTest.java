package com.x86.prozex86;

import com.x86.prozex86.components.Level;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevelTest {
	private static class LevelTester extends Level {
		/**
		 * Count how often the levelUp() method got called.
		 */
		public int levelUpEvents = 0;

		@Override
		protected void levelUp() {
			++this.levelUpEvents;
		}
	}

	@Test
	void testDefaults() {
		LevelTester level = new LevelTester();
		assertEquals(1, level.getLevel());
		assertEquals(1, level.getMaxXP());
		assertEquals(0, level.getXP());
	}

	@Test
	public void testMaxLevelling() {
		LevelTester level = new LevelTester();
		int curLevel = level.getLevel();
		int maxXP = level.getMaxXP();

		while (level.getLevel() < Level.MAX_LEVEL) {
			level.increaseCurrentXP(level.getMaxXP());

			// Level should be increased by 1.
			assertEquals(curLevel + 1, level.getLevel());

			// The necessary XP for the next level should always increase.
			assert maxXP < level.getMaxXP();

			// Current XP should always be reset to 0 after a level up.
			assertEquals(0, level.getXP());

			// The levelUp() method should be called for every level up.
			assertEquals(level.getLevel() - 1, level.levelUpEvents);

			curLevel = level.getLevel();
			maxXP = level.getMaxXP();
		}
	}

	/**
	 * Test that increasing the xp amount by more than is needed for the next level will
	 * carry over the right amount of xp to the next level.
	 */
	@Test
	void testOverLevelling() {
		LevelTester level = new LevelTester();

		level.increaseCurrentXP(level.getMaxXP() + 1);

		assertEquals(2, level.getLevel());
		assertEquals(1, level.getXP());
	}

	@Test
	void testSetLevel() {
		int targetLevel = LevelTester.MAX_LEVEL - 1;
		LevelTester level = new LevelTester();

		level.increaseCurrentXP(2);
		level.setLevel(targetLevel);

		assertEquals(targetLevel, level.getLevel());
		assertEquals(0, level.getXP());

		LevelTester shouldBeTheSame = new LevelTester();
		while (shouldBeTheSame.getLevel() < targetLevel) {
			shouldBeTheSame.increaseCurrentXP(shouldBeTheSame.getMaxXP());
		}
		assertEquals(shouldBeTheSame.getLevel(), level.getLevel());
		assertEquals(shouldBeTheSame.getXP(), level.getXP());
		assertEquals(shouldBeTheSame.getMaxXP(), level.getMaxXP());
	}
}
