package com.x86.prozex86;

import com.x86.prozex86.components.CPUComponent;
import com.x86.prozex86.components.RAMComponent;
import com.x86.prozex86.components.StorageComponent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SettingsController {
    @FXML
    public Tab generalSettings;
    @FXML
    public Tab graphicSettings;
    @FXML
    public Tab secretDeveloperSettings;
    @FXML
    public TextField cpuLevelInput;
    @FXML
    public TextField ramLevelInput;
    @FXML
    public TextField storageLevelInput;
    @FXML
    public RadioButton soundsettings_muteRadioButton;
    @FXML
    public Slider soundsettings_volumeSlider;

    private CPUComponent cpuComponent;
    private RAMComponent ramComponent;
    private StorageComponent storageComponent;

    public SettingsController() {
        cpuComponent = SaveStateSingleton.getInstance().getCpuComponent();
        ramComponent = SaveStateSingleton.getInstance().getRamComponent();
        storageComponent = SaveStateSingleton.getInstance().getStorageComponent();
    }

    @FXML
    public void initialize() {
        getComponentLevelsFromSingleton();
        insetInputListeners();

        configureRadioButton_muted();
        configureSlider();

        soundsettings_muteRadioButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                soundsettings_volumeSlider.setDisable(newValue);
            }
        });
        soundsettings_volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                soundsettings_volumeSlider.setValue((Double) newValue);
                MediaPlayerController.setMediaPlayerVolume((Double) newValue);
            }
        });
    }

    private void configureRadioButton_muted() {
        if (SaveStateSingleton.getInstance().isVolumeMuted()) {
            soundsettings_muteRadioButton.setSelected(true);
        }
    }

    private void configureSlider() {
        soundsettings_volumeSlider.setMin(0.0);
        soundsettings_volumeSlider.setMax(1.0);
        soundsettings_volumeSlider.setValue(SaveStateSingleton.getInstance().getVolume());
        if (soundsettings_muteRadioButton.isSelected()) {
            soundsettings_volumeSlider.setDisable(true);
        }
    }

    private void getComponentLevelsFromSingleton() {
        cpuLevelInput.setText(String.valueOf(cpuComponent.getLevel()));
        ramLevelInput.setText(String.valueOf(ramComponent.getLevel()));
        storageLevelInput.setText(String.valueOf(storageComponent.getLevel()));
    }

    private void insetInputListeners() {
        cpuLevelInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cpuLevelInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!newValue.isEmpty()) {
                cpuComponent.setLevel(Integer.parseInt(newValue));
            }
        });
        ramLevelInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ramLevelInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!newValue.isEmpty()) {
                ramComponent.setLevel(Integer.parseInt(newValue));
            }
        });
        storageLevelInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                storageLevelInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!newValue.isEmpty()) {
                storageComponent.setLevel(Integer.parseInt(newValue));
            }
        });
    }

    public void returnHome(ActionEvent event) {
        ScenesSwitcher.switchScene(SceneType.Home);
    }

    public void setMutedOrUnmuted(ActionEvent actionEvent) {
        if (soundsettings_muteRadioButton.isSelected()) {
            MediaPlayerController.setMediaPlayerMute();
        }
        if (!soundsettings_muteRadioButton.isSelected()) {
            MediaPlayerController.setMediaPlayerUnmute();
        }
    }

    public void GoHomeOnEscPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            ScenesSwitcher.switchScene(SceneType.Home);
        }
    }
}
