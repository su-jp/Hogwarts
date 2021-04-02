package hogwarts.common;

import java.util.Optional;

import javafx.scene.control.ButtonType;

public interface CommonService {
	public Optional<ButtonType> alertConfirm(String msg);
}
