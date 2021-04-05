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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameCon02 implements Initializable {
	@FXML private AnchorPane mainPane, paneOrb1, paneOrb2, paneOrb3, paneOrb4, paneOrb5;
	@FXML private Label lblHome, lblTitle, word1, word2, word3, word4, word5, lblScore;
	@FXML private TextField txtField;
	@FXML private ImageView life1, life2, life3;
	CommonService cs;
	List<String> words, txtMatch;
	Timer timer, timer2;
	TimerTask tt, t1, t2, t3, t4, tc1, tc2, tc3, tc4, tc5;
	File wordTxt;
	BufferedReader reader;
	Random rand;
	int life, score;
	String inputTxt;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		score = 0;
		life = 3;
		cs = new CommonServiceImpl();
		timer = new Timer();
		timer2 = new Timer();
		words = new ArrayList<String>();
		txtMatch = new ArrayList<String>();
		wordTxt = new File(".\\resources\\words.txt");
		setGameTitle();
		txtFieldEvent();
		callWords();
		lblScale();
		setOrbs();
	}
	
	private void countdown() {
		if(life <= 0) { return; }
		if(life3.isVisible()) {
			life3.setVisible(false);
			life--;
		} else if(life2.isVisible()) {
			life2.setVisible(false);
			life--;
		} else {
			life3.setVisible(false);
			life--;
			txtField.setDisable(true);
			cs.alert("Game Over!\n"
					+ "You've saved " + score + " glass orbs!");
			loadPage("../mainPage");
		}
	}
	
	private void setScore(int n) {
		lblScore.setText(Integer.toString(n));
	}
	
	private void compareTxts(String txt) {
		if(txt.equals(word1.getText())) {
			paneOrb1.setVisible(false);
			txtMatch.set(0, "dead");
			score++;
		} else if(txt.equals(word2.getText())) {
			paneOrb2.setVisible(false);
			txtMatch.set(1, "dead");
			score++;
		} else if(txt.equals(word3.getText())) {
			paneOrb3.setVisible(false);
			txtMatch.set(2, "dead");
			score++;
		} else if(txt.equals(word4.getText())) {
			paneOrb4.setVisible(false);
			txtMatch.set(3, "dead");
			score++;
		} else if(txt.equals(word5.getText())) {
			paneOrb5.setVisible(false);
			txtMatch.set(4, "dead");
			score++;
		}
		setScore(score);
	}
	
	private void txtFieldEvent() {
		txtField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER) {
					inputTxt = txtField.getText();
					compareTxts(inputTxt);
					txtField.clear();
				}
			}
		});
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
		for(int i=0; i<5; i++) { txtMatch.add("alive"); }
		tt = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer.cancel(); }
					txtMatch.set(1, "alive");
					paneOrb2.setVisible(true);
					fallDown(paneOrb2, 150);
					putWords(word2);
				});
			}
		};
		timer.schedule(tt, 1, 15000);
		t1 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer.cancel(); }
					txtMatch.set(0, "alive");
					paneOrb1.setVisible(true);
					fallDown(paneOrb1, 150);
					putWords(word1);
				});
			}
		};
		timer.schedule(t1, 3000, 15000);
		t2 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer.cancel(); }
					txtMatch.set(4, "alive");
					paneOrb5.setVisible(true);
					fallDown(paneOrb5, 150);
					putWords(word5);
				});
			}
		};
		timer.schedule(t2, 6000, 15000);
		t3 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer.cancel(); }
					txtMatch.set(2, "alive");
					paneOrb3.setVisible(true);
					fallDown(paneOrb3, 150);
					putWords(word3);
				});
			}
		};
		timer.schedule(t3, 9000, 15000);
		t4 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer.cancel(); }
					txtMatch.set(3, "alive");
					paneOrb4.setVisible(true);
					fallDown(paneOrb4, 150);
					putWords(word4);
				});
			}
		};
		timer.schedule(t4, 12000, 15000);
		
		tc1 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer2.cancel(); }
					if(txtMatch.get(1).equals("alive")) {
						countdown();
					}
				});
			}
		};
		timer2.schedule(tc1, 13000, 15000);
		tc2 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer2.cancel(); }
					if(txtMatch.get(0).equals("alive")) { countdown(); }
				});
			}
		};
		timer2.schedule(tc2, 16000, 15000);
		tc3 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer2.cancel(); }
					if(txtMatch.get(4).equals("alive")) { countdown(); }
				});
			}
		};
		timer2.schedule(tc3, 19000, 15000);
		tc4 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer2.cancel(); }
					if(txtMatch.get(2).equals("alive")) { countdown(); }
				});
			}
		};
		timer2.schedule(tc4, 22000, 15000);
		tc5 = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer2.cancel(); }
					if(txtMatch.get(3).equals("alive")) { countdown(); }
				});
			}
		};
		timer2.schedule(tc5, 25000, 15000);
	}

	private void fallDown(AnchorPane orb, int axis) {
		Path path = new Path();
		path.getElements().add(new MoveTo(axis, -30));
		path.getElements().add(new CubicCurveTo(axis + 100, 300, axis - 100, 600, axis, 1200));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(15000));
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
			timer.cancel();
			timer2.cancel();
			loadPage("../MainPage");
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
