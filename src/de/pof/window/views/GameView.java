package de.pof.window.views;

import de.pof.GUIConstants;
import de.pof.Maths;
import de.pof.game.entities.Actions;
import de.pof.game.entities.Lamp;
import de.pof.game.entities.Player;
import de.pof.gamelib.math.Vec2d;
import de.pof.textures.TextureHandler;
import de.pof.window.View;
import de.pof.window.Window;
import de.pof.window.listener.Controller;
import de.pof.window.listener.KeyInputListener;
import game.map.Map;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameView extends View implements Controller{

	private Window w;
	private boolean running;

	private KeyInputListener key;

	private Map m;

	private Player player;
	private Lamp lamp;

	private boolean lampFollow;

	@Override
	public void init(Window window) {

		TextureHandler.loadImagePng("lamp", "lamp");
		TextureHandler.loadImagePngSpriteSheet("player", "player");

		this.w = window;
		key = new KeyInputListener(this);
		w.addKeyInputListener(key);
		running = true;

		lampFollow = true;

		m = new Map("TestMap_1");
		this.player = new Player(5, 50);
		this.lamp = new Lamp(0, 45);

		new Thread(() -> {
			while(true) {
				draw();


				Vec2d vel = new Vec2d(0, 0);
				if (key.isPressed(KeyEvent.VK_D)) vel.x = 6;
				if (key.isPressed(KeyEvent.VK_A)) vel.x = -6;
				if (key.isPressed(KeyEvent.VK_E) && lampFollow) lamp.setBrigthShining(true);
				else lamp.setBrigthShining(false);
				if(key.isPressed(KeyEvent.VK_W)) player.setAction(Actions.JUMPING);

				player.setVelocity((int) vel.x, (int) vel.y);
				player.update(m);

				if(lampFollow) lamp.setPosition(player.getPosition().x - 20, player.getPosition().y - 7);
				lamp.update(m);

				try {
					Thread.sleep(1000/30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void draw() {
		BufferedImage buffer = new BufferedImage(w.getPanel().getWidth(), w.getPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.setColor(GUIConstants.BACKGROUND_COLOR);
		g.fillRect(0, 0, w.getPanel().getWidth(), w.getPanel().getHeight());

		float lampreach = GUIConstants.LAMP_REACH * (lamp.isShiningBrigth()? 1.5f: 1.0f);

		for(int x = 0; x < m.getWidth(); x++) {
			for(int y = 0; y < m.getHeight(); y++) {
				BufferedImage sprite = m.getMap()[x][y] == 1? TextureHandler.getImagePng("grass"): m.getMap()[x][y] == 2? TextureHandler.getImagePng("dirt"): null;
				if(lamp.getPosition().clone().divide(GUIConstants.TILE_SIZE).distanceTo(new Vec2d(x, y)) <= lampreach)g.drawImage(sprite, x * GUIConstants.TILE_SIZE, y * GUIConstants.TILE_SIZE, GUIConstants.TILE_SIZE, GUIConstants.TILE_SIZE, null);
			}
		}

		if(lamp.getPosition().distanceTo(player.getPosition()) <= lampreach*GUIConstants.TILE_SIZE) g.drawImage(player.getSprite(), (int) player.getPosition().x, (int) player.getPosition().y, null);
		if(lamp.getSprite() != null) g.drawImage(lamp.getSprite(), (int) lamp.getPosition().x, (int) lamp.getPosition().y, null);

		w.getPanel().getGraphics().drawImage(buffer, 0, 0, null);
	}

	@Override
	public void onKeyType(int i) {
		if(i == KeyEvent.VK_S) {
			lampFollow = !lampFollow;
		}
	}

	@Override
	public void onMouseWheel(double v) {}

	@Override
	public void onMouseDrag(int i, int i1) {}

	@Override
	public void onMouseClick(int i, int i1) {}

	@Override
	public void stop() {
		running = false;
	}
}
