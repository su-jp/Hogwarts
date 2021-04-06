package hogwarts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hogwarts.common.MyLabelEventHandler;
import hogwarts.common.MyLabelEventHandler02;
import hogwarts.music.MusicMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainCon implements Initializable{
	@FXML AnchorPane mainPane;
	@FXML ImageView logo, imgPlay;
	@FXML Label lblStart, lblGame01, lblGame02, lblGame03, lblGame04;
	MusicMain mm;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mm = new MusicMain();
		lblScale();
	}
	
	public void musicStart() {
		mm.start();
		imgPlay.setVisible(false);
	}
	
	public void startGame01() {
		loadPage("game01/gameInfo01");
	}
	
	public void startGame02() {
		loadPage("game02/gameInfo02");
	}
	
	public void startGame03() {
		loadPage("game03/gameInfo03");
	}
	
	public void startGame04() {
		loadPage("game04/gameInfo04");
	}
	
	public void logoDisappear() {
		logo.setVisible(false);
		lblStart.setVisible(false);
		menuAppear();
	}
	
	private void lblScale() {
		lblStart.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyLabelEventHandler());
		lblGame01.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyLabelEventHandler());
		lblGame02.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyLabelEventHandler());
		lblGame03.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyLabelEventHandler());
		lblGame04.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyLabelEventHandler());
		lblStart.addEventHandler(MouseEvent.MOUSE_EXITED, new MyLabelEventHandler02());
		lblGame01.addEventHandler(MouseEvent.MOUSE_EXITED, new MyLabelEventHandler02());
		lblGame02.addEventHandler(MouseEvent.MOUSE_EXITED, new MyLabelEventHandler02());
		lblGame03.addEventHandler(MouseEvent.MOUSE_EXITED, new MyLabelEventHandler02());
		lblGame04.addEventHandler(MouseEvent.MOUSE_EXITED, new MyLabelEventHandler02());
	}
	
	private void menuAppear() {
		lblGame01.setVisible(true);
		lblGame02.setVisible(true);
		lblGame03.setVisible(true);
		lblGame04.setVisible(true);
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
