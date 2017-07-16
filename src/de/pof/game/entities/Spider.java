package de.pof.game.entities;

import de.pof.GUIConstants;
import de.pof.gamelib.hitboxes.Hitbox;
import de.pof.gamelib.math.Vec2d;
import de.pof.textures.TextureHandler;
import game.map.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spider implements Entity{


	//walking-left-rigth-AI

	private Vec2d pos;
	private Vec2d vel;

	private boolean walkRigth;

	public Spider(int x, int y) {
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

		if(walkRigth) {
			Vec2d test = pos.clone().add(5, 2).add(getHitBox().getWidth(), getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);
			Vec2d test2 = pos.clone().add(5, 0).add(getHitBox().getWidth(), 0).divide(GUIConstants.TILE_SIZE);
			try {
				if(!m.isSolid((int)Math.floor(test.x), (int)Math.floor(test.y)) || m.isSolid((int)Math.floor(test2.x), (int)Math.floor(test2.y))) walkRigth = false;
			}catch (Exception e) {walkRigth = false;}
		}else {
			Vec2d test = pos.clone().add(-5, 2 + getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);
			Vec2d test2 = pos.clone().add(-5, 0).divide(GUIConstants.TILE_SIZE);
			try{
				if(!m.isSolid((int)Math.floor(test.x), (int)Math.floor(test.y)) || m.isSolid((int)Math.floor(test2.x), (int)Math.floor(test2.y))) walkRigth = true;
			}catch (Exception e) {walkRigth = true;}
		}

		vel.x = walkRigth? 5: -5;
		this.pos.add(vel);
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
