package com.x86.prozex86;

import com.x86.prozex86.components.BytesState;
import com.x86.prozex86.components.CPUComponent;
import com.x86.prozex86.components.RAMComponent;
import com.x86.prozex86.components.StorageComponent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.*;

public class HomeScreenController {

	//region Variables
	@FXML
	public ImageView imageView_cpu;
	@FXML
	public ImageView imageView_ram;
	@FXML
	public ImageView imageView_storage;
	@FXML
	public ProgressBar progressBar_main;
	@FXML
	public BorderPane root;
	@FXML
	public VBox vBox_progressBar_main;
	@FXML
	public HBox hBox_progressBarLabels_main;
	@FXML
	public Label label_progressBar_bytes_value;
	@FXML
	public HBox hBox_SubScreen_CPU;
	@FXML
	public Label label_UpgradesSubScreen_CPU_Lvl;
	@FXML
	public HBox hBox_SubScreen_STORAGE;
	@FXML
	public Label label_UpgradesSubScreen_STORAGE_Lvl;
	@FXML
	public Label label_UpgradesSubScreen_RAM_LvlValue;
	@FXML
	public Label label_UpgradesSubScreen_CPU_LvlValue;
	@FXML
	public Label label_UpgradesSubScreen_STORAGE_LvlValue;
	@FXML
	public VBox vBox_upgrades;
	@FXML
	public Image image_UpgradesSubScreen_CPU;
	@FXML
	public Image image_UpgradesSubScreen_STORAGE;
	@FXML
	public ProgressBar progressBar_UpgradesSubScreen_RAM_Exp;
	@FXML
	public Label label_UpgradesSubScreen_ProgressBar_RAM_Exp_value1;
	@FXML
	public Label label_UpgradesSubScreen_ProgressBar_RAM_Exp_value2;
	@FXML
	public ProgressBar progressBar_UpgradesSubScreen_CPU_Exp;
	@FXML
	public Label label_UpgradesSubScreen_ProgressBar_CPU_Exp_value1;
	@FXML
	public Label label_UpgradesSubScreen_ProgressBar_CPU_Exp_value2;
	@FXML
	private Button button_cpu;
	@FXML
	public ProgressBar progressBar_UpgradesSubScreen_STORAGE_Exp;
	@FXML
	public Label label_UpgradesSubScreen_ProgressBar_STORAGE_Exp_value1;
	@FXML
	public Label label_UpgradesSubScreen_ProgressBar_STORAGE_Exp_value2;
	@FXML
	private Button button_ram;
	@FXML
	private StackPane stackPane_CPU;
	@FXML
	private StackPane stackPane_RAM;
	@FXML
	private AnchorPane gameField;
	@FXML
	private ScrollPane scrollGameField;
	private int maxProgressBarPoints;
	private int currentProgressBarPoints;
	private final int xpCost = 16;
	private static Timer progressBarTimer = null;
	private Timer gameTimeTimer = null;
	//endregion

	private final StorageComponent storageComponent;
	private final BytesState bytesState;
	private final RAMComponent ramComponent;
	private final CPUComponent cpuComponent;

	public static HomeScreenController currentHomeScreenControllerNOTSingleton;

	public HomeScreenController() {
		currentHomeScreenControllerNOTSingleton = this;

		ramComponent = SaveStateSingleton.getInstance().getRamComponent();
		cpuComponent = SaveStateSingleton.getInstance().getCpuComponent();
		storageComponent = SaveStateSingleton.getInstance().getStorageComponent();
		bytesState = SaveStateSingleton.getInstance().getBytesState();
	}

