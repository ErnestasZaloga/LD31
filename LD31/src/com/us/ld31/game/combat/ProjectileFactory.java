package com.us.ld31.game.combat;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.us.ld31.LD31;

public class ProjectileFactory {
	
	private LD31 app;
	private Group projectileGroup;

	public ProjectileFactory(LD31 app, Group projectileGroup) {
		this.app = app;
		this.projectileGroup = projectileGroup;
	}
	
//	public Projectile createMeleeAttack(float startY, float startX, float dir) {
	public void createMeleeAttack(float startX, float startY, float dir) {
		Projectile p = new Projectile(startX, startY);
		p.setDir(dir);
		p.setRegion(app.assets.uiArrowDown);
		p.setDamage(-5);
		p.setSpeed(15);
		projectileGroup.addActor(p);
		System.out.println("added");
		//return p;
	}
	
	public void createPowerAttack(float startX, float startY, float dir) {
		Projectile p = new Projectile(startX, startY);
		p.setDir(dir);
		p.setRegion(app.assets.uiArrowDown);
		p.setDamage(-5);
		p.setSpeed(15);
		projectileGroup.addActor(p);
		System.out.println("added");
	}
}
