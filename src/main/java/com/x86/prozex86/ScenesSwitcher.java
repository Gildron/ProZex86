package com.x86.prozex86;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class ScenesSwitcher {
	public static final String homeScreen = "homeScreen.fxml";
	public static final String cpuMiniGameScreen = "cpuMiniGameScreen.fxml";
	public static final String ramMiniGameScreen = "ramMiniGameScreen.fxml";
	public static final String settingsScreen = "settings.fxml";
	public static final String tutorialScreen = "tutorial.fxml";

	public static int scenewidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int sceneheight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static String os = System.getProperty("os.name");

	public static Stage defaultStage;

	public static void switchScene(SceneType scene) {
		TimerSingleton.getInstance().cancelSceneTimers();

		Stage currentStage = defaultStage;
		String resourcePath = switch (scene) {
			case Home -> homeScreen;
			case CPUMinigame -> cpuMiniGameScreen;
			case RAMMinigame -> ramMiniGameScreen;
			case Settings -> settingsScreen;
			case Tutorial -> tutorialScreen;
		};

		try {
			var loader = new FXMLLoader(Main.class.getResource(resourcePath));
			int sceneHeight = os.contains("Windows") ? sceneheight : sceneheight - 40;
			Scene newScene = new Scene(loader.load(), scenewidth, sceneHeight);
			currentStage.setScene(newScene);

			if (os.contains("Windows")) {
				currentStage.setResizable(false);
				currentStage.setFullScreen(true);
			} else {
				currentStage.setMaximized(true);
			}

			currentStage.setTitle("ProZex86!");
			currentStage.centerOnScreen();
			currentStage.setFullScreenExitHint("");

			currentStage.show();
//			currentStage.setFullScreen(true);
//			currentStage.setMaximized(true);
		} catch (IOException ex) {
			System.err.println("Failed to load game screen resource: " + resourcePath);
			ex.printStackTrace();
		}
	}
}
