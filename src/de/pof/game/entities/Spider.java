package de.pof.game.entities;

import de.pof.gamelib.hitboxes.Hitbox;
import de.pof.gamelib.math.Vec2d;
import de.pof.textures.TextureHandler;
import game.map.Map;

import java.awt.image.BufferedImage;

public class Spider implements Entity{


	//walking-left-rigth-AI

	private Vec2d pos;

	public Spider(int x, int y) {
		this.pos = new Vec2d(x, y);
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

	}

	@Override
	public BufferedImage getSprite() {
		return TextureHandler.getImagePng("spider");
	}

	@Override
	public Hitbox getHitBox() {
		return new Hitbox(Hitbox.HitboxType.RECTANGLE, 0, 0, 18, 9);
	}
}
