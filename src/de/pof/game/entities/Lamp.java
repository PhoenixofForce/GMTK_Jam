package de.pof.game.entities;

import de.pof.gamelib.hitboxes.Hitbox;
import de.pof.gamelib.math.Vec2d;
import de.pof.textures.TextureHandler;
import game.map.Map;

import java.awt.image.BufferedImage;

public class Lamp implements Entity{

	private Vec2d pos;
	private float fuel;

	private boolean blink;
	private long lastUpdate;

	public Lamp(int x, int y) {
		fuel = 1000.0f;
		pos = new Vec2d(x, y);
		blink = false;

		lastUpdate = System.currentTimeMillis();
	}

	@Override
	public Vec2d getPosition() {
		return pos;
	}

	@Override
	public void setVelocity(int xvel, int yvel) {

	}

	@Override
	public void update(Map m) {
		if(fuel <= 100.0f) {
			if(System.currentTimeMillis() - lastUpdate >= 500) {
				lastUpdate = System.currentTimeMillis();
				blink = !blink;
			}
		}
	}

	@Override
	public BufferedImage getSprite() {
		return blink? null: TextureHandler.getImagePng("lamp");
	}

	@Override
	public Hitbox getHitBox() {
		return null;
	}

	public void setPosition(double x, double y) {
		fuel -= pos.distanceTo(new Vec2d(x, y))/30.0f;
		this.pos.x = x;
		this.pos.y = y;
	}
}
