package de.pof.window.views;

import de.pof.GUIConstants;
import de.pof.window.View;
import de.pof.window.Window;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VictoryView extends View {
	Window w;
	@Override
	public void init(Window window) {
		this.w = window;
	}

	@Override
	public void draw() {
		BufferedImage buffer = new BufferedImage(w.getPanel().getWidth(), w.getPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) buffer.getGraphics();
		g.setColor(GUIConstants.BACKGROUND_COLOR);
		g.fillRect(0, 0, w.getPanel().getWidth(), w.getPanel().getHeight());


		g.setColor(Color.YELLOW);
		g.setFont(new Font("Arial", Font.PLAIN, 50));

		g.drawString("VICTORY!", 50, 50);

		w.getPanel().getGraphics().drawImage(buffer, 0, 0, null);
	}

}
