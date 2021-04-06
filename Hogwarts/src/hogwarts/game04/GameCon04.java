package hogwarts.game04;

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
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GameCon04 implements Initializable {
	@FXML AnchorPane mainPane;
	@FXML Label lblHome, lblTitle, lblScore, lblSpell;
	@FXML ImageView life1, life2, life3, keyUp, keyDown, keyLeft, keyRight,
				imgKey1, imgKey2, imgKey3, imgKey4, imgKey5, imgKey6;
	@FXML TextField txtField;
	CommonService cs;
	List<String> spells;
	List<ImageView> keys;
	List<Integer> givenKeys;
	Timer timer;
	TimerTask tt;
	Random rand;
	Image quizImg;
	int score, life, listIdx, keyNum, cycleIdx, numAnswer;
	String spell, keyPressed;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cs = new CommonServiceImpl();
		spells = new ArrayList<String>();
		keys  = new ArrayList<ImageView>();
		givenKeys = new ArrayList<Integer>();
		timer = new Timer();
		rand = new Random();
		score = 0;
		life = 3;
		numAnswer = 0;
		lblScale();
		setGameTitle();
		setSpells();
		setKeys();
		callSpell();
		callKeys();
		tfEvent();
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
			life1.setVisible(false);
			life--;
			cs.alert("Game Over!\n"
					+ "You defeated " + score + " enemies in the battle!");
			loadPage("../mainPage");
			timer.cancel();
		}
	}
	
	private void getScore() {
		score++;
		lblScore.setText(Integer.toString(score));
	}
	
	private void chkKeys(int n) {
		if(givenKeys.get(cycleIdx) == n) {
			keys.get(cycleIdx).setEffect(new Bloom());
			numAnswer++;
		} else {
			keys.get(cycleIdx).setImage(new Image("file:resources/img/game04/key"+givenKeys.get(cycleIdx)+"gray.png"));
			countdown();
		}
	}
	
	public void tfEvent() {
		txtField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(!event.getCode().isArrowKey()) { System.out.println("잘못된입력"); return; }
				keyPressed = event.getCode().toString();
				if(keyPressed.equals("UP")) {
					keyUp.setEffect(new Glow());
					chkKeys(2);
				} else if(keyPressed.equals("DOWN")) {
					keyDown.setEffect(new Glow());
					chkKeys(4);
				} else if(keyPressed.equals("LEFT")) {
					keyLeft.setEffect(new Glow());
					chkKeys(1);
				} else if(keyPressed.equals("RIGHT")) {
					keyRight.setEffect(new Glow());
					chkKeys(3);
				}
				cycleIdx++;
				if(cycleIdx == 6) { txtField.setDisable(true); }
				tt = new TimerTask() {
					@Override
					public void run() {
						Platform.runLater(() -> {
							keyUp.setEffect(new Blend());
							keyDown.setEffect(new Blend());
							keyLeft.setEffect(new Blend());
							keyRight.setEffect(new Blend());
						});
					}
				};
				if(life > 0) { timer.schedule(tt, 100); }
			}
		});
	}
	
	private void callKeys() {
		tt = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(numAnswer == 6) { getScore(); }
					numAnswer = 0;
					cycleIdx = 0;
					txtField.setDisable(false);
					txtField.requestFocus();
					for(int i=0; i<6; i++) {
						keys.get(i).setEffect(new Blend());
						keyNum = rand.nextInt(4) + 1;
						quizImg = new Image("file:resources/img/game04/key" + keyNum + ".png");
						keys.get(i).setImage(quizImg);
						givenKeys.set(i, keyNum);
					};
				});
			}
		};
		timer.schedule(tt, 1, 2500);
	}
	
	private void callSpell() {
		tt = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					listIdx = rand.nextInt(spells.size());
					spell = spells.get(listIdx);
					lblSpell.setText(spell);
				});
			}
		};
		timer.schedule(tt, 1, 2500);
	}
	
	private void setKeys() {
		keys.add(imgKey1);
		keys.add(imgKey2);
		keys.add(imgKey3);
		keys.add(imgKey4);
		keys.add(imgKey5);
		keys.add(imgKey6);
		for(int i=0; i<6; i++) {
			givenKeys.add(0);
		}
	}
	
	private void setSpells() {
		spells.add("Bombarda Maxima");
		spells.add("Confringo");
		spells.add("Confundo");
		spells.add("Expelliarmus");
		spells.add("Incarcerous");
		spells.add("Immobulus");
		spells.add("Impedimenta");
		spells.add("Petrificus Totalus");
		spells.add("Protego");
		spells.add("Reducto");
		spells.add("Sectumsempra");
		spells.add("Stupefy");
	}
	
	public void goHome() {
		Optional<ButtonType> result = cs.alertConfirm("Go back to the main page.");
		if(result.get() == ButtonType.OK) {
			timer.cancel();
			loadPage("../mainPage");
		} else { return; }
	}
	
	private void setGameTitle() {
		lblTitle.setText("Battle of Hogwarts");
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
