package com.x86.prozex86;

import javafx.css.Size;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;


public class TutorialController {
    @FXML
    public BorderPane root;
    @FXML
    public TextArea textArea;

    private double rootWidth;
    private double rootHeight;
    private Image backgroundImage;
    private BackgroundSize backgroundSize;
    public TutorialController(){

    }

    @FXML
    public void initialize(){
        rootWidth = root.getWidth();
        rootHeight = root.getHeight();
        backgroundSize = new BackgroundSize(rootWidth, rootHeight, false, false, true, true);
        backgroundImage = new Image(this.getClass().getResourceAsStream("/com/x86/prozex86/images/TutorialRolleCustomized.png"));

        configureStyle();
    }

    @FXML
    public void returnHome() {
        ScenesSwitcher.switchScene(SceneType.Home);
    }
    private void configureStyle() {

        root.setBackground(new Background(new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize)));

        textArea.setWrapText(true);
    }
    public void GoHomeOnEscPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE){
            ScenesSwitcher.switchScene(SceneType.Home);
        }
    }
}
