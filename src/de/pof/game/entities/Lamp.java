package de.pof.game.entities;

import de.pof.GUIConstants;
import de.pof.gamelib.hitboxes.Hitbox;
import de.pof.gamelib.math.Vec2d;
import de.pof.textures.TextureHandler;
import game.map.Map;

import java.awt.image.BufferedImage;

public class Lamp implements Entity{

	public static final float MAX_FUEL = 375.0f;

	private Vec2d pos;
	private float fuel;

	private boolean blink;
	private boolean following;
	private boolean shineBrigth;
	private long lastUpdate;
	private long lastShineUpdate;

	public Lamp(int x, int y) {
		fuel = MAX_FUEL;
		pos = new Vec2d(x, y);

		blink = false;
		shineBrigth = false;
		following = true;

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

		Vec2d tilePos_1 = pos.clone().divide(GUIConstants.TILE_SIZE);
		Vec2d tilePos_2 = pos.clone().add(0, 2).add(getHitBox().getWidth(), getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);

		if(!following) {
			Vec2d vel = new Vec2d(0, 0);
			if(!(m.isSolid((int) Math.floor(tilePos_2.x), (int) Math.floor(tilePos_2.y)) || m.isSolid((int) Math.floor(tilePos_1.x), (int) Math.floor(tilePos_2.y)))) {
				vel.y = 2;
			} else vel.y = 0;
			this.pos.add(vel);
		}
	}

	public float getFuel() {
		return fuel;
	}

	@Override
	public BufferedImage getSprite() {
		return blink? null: TextureHandler.getImagePng("lamp");
	}

	@Override
	public Hitbox getHitBox() {
		return new Hitbox(Hitbox.HitboxType.RECTANGLE, 0, 0, 17, 20);
	}

	public void setPosition(double x, double y) {
		fuel -= pos.distanceTo(new Vec2d(x, y))/30.0f;
		this.pos.x = x;
		this.pos.y = y;
	}

	public void setFollowing(boolean a) {
		this.following = a;
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
