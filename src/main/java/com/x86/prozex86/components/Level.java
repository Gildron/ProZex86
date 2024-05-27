package com.x86.prozex86.components;

import com.x86.prozex86.SaveStateSingleton;

import java.io.Serializable;

public abstract class Level implements Serializable {
	public static final int MAX_LEVEL = 64;

	private int level;
	private int xp;
	private int maxXp;

	protected Level() {
		this.level = 1;
		this.xp = 0;
		this.maxXp = calculateMaxXP();
	}

	/**
	 * Called when a new level is reached. Recalculate all fields that depend on the
	 * level.
	 */
	protected abstract void levelUp();

	private int calculateMaxXP() {
		return (int) Math.pow(this.level, 2);
	}

	public void increaseCurrentXP() {
		increaseCurrentXP(1);
	}

	public void increaseCurrentXP(int amount) {
		while (amount > 0 && this.level < MAX_LEVEL) {
			while (amount > 0) {
				amount -= 1;
				this.xp += 1;

				if (this.xp == this.maxXp) {
					this.level += 1;
					this.xp = 0;
					this.maxXp = this.calculateMaxXP();
					this.levelUp();
					break;
				}
			}
		}

		SaveStateSingleton.getInstance().save();
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.xp = 0;
		this.level = level;
		this.maxXp = this.calculateMaxXP();
		this.levelUp();
		SaveStateSingleton.getInstance().save();
	}

	public int getXP() {
		return this.xp;
	}

	public int getMaxXP() {
		return this.maxXp;
	}
}
