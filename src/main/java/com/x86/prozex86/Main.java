package com.x86.prozex86;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		// Stop all timer threads when closing the application.
		stage.setOnCloseRequest((event) -> TimerSingleton.getInstance().cancelAllTimers());
		ScenesSwitcher.defaultStage = stage;

		ScenesSwitcher.switchScene(SceneType.Home);

		MediaPlayerController mediaPlayerController = new MediaPlayerController();
	}

	public static void main(String[] args) {
		launch();
	}
}