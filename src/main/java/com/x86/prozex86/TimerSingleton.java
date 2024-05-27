package com.x86.prozex86;

import java.util.ArrayList;
import java.util.Timer;

public class TimerSingleton {
	private static TimerSingleton instance;
	private final ArrayList<Timer> longTimers;
	private final ArrayList<Timer> sceneTimers;

	private TimerSingleton() {
		sceneTimers = new ArrayList<>();
		longTimers = new ArrayList<>();
	}

	public static TimerSingleton getInstance() {
		if (instance == null) {
			instance = new TimerSingleton();
		}
		return instance;
	}

	/**
	 * Add a timer that won't get cancelled by the resetTimers() method.
	 */
	public void addLongTimer(Timer timer) {
		this.longTimers.add(timer);
	}

	/**
	 * Add a short term timer that should get cancelled when switching scenes.
	 */
	public void addSceneTimer(Timer timer) {
		this.sceneTimers.add(timer);
	}

	public void cancelSceneTimers() {
		for (var timer : this.sceneTimers) {
			timer.cancel();
		}
		this.sceneTimers.clear();
	}

	public void cancelAllTimers() {
		this.cancelSceneTimers();

		for (var timer : this.longTimers) {
			timer.cancel();
		}
		this.longTimers.clear();
	}
}