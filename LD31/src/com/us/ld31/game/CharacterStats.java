package com.us.ld31.game;

import com.us.ld31.game.skills.SkillTree;

public class CharacterStats extends Stats {

	private int hitPoints = 100;
	private int level = 1;
	private int freePoints;
	private int skillPoints = 1;
	private int experience;
	private int gold;
	private final SkillTree[] skills = new SkillTree[3];
	
	public int getHitPoints() {
		return hitPoints;
	}
	
	public SkillTree[] getSkills() {
		return skills;
	}
	
	public int getSkillPoints() {
		return skillPoints;
	}
	
	public void addGold(final int amount) {
		this.gold += amount;
	}
	
	public void spendGold(final int amount) {
		this.gold -= amount;
	}
	
	public int getGold() {
		return gold;
	}
	
	public void spendFreePoints() {
		freePoints = 0;
	}
	
	public boolean addExperience(final int exp) {
		experience += exp;
		
		boolean levelUp = false;
		while(experience > level * 100) {
			levelUp = true;
			experience -= level * 100;
			level += 1;
			freePoints += 3;
			skillPoints += 1;
		}
		
		return levelUp;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getFreePoints() {
		return freePoints;
	}
	
	public int getExperience() {
		return experience;
	}
	
	public int getLevelUpRequirement() {
		return level * 100;
	}
	
	public int getExperienceLeft() {
		return level * 100 - experience;
	}
}
