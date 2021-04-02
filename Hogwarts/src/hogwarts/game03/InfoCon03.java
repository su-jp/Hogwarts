package hogwarts.game03;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import hogwarts.common.CommonService;
import hogwarts.common.CommonServiceImpl;
import hogwarts.common.MyLabelEventHandler;
import hogwarts.common.MyLabelEventHandler02;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class InfoCon03 implements Initializable {
	@FXML AnchorPane mainPane;
	@FXML Label lblHome, lblTitle, lblInfo, lblStart;
	CommonService cs;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cs = new CommonServiceImpl();
		lblScale();
		setGameTitle();
		setGameInfo();
	}
	
	public void startGame() {
		loadPage("gamePage03");
	}
	
	public void goHome() {
		Optional<ButtonType> result = cs.alertConfirm("Return to the main page.");
		if(result.get() == ButtonType.OK) {
			loadPage("../mainPage");
		} else { return; }
	}
	
	private void setGameTitle() {
		lblTitle.setText("I MUST NOT TELL LIES");
	}
	
	private void setGameInfo() {
		lblInfo.setText("Will be added soon.");
	}
	
	private void lblScale() {
		lblHome.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyLabelEventHandler());
		lblHome.addEventHandler(MouseEvent.MOUSE_EXITED, new MyLabelEventHandler02());
		lblStart.addEventHandler(MouseEvent.MOUSE_ENTERED, new MyLabelEventHandler());
		lblStart.addEventHandler(MouseEvent.MOUSE_EXITED, new MyLabelEventHandler02());
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
