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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class GameCon04 implements Initializable {
	@FXML AnchorPane mainPane;
	@FXML Label lblHome, lblTitle, lblScore, lblSpell;
	@FXML ImageView life1, life2, life3, imgKey1, imgKey2, imgKey3, imgKey4, imgKey5, imgKey6;
	CommonService cs;
	List<String> spells;
	List<ImageView> keys;
	Timer timer;
	TimerTask tt;
	Random rand;
	Image quizImg;
	int score, life, idx, keyNum;
	String spell;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cs = new CommonServiceImpl();
		spells = new ArrayList<String>();
		keys  = new ArrayList<ImageView>();
		timer = new Timer();
		rand = new Random();
		score = 0;
		life = 3;
		lblScale();
		setGameTitle();
		setSpells();
		setKeys();
		callSpell();
		callKeys();
	}
	
	private void callKeys() {
		tt = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					for(ImageView key : keys) {
						keyNum = rand.nextInt(4) + 1;
						quizImg = new Image("file:resources/img/game04/key" + keyNum + ".png");
						key.setImage(quizImg);
					};
				});
			}
		};
		timer.schedule(tt, 1, 3000);
	}
	
	private void callSpell() {
		tt = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					idx = rand.nextInt(spells.size());
					spell = spells.get(idx);
					lblSpell.setText(spell);
				});
			}
		};
		timer.schedule(tt, 1, 3000);
	}
	
	private void setKeys() {
		keys.add(imgKey1);
		keys.add(imgKey2);
		keys.add(imgKey3);
		keys.add(imgKey4);
		keys.add(imgKey5);
		keys.add(imgKey6);
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
			loadPage("../MainPage");
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
