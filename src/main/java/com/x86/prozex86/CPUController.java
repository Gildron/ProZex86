package com.x86.prozex86;

import com.x86.prozex86.minigames.cpu.CPUMinigame;
import com.x86.prozex86.minigames.cpu.NumberEncoding;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CPUController {
	private static int stage = 0;

	private final CPUMinigame minigame;
	private final ArrayList<TextField> userRegisterInputs;

	@FXML
	public VBox instructionsBox;
	@FXML
	public GridPane processorAnchor;
	@FXML
	public Pane root;
	@FXML
	public GridPane userAnchor;
	@FXML
	public VBox registerAnchor;
	@FXML
	public VBox controls;

	public RadioButton binary = new RadioButton("Binär");
	public RadioButton decimal = new RadioButton("Dezimal");
	public RadioButton hexadecimal = new RadioButton("Hexadezimal");
	public ToggleGroup encodingToggleGroup = new ToggleGroup();
	private final double paddingPercentage = 0.1;

	private NumberEncoding currentEncoding;

	public CPUController() throws IllegalAccessException {
		this.currentEncoding = NumberEncoding.Binary;
		binary.setSelected(true);

		this.userRegisterInputs = new ArrayList<>();

		try {
			this.minigame = CPUMinigame.getCPUMinigame(stage);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("[CPU Minigame] Tried to get CPU minigame " + (stage - 1) + " but there are no more minigames.");
			throw new IllegalAccessException();
		}
	}

	@FXML
	public void initialize() {
		showInstructions();
		generateUserInput();
		generateUserOptions();

		addDynamicResize();
	}

	private void addDynamicResize() {
		ChangeListener<Number> sizeListener = (observableValue, number, t1) -> {
			double widthPadding = root.getWidth() * paddingPercentage;
			double heightPadding = root.getHeight() * paddingPercentage;

			processorAnchor.setPadding(new Insets(heightPadding, widthPadding, heightPadding, widthPadding));
			userAnchor.setPadding(new Insets(heightPadding, widthPadding, heightPadding, widthPadding));
		};

		root.widthProperty().addListener(sizeListener);
		root.heightProperty().addListener(sizeListener);
	}

	public void showInstructions() {
		for (var instruction : this.minigame.getProgram().instructions) {
			Pane pane = new Pane();
			Label label = new Label(instruction.instructionText);
			pane.getChildren().add(label);

			instructionsBox.getChildren().add(pane);
		}

		markActiveInstruction();
	}

	public void generateUserInput() {
		registerAnchor.getChildren().clear();

		for (var register : this.minigame.getRegisterFile().getRegisters()) {
			Rectangle optionRectangle = new Rectangle(50, 50, Paint.valueOf("D9D9D9"));
			Text optionText = new Text(register.name);
			StackPane optionStack = new StackPane();
			optionStack.getChildren().addAll(optionRectangle, optionText);

			HBox userField = new HBox();
			userField.setAlignment(Pos.CENTER);

			TextField input = new TextField();
			input.setOnKeyTyped(actionEvent -> {
				register.setValue(input.getText(), this.currentEncoding);
				System.out.println("[CPU Minigame] Updated " + register.name + " to " + register.toString(NumberEncoding.Hexadecimal));
			});

			this.userRegisterInputs.add(input);
			userField.getChildren().add(optionStack);
			userField.getChildren().add(input);

			registerAnchor.getChildren().add(userField);
		}

		this.showRegisterContents();
	}

	private void showRegisterContents() {
		var registerTextFields = this.userRegisterInputs.iterator();
		for (var register : this.minigame.getRegisterFile().getRegisters()) {
			var field = registerTextFields.next();
			field.setText(register.toString(this.currentEncoding));
		}
	}

	public void generateUserOptions() {
		binary.setToggleGroup(encodingToggleGroup);
		binary.setOnAction((ActionEvent e) -> this.selectEncoding(NumberEncoding.Binary));

		decimal.setToggleGroup(encodingToggleGroup);
		decimal.setOnAction((ActionEvent e) -> this.selectEncoding(NumberEncoding.Decimal));

		hexadecimal.setToggleGroup(encodingToggleGroup);
		hexadecimal.setOnAction((ActionEvent e) -> this.selectEncoding(NumberEncoding.Hexadecimal));

		registerAnchor.getChildren().addAll(binary, decimal, hexadecimal);
	}

	public void selectEncoding(NumberEncoding encoding) {
		this.currentEncoding = encoding;
		// Print register values in new encoding.
		this.showRegisterContents();
	}

	@FXML
	public void processorStep() {
		if (!this.minigame.step()) {
			System.err.println("[CPU Minigame] Wrong register state!");
		}

		if (this.minigame.isDone()) {
			++stage;
			SaveStateSingleton.getInstance().getCpuComponent().increaseCurrentXP(1);
			ScenesSwitcher.switchScene(SceneType.Home);
		}

		this.markActiveInstruction();
	}

	/**
	 * Clear all marked instructions and mark the currently active instruction.
	 */
	public void markActiveInstruction() {
		// Clear all.
		for (int i = 0; i < instructionsBox.getChildren().size(); ++i) {
			Pane pane = getProcessorPane(i);
			pane.setBackground(new Background(new BackgroundFill(Paint.valueOf("F4F9FF"), new CornerRadii(0), new Insets(0))));
		}

		// Mark currently active instruction.
		Pane pane = getProcessorPane(this.minigame.getCurrentInstructionCounter());
		pane.setBackground(new Background(new BackgroundFill(Paint.valueOf("D03030"), new CornerRadii(0), new Insets(0))));
	}

	public Pane getProcessorPane(int number) {
		ObservableList<Node> processorOutputChildren = instructionsBox.getChildren();
		Pane pane = (Pane) processorOutputChildren.get(number);
		return pane;
	}

	@FXML
	public void returnHome(ActionEvent event) {
		ScenesSwitcher.switchScene(SceneType.Home);
	}

	public void checkKeyPressed(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			processorStep();
		}
		if (keyEvent.getCode() == KeyCode.ESCAPE) {
			ScenesSwitcher.switchScene(SceneType.Home);
		}
	}
}
