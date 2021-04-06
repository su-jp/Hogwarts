package hogwarts.game03;

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
import hogwarts.common.MyImgEventHandler;
import hogwarts.common.MyImgEventHandler02;
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

public class GameCon03 implements Initializable {
	@FXML AnchorPane mainPane;
	@FXML Label lblHome, lblTitle, lblScore;
	@FXML ImageView life1, life2, life3, potionQuiz, potion1, potion2, potion3, potion4, potion5, potion6;
	CommonService cs;
	List<ImageView> potions;
	List<Integer> colors;
	Timer timer;
	TimerTask tt;
	Random rand;
	Image answerImg, quizImg;
	int score, life, answer, listIdx, idxPotion;
	String quizUrl, answerUrl;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cs = new CommonServiceImpl();
		timer = new Timer();
		potions = new ArrayList<ImageView>();
		colors = new ArrayList<Integer>();
		score = 0;
		life = 3;
		lblScale();
		numPotions();
		setGameTitle();
		imgEvent();
		setQuiz();
		setPotions();
	}
	
	public void getPotionNum(MouseEvent arg0) {
		ImageView potion = (ImageView) arg0.getSource();
		turnGray(potion);
		chkEqual(potion);
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
			timer.cancel();
			cs.alert("Game Over!\n"
					+ "You kept " + score + " secrets from Professor Umbridge!");
			loadPage("../mainPage");
		}
	}
	
	private void getScore() {
		score++;
		lblScore.setText(Integer.toString(score));
	}
	
	private void chkEqual(ImageView potion) {
		int select = Integer.parseInt(potion.getId().substring(6)) - 1;
		if(colors.get(select) == answer) {
			getScore();
		} else {
			countdown();
		}
	}
	
	private void turnGray(ImageView img) {
		Image gray = new Image("file:resources/img/game03/potion0.png");
		img.setImage(gray);
	}
	
	private void setPotions() {
		tt = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					if(life <= 0) { timer.cancel(); }
					listIdx = rand.nextInt(6);
					idxPotion = rand.nextInt(6) + 1;
					quizImg = new Image("file:resources/img/game03/potion" + idxPotion + ".png");
					potions.get(listIdx).setImage(quizImg);
					colors.set(listIdx, idxPotion);
				});
			}
		};
		timer.schedule(tt, 500, 500);
	}
	
	private void setQuiz() {
		rand = new Random();
		answer = rand.nextInt(6) + 1;
		answerImg = new Image("file:resources/img/game03/potion" + answer + ".png");
		potionQuiz.setImage(answerImg);
	}
	
	private void numPotions() {
		potions.add(potion1);
		potions.add(potion2);
		potions.add(potion3);
		potions.add(potion4);
		potions.add(potion5);
		potions.add(potion6);
		for(int i=0; i<6; i++) {
			colors.add(0);
		}
	}
	
	private void imgEvent() {
		for(ImageView img : potions) {
			img.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
			img.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		}
	}
	
	public void goHome() {
		Optional<ButtonType> result = cs.alertConfirm("Go back to the main page.");
		if(result.get() == ButtonType.OK) {
			timer.cancel();
			loadPage("../mainPage");
		} else { return; }
	}
	
	private void setGameTitle() {
		lblTitle.setText("I MUST NOT TELL LIES");
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
