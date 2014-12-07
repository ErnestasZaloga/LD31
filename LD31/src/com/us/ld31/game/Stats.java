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
	
	private final Array<Upgrade> upgrades = new Array<Upgrade>();
	
	private int hitPoints;
	private int strength;
	private int dextirity;
	private int intelligence;

	// Strength: melee damage, hp
	// Dextirity: attack rating, defense rating, critical chance, ranged damage
	// Intelligence: magic damage bonus, magic resistance, cooldown reduction

	public boolean isDead() {
		return hitPoints == 0;
	}
	
	public void setHitPoints(final int hitPoints) {
		if(hitPoints < 0) {
			this.hitPoints = 0;
			return;
		}
		this.hitPoints = hitPoints;
	}
	
	public int getHitPoints() {
		return hitPoints;
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