	@FXML
	private void initialize() {
		stackPane_CPU.setAlignment(button_cpu, Pos.BOTTOM_CENTER);
		stackPane_RAM.setAlignment(button_ram, Pos.BOTTOM_CENTER);

		configureProgressBar();
		updateCompontentLevelingBars();
		setFocusOnComponent(Components.CPU);

		updateProgressBarSpeed();

		if (gameTimeTimer == null) {
			gameTimeTimer = new Timer();
			gameTimeTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					updateLabels();
					updateCompontentLevelingBars();
					updateProgressBarLabels();

				}
			}, 0, 1);
		}
		TimerSingleton.getInstance().addSceneTimer(gameTimeTimer);
	}

	private void updateCompontentLevelingBars() {
		progressBar_UpgradesSubScreen_CPU_Exp.setProgress((double) cpuComponent.getXP()
			/ cpuComponent.getMaxXP());
		progressBar_UpgradesSubScreen_RAM_Exp.setProgress((double) ramComponent.getXP()
			/ ramComponent.getMaxXP());
		progressBar_UpgradesSubScreen_STORAGE_Exp.setProgress((double) storageComponent.getXP() /
			storageComponent.getMaxXP());
	}

	private void configureProgressBar() {
		setMaxPoints(50);
		setCurrentPoints(0);
		progressBar_main.setProgress(calculateProgressBarValue());
	}

	public void updateProgressBarSpeed() {
		if (progressBarTimer != null) {
			progressBarTimer.cancel();
		}

		int period = (int) Math.ceil(1000. / (SaveStateSingleton.getInstance().getCpuComponent().getTickRate() * (double) this.maxProgressBarPoints));

		progressBarTimer = new Timer();
		progressBarTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				updateProgressBarPoints();
				updateProgressBar();
				updateProgressBarLabels();
			}
		}, 0, period);
		TimerSingleton.getInstance().addLongTimer(progressBarTimer);
	}

	public void updateLabels() {
		Platform.runLater(() -> {
			label_UpgradesSubScreen_RAM_LvlValue.setText(String.valueOf(ramComponent.getLevel()));
			label_UpgradesSubScreen_ProgressBar_RAM_Exp_value1.setText(String.valueOf(ramComponent.getXP()));
			label_UpgradesSubScreen_ProgressBar_RAM_Exp_value2.setText(String.valueOf(ramComponent.getMaxXP()));
			label_UpgradesSubScreen_CPU_LvlValue.setText(String.valueOf(cpuComponent.getLevel()));
			label_UpgradesSubScreen_ProgressBar_CPU_Exp_value1.setText(String.valueOf(cpuComponent.getXP()));
			label_UpgradesSubScreen_ProgressBar_CPU_Exp_value2.setText(String.valueOf(cpuComponent.getMaxXP()));
			label_UpgradesSubScreen_STORAGE_LvlValue.setText(String.valueOf(storageComponent.getLevel()));
			label_UpgradesSubScreen_ProgressBar_STORAGE_Exp_value1.setText(String.valueOf(storageComponent.getXP()));
			label_UpgradesSubScreen_ProgressBar_STORAGE_Exp_value2.setText(String.valueOf(storageComponent.getMaxXP()));

		});
	}

	public void updateProgressBarLabels() {
		Platform.runLater(() -> {
			label_progressBar_bytes_value.setText(bytesState.displayConvertedBytes());
		});
	}

	public void updateProgressBarPoints() {
		if (currentProgressBarPoints < maxProgressBarPoints) {
			currentProgressBarPoints += 1;
		} else {
			bytesState.increaseCurrentBytes();
			setCurrentPoints(0);
		}
	}

	private void updateProgressBar() {
		Platform.runLater(() -> progressBar_main.setProgress(calculateProgressBarValue()));
	}

	public void testFunction(ActionEvent ignored) {
		System.out.println("Noch nicht implementiert");
	}

	private double calculateProgressBarValue() {
		return (double) currentProgressBarPoints / maxProgressBarPoints;
	}

	public void exit(ActionEvent ignored) {
		TimerSingleton.getInstance().cancelAllTimers();
		System.exit(0);
	}

	public void switchSceneCPUGame(ActionEvent ignored) {
		try {
			ScenesSwitcher.switchScene(SceneType.CPUMinigame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buyExpCpu(ActionEvent ignored) {
		if (bytesState.getCurrentBytes() >= xpCost) {
			if (bytesState.decreaseCurrentBytes(xpCost)) {
				cpuComponent.increaseCurrentXP();
			}
		}
	}

	public void buyExpRam(ActionEvent ignored) {
		if (bytesState.getCurrentBytes() >= xpCost) {
			if (bytesState.decreaseCurrentBytes(xpCost)) {
				ramComponent.increaseCurrentXP();
			}
		}
	}

	public void buyExpStorage(ActionEvent ignored) {
		if (bytesState.getCurrentBytes() >= xpCost) {
			if (bytesState.decreaseCurrentBytes(xpCost)) {
				storageComponent.increaseCurrentXP();
			}
		}
	}

	public void buyMaxExpCpu(ActionEvent ignored) {
		int xp = bytesState.getCurrentBytes() / this.xpCost;
		bytesState.decreaseCurrentBytes(xp * xpCost);
		cpuComponent.increaseCurrentXP(xp);
	}

	public void buyMaxExpRam(ActionEvent ignored) {
		int xp = bytesState.getCurrentBytes() / this.xpCost;
		bytesState.decreaseCurrentBytes(xp * xpCost);
		ramComponent.increaseCurrentXP(xp);
	}

	public void buyMaxExpStorage(ActionEvent ignored) {
		int xp = bytesState.getCurrentBytes() / this.xpCost;
		bytesState.decreaseCurrentBytes(xp * xpCost);
		storageComponent.increaseCurrentXP(xp);
	}

	public void switchSceneRAMGame(ActionEvent ignored) {
		try {
			ScenesSwitcher.switchScene(SceneType.RAMMinigame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void switchSceneSettings(MouseEvent ignored) {
		try {
			ScenesSwitcher.switchScene(SceneType.Settings);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void switchSceneTutorial(MouseEvent ignored) {
		try {
			ScenesSwitcher.switchScene(SceneType.Tutorial);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void centerOnCPU(MouseEvent ignored) {
		setFocusOnComponent(Components.CPU);
	}

	public void centerOnRAM(MouseEvent ignored) {
		setFocusOnComponent(Components.RAM);
	}

	public void centerOnStorage(MouseEvent ignored) {
		setFocusOnComponent(Components.STORAGE);
	}

	public void setFocusOnComponent(Components component) {
		double xpo = 0, ypo = 0;
		switch (component) {
			case CPU:
				xpo = stackPane_CPU.getLayoutX() + stackPane_CPU.getWidth() / 2.0;
				ypo = stackPane_CPU.getLayoutY() + stackPane_CPU.getHeight() / 2.0;
				break;
			case RAM:
				xpo = stackPane_RAM.getLayoutX() + stackPane_RAM.getWidth() / 2.0;
				ypo = stackPane_RAM.getLayoutY() + stackPane_RAM.getHeight() / 2.0;
				break;
			case STORAGE:
				break;
		}
		setGameFieldPosition(xpo, ypo);
	}

	public void setGameFieldPosition(double xpo, double ypo) {
		double hValue = (xpo - scrollGameField.getViewportBounds().getWidth() / 2.0) / (scrollGameField.getContent().getBoundsInLocal().getWidth() - scrollGameField.getViewportBounds().getWidth());
		double vValue = (ypo - scrollGameField.getViewportBounds().getHeight() / 2.0) / (scrollGameField.getContent().getBoundsInLocal().getHeight() - scrollGameField.getViewportBounds().getHeight());

		hValue = Math.min(Math.max(hValue, 0), 1);
		vValue = Math.min(Math.max(vValue, 0), 1);

		scrollGameField.setHvalue(hValue);
		scrollGameField.setVvalue(vValue);
	}

	public void setMaxPoints(int maxPoints) {
		this.maxProgressBarPoints = maxPoints;
	}

	public void setCurrentPoints(int currentPoints) {
		this.currentProgressBarPoints = currentPoints;
	}


	public void buyExpGPU(ActionEvent event) {
	}

	public void buyMaxExpGPU(ActionEvent event) {
	}

	public void buyExpPSU(ActionEvent event) {
	}

	public void buyMaxExpPSU(ActionEvent event) {
	}

	public void centerOnPSU(MouseEvent mouseEvent) {
	}

	public void centerOnGPU(MouseEvent mouseEvent) {
	}
}
