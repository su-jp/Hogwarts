package hogwarts.game01;

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
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

public class GameCon01 implements Initializable {
	List<ImageView> images;
	List<String> idx;
	@FXML AnchorPane mainPane;
	@FXML Label lblHome, lblTitle, lblScore;
	@FXML ImageView card1, card2, card3, card4, card5, card6, card7, card8,
					card9, card10, card11, card12, card13, card14, card15, card16,
					img1, img2, img3, img4, img5, img6, img7, img8, img9, img10,
					img11, img12, img13, img14, img15, img16;
	CommonService cs;
	FadeTransition ft;
	int score, turn, match, count;
	ImageView cardSelected, cardSelected2;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cs = new CommonServiceImpl();
		images = new ArrayList<ImageView>();
		idx = new ArrayList<String>();
		lblScale();
		imgScale();
		setGameTitle();
		setIndexList();
		setImageList();
		setImages();
		score = 0;
		turn = 0;
		match = 0;
		count = 0;
	}
	
	
	private void setImages() {
		Random rand = new Random();
		int countSet = 0;
		Image cardImg;
		while(countSet < 16) {
			int num = rand.nextInt(16);
			if(idx.get(num) == null) {
				cardImg = new Image("file:resources/img/game01/card" + (countSet+1) + ".png");
				images.get(num).setImage(cardImg);
				countSet++;
				idx.set(num, "Set");
			}
		}
		
	}
	
	private void fadeOut(ImageView card) {
		ft = new FadeTransition(Duration.millis(1000), card);
		ft.setFromValue(1.0); 
		ft.setToValue(0.3);
		ft.setCycleCount(1); 
		ft.setAutoReverse(false); 
		ft.play();
	}
	
	private void matching() {
		if((cardSelected == card1 && cardSelected2 == card8) || 
				(cardSelected == card8 && cardSelected2 == card1)) {
			fadeOut(card1);
			fadeOut(card8);
			match = 1;
			count++;
		} else if((cardSelected == card2 && cardSelected2 == card5) || 
				(cardSelected == card5 && cardSelected2 == card2)) {
			fadeOut(card2);
			fadeOut(card5);
			match = 1;
			count++;
		} else if((cardSelected == card3 && cardSelected2 == card4) || 
				(cardSelected == card4 && cardSelected2 == card3)) {
			fadeOut(card3);
			fadeOut(card4);
			match = 1;
			count++;
		} else if((cardSelected == card6 && cardSelected2 == card13) || 
				(cardSelected == card13 && cardSelected2 == card6)) {
			fadeOut(card6);
			fadeOut(card13);
			match = 1;
			count++;
		} else if((cardSelected == card7 && cardSelected2 == card12) || 
				(cardSelected == card12 && cardSelected2 == card7)) {
			fadeOut(card7);
			fadeOut(card12);
			match = 1;
			count++;
		} else if((cardSelected == card9 && cardSelected2 == card15) || 
				(cardSelected == card15 && cardSelected2 == card9)) {
			fadeOut(card9);
			fadeOut(card15);
			match = 1;
			count++;
		} else if((cardSelected == card10 && cardSelected2 == card11) || 
				(cardSelected == card11 && cardSelected2 == card10)) {
			fadeOut(card11);
			fadeOut(card10);
			match = 1;
			count++;
		} else if((cardSelected == card14 && cardSelected2 == card16) || 
				(cardSelected == card16 && cardSelected2 == card14)) {
			fadeOut(card14);
			fadeOut(card16);
			match = 1;
			count++;
		} else {
//			cardSelected.setVisible(true);
//			cardSelected2.setVisible(true);
		}
		if(count == 8) {
			cs.alert("You've defeated Lord Voldemort with " + (score+1) + " clicks!");
			loadPage("../mainPage");
		}
	}
	
	private void revealCard(ImageView card) {
		turn++;
		card.setVisible(false);
		if(turn > 1) {
			turn = 0;
			cardSelected2 = card;
			matching();
			setCardBack();
		} else if(turn == 1) {
			cardSelected = card;
		}
	}
	
	private void setCardBack() {
		if(match == 1) {
			match--;
		} else {
			Timer timer = new Timer();
			TimerTask tt = new TimerTask() {
				@Override
				public void run() {
					cardSelected.setVisible(true);;
					cardSelected2.setVisible(true);;
				}
			};
			timer.schedule(tt, 300);
		}
	}
	
	private void setScore() {
		score++;
		lblScore.setText(Integer.toString(score));
	}
	
	
	public void getCardNum(MouseEvent arg0) {
		ImageView card = (ImageView) arg0.getSource();
		revealCard(card);
		setScore();
	}
	
	private void setIndexList() {
		for(int i=0; i<16; i++) {
			idx.add(null);
		}
	}

	private void setImageList() {
		images.add(img1);
		images.add(img2);
		images.add(img3);
		images.add(img4);
		images.add(img5);
		images.add(img6);
		images.add(img7);
		images.add(img8);
		images.add(img9);
		images.add(img10);
		images.add(img11);
		images.add(img12);
		images.add(img13);
		images.add(img14);
		images.add(img15);
		images.add(img16);
	}

	public void goHome() {
		Optional<ButtonType> result = cs.alertConfirm("Return to the main page.");
		if(result.get() == ButtonType.OK) {
			loadPage("../mainPage");
		} else { return; }
	}
	
	private void setGameTitle() {
		lblTitle.setText("WARNING : Horcrux");
	}
	
	private void imgScale() {
		card1.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card2.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card3.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card4.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card5.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card6.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card7.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card8.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card9.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card10.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card11.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card12.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card13.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card14.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card15.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card16.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyImgEventHandler());
		card1.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card2.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card3.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card4.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card5.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card6.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card7.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card8.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card9.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card10.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card11.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card12.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card13.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card14.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card15.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
		card16.addEventHandler(MouseEvent.MOUSE_EXITED, new MyImgEventHandler02());
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
