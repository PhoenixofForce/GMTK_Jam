package de.pof;

import de.pof.window.Window;
import de.pof.window.views.GameView;

public class Main {

	public static void main(String[] args) {
		Window w = new Window();
		w.setResizable(false);
		w.updateView(new GameView());
	}

}
