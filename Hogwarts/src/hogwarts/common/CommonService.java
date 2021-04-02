package hogwarts.common;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public interface CommonService {
	public Optional<ButtonType> alertConfirm(String msg);
	public void alert(String msg);
}
