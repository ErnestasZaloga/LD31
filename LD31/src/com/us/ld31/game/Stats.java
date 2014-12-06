package com.us.ld31.game;

import com.badlogic.gdx.utils.Array;

public class Stats {

	public static interface Upgrade {
		public void apply(final Stats stats);
		public void delete(final Stats stats);
	}
	
	public static class MixedUpgrade implements Upgrade {
		private int strength;
		private int dextirity;
		private int intelligence;

		public int getTotalPoints() {
			return strength + dextirity + intelligence;
		}
		
		public int getStrength() {
			return strength;
		}

		public void setStrength(final int strength) {
			this.strength = strength;
		}

		public int getDextirity() {
			return dextirity;
		}

		public void setDextirity(final int dextirity) {
			this.dextirity = dextirity;
		}

		public int getIntelligence() {
			return intelligence;
		}

		public void setIntelligence(final int intelligence) {
			this.intelligence = intelligence;
		}

		@Override
		public void apply(final Stats stats) {
			stats.setStrength(stats.getStrength() + strength);
			stats.setDextirity(stats.getDextirity() + dextirity);
			stats.setIntelligence(stats.getIntelligence() + intelligence);
		}
		
		@Override
		public void delete(final Stats stats) {
			stats.setStrength(stats.getStrength() - strength);
			stats.setDextirity(stats.getDextirity() - dextirity);
			stats.setIntelligence(stats.getIntelligence() - intelligence);
		}
	}

	private int level;
	private int freePoints;
	private int experience;
	
	private final Array<Upgrade> upgrades = new Array<Upgrade>();
	
	private int strength;
	private int dextirity;
	private int intelligence;
	
	public void spendFreePoints() {
		freePoints = 0;
	}
	
	public void setStrength(final int strength) {
		this.strength = strength;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public void setDextirity(final int dextirity) {
		this.dextirity = dextirity;
	}
	
	public int getDextirity() {
		return dextirity;
	}
	
	public void setIntelligence(final int intelligence) {
		this.intelligence = intelligence;
	}
	
	public int getIntelligence() {
		return intelligence;
	}
	
	public boolean addExperience(final int exp) {
		experience += exp;
		
		boolean levelUp = false;
		while(experience > level * 100) {
			levelUp = true;
			experience -= level * 100;
			level += 1;
			freePoints += 3;
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
	
	public void addUpgrade(final Upgrade upgrade) {
		upgrade.apply(this);
		upgrades.add(upgrade);
	}
	
	public void removeUpgrade(final Upgrade upgrade) {
		if(upgrades.contains(upgrade, true)) {
			upgrade.delete(this);
			upgrades.removeValue(upgrade, true);
		}
	}
	
	public Array<Upgrade> getUpgrades() {
		return upgrades;
	}
	
}
