package com.us.ld31.game.stats;

import com.us.ld31.game.skills.SkillTree;

public class CharacterStats extends Stats {

	private int level = 1;
	private int statPoints;
	private int skillPoints = 5;
	private int experience;
	private final SkillTree[] skills = new SkillTree[3];
	
	public SkillTree[] getSkills() {
		return skills;
	}
	
	public void setSkillPoints(final int skillPoints) {
		this.skillPoints = skillPoints;
	}
	
	public int getSkillPoints() {
		return skillPoints;
	}

	public void setStatPoints(final int statPoints) {
		this.statPoints = statPoints;
	}
	
	public int getStatPoints() {
		return statPoints;
	}
	
	public boolean addExperience(final int exp) {
		experience += exp;
		
		boolean levelUp = false;
		while(experience > level * 100) {
			levelUp = true;
			experience -= level * 100;
			level += 1;
			statPoints += 3;
			skillPoints += 1;
		}
		
		return levelUp;
	}
	
	public int getLevel() {
		return level;
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
