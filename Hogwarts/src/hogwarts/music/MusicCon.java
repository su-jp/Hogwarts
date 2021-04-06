package hogwarts.music;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MusicCon implements Initializable {
	@FXML MediaView mv;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Media music = new Media(new File("./resources/music.mp3").toURI().toString());
		MediaPlayer mp = new MediaPlayer(music);
		mv.setMediaPlayer(mp);
		mp.setCycleCount(Timeline.INDEFINITE);
		mp.setAutoPlay(true);
	}
}
