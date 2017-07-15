package de.pof.game.entities;

import de.pof.gamelib.hitboxes.Hitbox;
import de.pof.gamelib.math.Vec2d;
import game.map.Map;

import java.awt.image.BufferedImage;

public interface Entity {

	Vec2d getPosition();
	void setVelocity(int xvel, int yvel);
	void update(Map m);

	BufferedImage getSprite();
	Hitbox getHitBox();
}