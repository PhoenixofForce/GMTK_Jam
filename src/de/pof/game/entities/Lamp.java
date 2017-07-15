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
	private boolean shineBrigth;
	private long lastUpdate;
	private long lastShineUpdate;

	public Lamp(int x, int y) {
		fuel = 1000.0f;
		pos = new Vec2d(x, y);

		blink = false;
		shineBrigth = false;

		lastUpdate = System.currentTimeMillis();
		lastShineUpdate = System.currentTimeMillis();
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
		System.out.println(fuel);

		if(fuel <= 100.0f) {
			if(System.currentTimeMillis() - lastUpdate >= 500) {
				lastUpdate = System.currentTimeMillis();
				blink = !blink;
			}
		}

		if(shineBrigth && System.currentTimeMillis() - lastShineUpdate >= 500) {
			fuel -= 10.0f;
			lastShineUpdate = System.currentTimeMillis();
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

	public boolean isShiningBrigth() {
		return shineBrigth;
	}

	public void setBrigthShining(boolean bool) {
		this.shineBrigth = bool;
	}

	public boolean hasFuel() {
		return fuel > 0;
	}

	public void addFuel(float toadd) {
		fuel += toadd;
	}
}
