package hogwarts.game02;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import hogwarts.common.CommonService;
import hogwarts.common.CommonServiceImpl;
import hogwarts.common.MyLabelEventHandler;
import hogwarts.common.MyLabelEventHandler02;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameCon02 implements Initializable {
	@FXML AnchorPane mainPane;
	@FXML Label lblHome, lblTitle;
	@FXML ImageView testOrb;
	CommonService cs;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cs = new CommonServiceImpl();
		lblScale();
		setGameTitle();
		fallDown(testOrb, 150);
	}
	
	private void fallDown(ImageView orb, int axis) {
		Path path = new Path();
		path.getElements().add(new MoveTo(axis, -30));
		path.getElements().add(new CubicCurveTo(axis + 100, 300, axis - 100, 600, axis, 1200));
		PathTransition pathTransition = new PathTransition(); pathTransition.setDuration(Duration.millis(6000));
		pathTransition.setPath(path);
		pathTransition.setNode(orb);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(Timeline.INDEFINITE);
		pathTransition.setAutoReverse(false);
		pathTransition.play();
	}
	
	public void goHome() {
		Optional<ButtonType> result = cs.alertConfirm("Go back to the main page.");
		if(result.get() == ButtonType.OK) {
			loadPage("../mainPage");
		} else { return; }
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
