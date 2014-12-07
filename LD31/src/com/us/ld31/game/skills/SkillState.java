package com.us.ld31.game.skills;

import com.badlogic.gdx.utils.Array;

public class SkillState {

	private final SkillInfo skillInfo;
	private int level;
	private float cooldown;
	private float cooldownLeft;
	private final Array<SkillState> parents = new Array<SkillState>();
	private final Array<SkillState> children = new Array<SkillState>();
	
	public SkillState(final SkillInfo skillInfo) {
		this.skillInfo = skillInfo;
	}
	
	public boolean updateCooldown(final float delta) {
		cooldownLeft -= delta;
		if(cooldownLeft < 0f) {
			cooldownLeft = 0f;
			return true;
		}
		
		return false;
	}
	
	public SkillInfo getSkillInfo() {
		return skillInfo;
	}

	public void setCooldown(final float cooldown) {
		this.cooldown = cooldown;
	}
	
	public float getCooldown() {
		return cooldown;
	}
	
	public void setCooldownLeft(final float cooldownLeft) {
		this.cooldownLeft = cooldownLeft;
	}
	
	public float getCooldownLeft() {
		return cooldownLeft;
	}
	
	public void setLevel(final int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void addChild(final SkillState child) {
		children.add(child);
		child.parents.add(this);
	}
	
	public Array<SkillState> getParents() {
		return parents;
	}
	
	public Array<SkillState> getChildren() {
		return children;
	}
	
}
