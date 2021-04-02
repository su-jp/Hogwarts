package hogwarts.common;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class CommonServiceImpl implements CommonService {

	@Override
	public Optional<ButtonType> alertConfirm(String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(msg);
		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}

	@Override
	public void alert(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
