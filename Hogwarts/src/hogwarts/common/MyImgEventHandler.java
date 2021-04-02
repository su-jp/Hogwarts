package hogwarts.common;

import javafx.event.EventHandler;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MyImgEventHandler implements EventHandler<MouseEvent> {
	@Override
	public void handle(MouseEvent arg0) {
		Object obj = arg0.getSource();
		ImageView img = (ImageView) obj;
		Effect bloom = new Bloom();
		img.setEffect(bloom);
	}
}
