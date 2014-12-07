package com.us.ld31.game.combat;

import com.badlogic.gdx.math.MathUtils;
import com.us.ld31.utils.SpriteActor;

/**
 * Represents any kind of damaging attack, e.g. fireball, melee/ranged attack, stone being translocated and etc.
 * 
 * @author Ernyz
 */
public class Projectile extends SpriteActor {
	
	private float x;
	private float y;
	private float dir;
	private float speed;
	private float damage;
	private float range;

	public Projectile(float startX, float startY) {
		this.x = startX;
		this.y = startY;
	}
	
	public void update(float delta) {
		x += speed * MathUtils.cosDeg(dir);
		y += speed * MathUtils.sinDeg(dir);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getDir() {
		return dir;
	}

	public void setDir(float dir) {
		this.dir = dir;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}
	
}
