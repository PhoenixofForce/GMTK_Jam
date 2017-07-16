package de.pof.game.entities;

import de.pof.GUIConstants;
import de.pof.gamelib.hitboxes.Hitbox;
import de.pof.gamelib.math.Vec2d;
import de.pof.textures.TextureHandler;
import game.map.Map;

import java.awt.image.BufferedImage;

public class Spike implements Entity{


	//walking-left-rigth-AI

	private Vec2d pos;
	private Vec2d vel;

	private boolean walkRigth;

	public Spike(int x, int y) {
		this.pos = new Vec2d(x, y);
		vel = new Vec2d(0, 0);
		walkRigth = true;
	}

	@Override
	public Vec2d getPosition() {
		return pos;
	}

	@Override
	public void setVelocity(int xvel, int yvel) {
		this.vel.x = xvel;
		this.vel.y = yvel;
	}

	@Override
	public void update(Map m) {
		Vec2d tilePos_1 = pos.clone().divide(GUIConstants.TILE_SIZE);
		Vec2d tilePos_2 = pos.clone().add(0, 2).add(getHitBox().getWidth(), getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);

		if(!(m.isSolid((int) Math.floor(tilePos_2.x), (int) Math.floor(tilePos_2.y)) || m.isSolid((int) Math.floor(tilePos_1.x), (int) Math.floor(tilePos_2.y)))) {
			this.vel.y = 2;
		} else this.vel.y = 0;
		this.pos.add(vel);
	}

	@Override
	public BufferedImage getSprite() {
		return TextureHandler.getImagePng("spike");
	}

	@Override
	public Hitbox getHitBox() {
		return new Hitbox(Hitbox.HitboxType.RECTANGLE, 0, 0, 13, 8);
	}
}