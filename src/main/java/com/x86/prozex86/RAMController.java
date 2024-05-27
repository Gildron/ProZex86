package com.x86.prozex86;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.util.Timer;
import java.util.TimerTask;

public class RAMController {
    @FXML
    public BorderPane root;
    @FXML
    public Label label_RAMMinigame_ziffernfolge1;
    @FXML
    public Label label_RAMMinigame_ziffernfolge2;
    @FXML
    public Label label_hint;
    @FXML
    public Label timer;
    @FXML
    public Label label_hint_userInput;
    @FXML
    public TextField textField_RAMMinigame_userInput;
    @FXML
    public Button button_RAMMinigame_check;
    @FXML
    public ImageView imageView_RAMMinigame_true;
    @FXML
    public ImageView imageView_RAMMinigame_false;
    private FXMLLoader fxmlLoader;
    private double rootWidth;
    private double rootHeight;
    private int timerValue = 10;
    private int timerTickRate = 1000;
    private boolean resultTrue = false;
    private boolean resultFalse = false;
    private int laengeZiffernfolge = 7;
    private String ziffernfolge = "";
    private int ramXP;
    private int increasedXPOnWin = 1;
    private Timer CountdownTimer;

    @FXML
    private void initialize() {
        rootWidth = root.getWidth();
        rootHeight = root.getHeight();

        generateZiffernfolge(laengeZiffernfolge);
        label_RAMMinigame_ziffernfolge2.setText(ziffernfolge);

        if (CountdownTimer == null) {
            CountdownTimer = new Timer();

            //Timer für den Countdown
            CountdownTimer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            //TimerLbl inital setzen + countdown---
                            int tempTimerValue = timerValue;
                            if (tempTimerValue == timerValue) {
                                setTimerLabel(tempTimerValue);
                            }
                            if (timerValue > 0) {
                                decreaseTimerValue();
                                setTimerLabel(timerValue);
                                visibilityOnOpenPage();
                            } else {
                                visibilityWhenTimer0();
                            }
                            //TimerLbl inital setzen + countdown+++
                        }
                    }, 0, timerTickRate);
        }
        TimerSingleton.getInstance().addSceneTimer(CountdownTimer);
    }

    private void generateZiffernfolge(int laengeZiffernfolge) {
        int max = 9;
        int min = 0;

        for (int i = 0; i < laengeZiffernfolge; i++) {
            int range = (max - min) + 1;
            int randomZiffer = (int) (Math.random() * range) + min;

            ziffernfolge += randomZiffer;
        }
    }

    private void visibilityOnOpenPage() {
        label_RAMMinigame_ziffernfolge1.setVisible(true);
        label_RAMMinigame_ziffernfolge2.setVisible(true);
        label_hint.setVisible(true);
        timer.setVisible(true);
        label_hint_userInput.setVisible(false);
        textField_RAMMinigame_userInput.setVisible(false);
        button_RAMMinigame_check.setVisible(false);
        imageView_RAMMinigame_true.setVisible(resultTrue);
        imageView_RAMMinigame_false.setVisible(resultFalse);
    }

    private void visibilityWhenTimer0() {
        label_RAMMinigame_ziffernfolge1.setVisible(false);
        label_RAMMinigame_ziffernfolge2.setVisible(false);
        label_hint.setVisible(false);
        timer.setVisible(false);
        label_hint_userInput.setVisible(true);
        textField_RAMMinigame_userInput.setVisible(true);
        button_RAMMinigame_check.setVisible(true);
        imageView_RAMMinigame_true.setVisible(resultTrue);
        imageView_RAMMinigame_false.setVisible(resultFalse);
    }

    private void decreaseTimerValue() {
        this.timerValue--;
    }

    private void setTimerLabel(int timerValue) {
        Platform.runLater(() -> {
            timer.setText(String.valueOf(timerValue));
        });
    }

    @FXML
    public void returnHome() {
        ScenesSwitcher.switchScene(SceneType.Home);
    }

    @FXML
    public void checkResult() {
        if (textField_RAMMinigame_userInput.getText().equals(label_RAMMinigame_ziffernfolge2.getText())) {
            resultTrue = true;
            resultFalse = false;

            SaveStateSingleton.getInstance().getRamComponent().increaseCurrentXP(increasedXPOnWin);

        } else {
            resultFalse = true;
            resultTrue = false;
        }
        button_RAMMinigame_check.setDisable(true);
    }

    public void GoHomeOnEscPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            ScenesSwitcher.switchScene(SceneType.Home);
        }
    }
}
