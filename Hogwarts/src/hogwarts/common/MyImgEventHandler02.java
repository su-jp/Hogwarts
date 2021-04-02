package hogwarts.common;

import javafx.event.EventHandler;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MyImgEventHandler02 implements EventHandler<MouseEvent> {
	@Override
	public void handle(MouseEvent arg0) {
		Object obj = arg0.getSource();
		ImageView img = (ImageView) obj;
		Effect none = new Blend();
		img.setEffect(none);
	}
}
