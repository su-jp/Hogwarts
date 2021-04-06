package hogwarts.music;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MusicMain {
	public void start() {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("music.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("BGM");
		stage.show();
	}
}
