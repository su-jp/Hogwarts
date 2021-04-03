package hogwarts.game02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import hogwarts.common.CommonService;
import hogwarts.common.CommonServiceImpl;
import hogwarts.common.MyLabelEventHandler;
import hogwarts.common.MyLabelEventHandler02;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameCon02 implements Initializable {
	@FXML
	AnchorPane mainPane, paneOrb1, paneOrb2, paneOrb3, paneOrb4, paneOrb5;
	@FXML
	Label lblHome, lblTitle, word1, word2, word3, word4, word5;
	CommonService cs;
	List<String> words;
	File wordTxt;
	BufferedReader reader;
	Random rand;
	TimerTask tt, t1, t2, t3, t4;
	int lightening;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cs = new CommonServiceImpl();
		words = new ArrayList<String>();
		wordTxt = new File(".\\resources\\words.txt");
		callWords();
		lightening = 3;
		lblScale();
		setGameTitle();
		setOrbs();
	}
	
	private void putWords(Label word) {
		rand = new Random();
		int idx = rand.nextInt(words.size());
		word.setText(words.get(idx));
	}

	private void callWords() {
		try {
			String s;
			reader = new BufferedReader(new FileReader(wordTxt));
			while ((s = reader.readLine()) != null) {
				words.add(s);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setOrbs() {
		Timer timer = new Timer();
		tt = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					fallDown(paneOrb2, 150);
					putWords(word2);
				});
			}
		};
		timer.schedule(tt, 1, 10000);
		t1 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					fallDown(paneOrb1, 150);
					putWords(word1);
				});
			}
		};
		timer.schedule(t1, 2000, 10000);
		t2 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					fallDown(paneOrb5, 150);
					putWords(word5);
				});
			}
		};
		timer.schedule(t2, 4000, 10000);
		t3 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
				fallDown(paneOrb3, 150);
				putWords(word3);
				});
			}
		};
		timer.schedule(t3, 6000, 10000);
		t4 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					fallDown(paneOrb4, 150);
					putWords(word4);
				});
			}
		};
		timer.schedule(t4, 8000, 10000);
	}

	private void fallDown(AnchorPane orb, int axis) {
		Path path = new Path();
		path.getElements().add(new MoveTo(axis, -30));
		path.getElements().add(new CubicCurveTo(axis + 100, 300, axis - 100, 600, axis, 1200));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(10000));
		pathTransition.setPath(path);
		pathTransition.setNode(orb);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(0);
		pathTransition.setAutoReverse(false);
		pathTransition.play();
	}

	public void goHome() {
		Optional<ButtonType> result = cs.alertConfirm("Go back to the main page.");
		if (result.get() == ButtonType.OK) {
			loadPage("../mainPage");
		} else {
			return;
		}
	}

	private void setGameTitle() {
		lblTitle.setText("Department of Mysteries");
	}

	private void lblScale() {
		lblHome.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyLabelEventHandler());
		lblHome.addEventHandler(MouseEvent.MOUSE_EXITED, new MyLabelEventHandler02());
	}

	private void loadPage(String page) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
			mainPane.getChildren().clear();
			mainPane.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
