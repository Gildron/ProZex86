package com.x86.prozex86;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MediaPlayerController {

	private Media media;
	private static MediaPlayer mediaPlayer;

	public MediaPlayerController() {
		init();
	}

	private void init() {
		//Initialising path of the media file, replace this with your file path
		String path = "src/main/resources/com/x86/prozex86/music/SuperMarioBrosmedley.mp3";
		//Instantiating Media class
		media = new Media(new File(path).toURI().toString());
		//Instantiating MediaPlayer class
		mediaPlayer = new MediaPlayer(media);
		//by setting this property to true, the audio will be played
		mediaPlayer.setVolume(SaveStateSingleton.getInstance().getVolume());
		mediaPlayer.setAutoPlay(true);
	}

	public static void setMediaPlayerVolume(double volume) {
		mediaPlayer.setVolume(volume);
		SaveStateSingleton.getInstance().setVolume(volume);
		if(volume > 0.0){
			SaveStateSingleton.getInstance().setVolumeMuted(false);
		}
	}

	public static void setMediaPlayerMute() {
		mediaPlayer.setMute(true);
		SaveStateSingleton.getInstance().setVolume(0.0);
		SaveStateSingleton.getInstance().setVolumeMuted(true);
	}

	public static void setMediaPlayerUnmute() {
		mediaPlayer.setMute(false);
		SaveStateSingleton.getInstance().setVolume(mediaPlayer.getVolume());
		SaveStateSingleton.getInstance().setVolumeMuted(false);
	}
}
