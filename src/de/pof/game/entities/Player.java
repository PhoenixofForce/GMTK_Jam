package de.pof.game.entities;

import de.pof.GUIConstants;
import de.pof.gamelib.hitboxes.Hitbox;
import de.pof.gamelib.math.Vec2d;
import de.pof.textures.Animation;
import game.map.Map;

import java.awt.image.BufferedImage;

public class Player implements Entity{

	private Vec2d pos;
	private Vec2d vel;
	private Animation anim;

	private BufferedImage sprite;
	private Actions action;

	private long jumpStart;
	private long lastUpdate;
	private long lastHit;
	private long attackStart;

	public Player(int x, int y) {
		this.pos = new Vec2d(x, y);
		this.vel = new Vec2d(0, 0);

		jumpStart = -1;
		lastHit = System.currentTimeMillis();
		attackStart = System.currentTimeMillis();

		action = Actions.STANDING;
		anim = new Animation("player_moving") {
			@Override
			public void onSpriteChange() {
				sprite = getCurrentSprite();
			}
		};
	}

	@Override
	public Vec2d getPosition() {
		return pos;
	}

	@Override
	public void setVelocity(int xvel, int yvel) {
		if(xvel == 0) {
			if(this.vel.x > 0) this.vel.x -= 1;
			else if(this.vel.x < 0) this.vel.x += 1;
		}
		else this.vel.x = xvel;
	}

	@Override
	public void update(Map m) {
		long curTime = System.currentTimeMillis();
		if(curTime - lastUpdate >= 75) {
			lastUpdate = curTime;
			anim.next();
		}

		if((action == Actions.JUMPING && curTime - jumpStart < GUIConstants.JUMP_TIME) || (action == Actions.ATTACKING && curTime - attackStart <= 300));
		else {
			jumpStart = -1;

			Vec2d tilePos_1 = pos.clone().divide(GUIConstants.TILE_SIZE);
			Vec2d tilePos_2 = pos.clone().add(0, 2).add(getHitBox().getWidth(), getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);

			if(!(m.isSolid((int) Math.floor(tilePos_2.x), (int) Math.floor(tilePos_2.y)) || m.isSolid((int) Math.floor(tilePos_1.x), (int) Math.floor(tilePos_2.y)))) {
				this.action = Actions.FALLING;
			}else {
				if(vel.x == 0.0) this.action = Actions.STANDING;
				else this.action = Actions.MOVING;
			}
		}

		System.out.println(action);

		if(this.action == Actions.JUMPING) vel.y -= 2;
		if(this.action == Actions.FALLING) vel.y += 2;

		Vec2d tilePos_1 = pos.clone().add(vel).divide(GUIConstants.TILE_SIZE);
		Vec2d tilePos_2 = pos.clone().add(vel).add(getHitBox().getWidth(), getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);

		try {
			if(vel.y > 0) {
				while(m.isSolid((int) Math.floor(tilePos_2.x), (int) Math.floor(tilePos_2.y))  || m.isSolid((int) Math.floor(tilePos_1.x), (int) Math.floor(tilePos_2.y))  && vel.y > 0) {
					vel.y-= 1;
					tilePos_1 = pos.clone().add(vel).divide(GUIConstants.TILE_SIZE);
					tilePos_2 = pos.clone().add(vel).add(getHitBox().getWidth(), getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);
				}
			}else if(vel.y < 0)
				while(m.isSolid((int) Math.floor(tilePos_1.x), (int) Math.floor(tilePos_1.y))  || m.isSolid((int) Math.floor(tilePos_2.x), (int) Math.floor(tilePos_1.y))  && vel.y < 0) {
					vel.y += 1;
					tilePos_1 = pos.clone().add(vel).divide(GUIConstants.TILE_SIZE);
					tilePos_2 = pos.clone().add(vel).add(getHitBox().getWidth(), getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);
				}
		}catch(Exception e) {
			vel.y = 0;
			System.err.println("Error Y");
		}

		try {
			if (vel.x > 0) {
				while (m.isSolid((int) Math.floor(tilePos_2.x), (int) Math.floor(tilePos_1.y)) || m.isSolid((int) Math.floor(tilePos_2.x), (int) Math.floor(tilePos_2.y)) && vel.x > 0) {
					vel.x -= 1;
					tilePos_1 = pos.clone().add(vel).divide(GUIConstants.TILE_SIZE);
					tilePos_2 = pos.clone().add(vel).add(getHitBox().getWidth(), getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);
				}
			} else if (vel.x < 0)
				while (m.isSolid((int) Math.floor(tilePos_1.x), (int) Math.floor(tilePos_1.y)) || m.isSolid((int) Math.floor(tilePos_1.x), (int) Math.floor(tilePos_2.y)) && vel.x < 0) {
					vel.x += 1;
					tilePos_1 = pos.clone().add(vel).divide(GUIConstants.TILE_SIZE);
					tilePos_2 = pos.clone().add(vel).add(getHitBox().getWidth(), getHitBox().getHeight()).divide(GUIConstants.TILE_SIZE);
				}
		}catch(Exception e) {
			vel.x = 0;
			System.err.println("Error X");
		}

		if (vel.length() > 0) {
			this.pos.add(vel);
			m.onPlayerMove(this);
		}
	}

	public Vec2d getVel() {
		return vel;
	}

	@Override
	public BufferedImage getSprite() {
		return sprite;
	}

	@Override
	public Hitbox getHitBox() {
		return new Hitbox(Hitbox.HitboxType.RECTANGLE, 0, 0, 18, 26);
	}

	public Actions getAction() {
		return action;
	}

	public void setAction(Actions newAc) {
		if(action != Actions.FALLING && action != Actions.JUMPING) {
			if(newAc == Actions.JUMPING) {
				jumpStart = System.currentTimeMillis();
			}
			this.action = newAc;
		}

		if(newAc == Actions.ATTACKING && System.currentTimeMillis() - attackStart >= 750) {
			attackStart = System.currentTimeMillis();
			this.action = newAc;
		}
	}

	public void hit() {
		lastHit = System.currentTimeMillis();
	}

	public boolean hitable() {
		return System.currentTimeMillis() - lastHit >= GUIConstants.INVULNERABLE_AFTER_HIT;
	}
}
