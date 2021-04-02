package hogwarts.common;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MyLabelEventHandler02 implements EventHandler<MouseEvent> {
	@Override
	public void handle(MouseEvent arg0) {
		Object obj = arg0.getSource();
		Label lbl = (Label) obj;
		lbl.setScaleX(1);
		lbl.setScaleY(1);
	}
}
