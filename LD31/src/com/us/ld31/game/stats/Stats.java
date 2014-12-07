package com.us.ld31.game.stats;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class Stats {

	public static interface Upgrade {
		public void apply(final Stats stats);
		public void delete(final Stats stats);
		public void update(final Stats stats, 
						  final float delta);
	}
	
	public static class MixedUpgrade implements Upgrade {
		private int strength;
		private int dextirity;
		private int intelligence;
		private float damageBonus;
		private float magicResistanceBonus;
		private float criticalChanceBonus;
		private float criticalBonus;
		private float hitChanceBonus;
		private float dodgeChanceBonus;
		private float healBonus;
		
		public void setHealBonus(final float healBonus) {
			this.healBonus = healBonus;
		}
		
		public float getHealBonus() {
			return healBonus;
		}
		
		public void setDamageBonus(final float damageBonus) {
			this.damageBonus = damageBonus;
		}
		
		public float getDamageBonus() {
			return damageBonus;
		}
		
		public void setMagicResistanceBonus(final float magicResistanceBonus) {
			this.magicResistanceBonus = magicResistanceBonus;
		}
		
		public float getMagicResistanceBonus() {
			return magicResistanceBonus;
		}
		
		public float getCriticalChanceBonus() {
			return criticalChanceBonus;
		}

		public void setCriticalChanceBonus(final float criticalChanceBonus) {
			this.criticalChanceBonus = criticalChanceBonus;
		}

		public float getCriticalBonus() {
			return criticalBonus;
		}

		public void setCriticalBonus(final float criticalBonus) {
			this.criticalBonus = criticalBonus;
		}

		public float getHitChanceBonus() {
			return hitChanceBonus;
		}

		public void setHitChanceBonus(final float hitChanceBonus) {
			this.hitChanceBonus = hitChanceBonus;
		}

		public float getDodgeChanceBonus() {
			return dodgeChanceBonus;
		}

		public void setDodgeChanceBonus(final float dodgeChanceBonus) {
			this.dodgeChanceBonus = dodgeChanceBonus;
		}
		
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
		public void update(final Stats stats, 
						   final float delta) {
			
		}

		@Override
		public void apply(final Stats stats) {
			stats.setStrength(stats.getStrength() + strength);
			stats.setDextirity(stats.getDextirity() + dextirity);
			stats.setIntelligence(stats.getIntelligence() + intelligence);
			stats.setCriticalBonus(stats.getCriticalBonus() + criticalBonus);
			stats.setCriticalChanceBonus(stats.getCriticalChanceBonus() + criticalChanceBonus);
			stats.setDamageBonus(stats.getDamageBonus() + damageBonus);
			stats.setDodgeChanceBonus(stats.getDodgeChanceBonus() + dodgeChanceBonus);
			stats.setHitChanceBonus(stats.getHitChanceBonus() + hitChanceBonus);
			stats.setMagicResistanceBonus(stats.getMagicResistanceBonus() + magicResistanceBonus);
			stats.setHealBonus(stats.getHealBonus() + healBonus);
		}
		
		@Override
		public void delete(final Stats stats) {
			stats.setStrength(stats.getStrength() - strength);
			stats.setDextirity(stats.getDextirity() - dextirity);
			stats.setIntelligence(stats.getIntelligence() - intelligence);
			stats.setCriticalBonus(stats.getCriticalBonus() - criticalBonus);
			stats.setCriticalChanceBonus(stats.getCriticalChanceBonus() - criticalChanceBonus);
			stats.setDamageBonus(stats.getDamageBonus() - damageBonus);
			stats.setDodgeChanceBonus(stats.getDodgeChanceBonus() - dodgeChanceBonus);
			stats.setHitChanceBonus(stats.getHitChanceBonus() - hitChanceBonus);
			stats.setMagicResistanceBonus(stats.getMagicResistanceBonus() - magicResistanceBonus);
			stats.setHealBonus(stats.getHealBonus() - healBonus);
		}
	}
	
	public class Calculator {
		private final Stats source;
		
		private Calculator(final Stats source) {
			this.source = source;
		}
		
		public StatsResult rawDamageFormula(final int rawAndBonus, 
											 final float resistance,
											 final float targetDodgeChance) {
			
			final boolean dodged = MathUtils.randomBoolean(targetDodgeChance);
			
			float finalResistance = resistance;
			if(finalResistance > 1f) {
				finalResistance = 0f;
			}
			else if(finalResistance < 0f) {
				finalResistance = 0f;
			}
			
			float finalCriticalChance = criticalChanceBonus + source.getCriticalChance();
			
			if(finalCriticalChance > 1f) {
				finalCriticalChance = 1f;
			}
			else if(finalCriticalChance < 0f) {
				finalCriticalChance = 0f;
			}
			
			final boolean critical = MathUtils.randomBoolean(finalCriticalChance);
			final int damage = (int)(rawAndBonus * (1f - finalResistance) * (critical ? 2f + criticalBonus : 1f) * (dodged ? 0f : 1f));
			
			return new StatsResult(critical, dodged, damage);
		}

		public StatsResult calcMagicHealAmount(final int skillBonus) {
			return rawDamageFormula(skillBonus + (int)(source.getRawMagicDamage() * (1f + healBonus)), 0f, 0f);
		}
		
		public StatsResult calcMagicDamageAmount(final Stats target, 
									 	 		 final int skillBonus) {

			final int damage = skillBonus + (int)(source.getRawMagicDamage() * (1f + damageBonus));
			final float magicResistance = target.getMagicResistance() * (1f + target.getMagicResistanceBonus());
			float dodgeChance = target.getDefensiveDodgeChance() * (1f + target.getDodgeChanceBonus()) - hitChanceBonus;
			if(dodgeChance < 0f) {
				dodgeChance = 0f;
			}
			
			return rawDamageFormula(damage, magicResistance, dodgeChance);
		}
		
		public StatsResult calcRangedDamageAmount(final Stats target, 
												  final int skillBonus) {
			
			final int damage = skillBonus + (int)(source.getRawRangedDamage() * (1f + damageBonus));
			final float resistance = (getAttackRating() / target.getDefenseRating()) - 1f;
			final float dodgeChance = target.getDefensiveDodgeChance() * (1f + target.getDodgeChanceBonus()) - hitChanceBonus;
			
			return rawDamageFormula(damage, resistance, dodgeChance);
		}
		
		public StatsResult caclMeleeDamageAmount(final Stats target, 
												 final int skillBonus) {
			
			final int damage = skillBonus + (int)(source.getRawMeleeDamage() + (1f + damageBonus));
			final float resistance = (getAttackRating() / target.getDefenseRating()) - 1f;
			final float dodgeChance = target.getDefensiveDodgeChance() * (1f + target.getDodgeChanceBonus()) - hitChanceBonus;
			
			return rawDamageFormula(damage,resistance, dodgeChance);
		}
	}
	
	private final Calculator calculator = new Calculator(this);
	private final DelayedRemovalArray<Upgrade> upgrades = new DelayedRemovalArray<Upgrade>();

	// Strength: melee damage, hp
	// Dextirity: attack rating, defense rating, critical chance, ranged damage
	// Intelligence: magic damage bonus, magic resistance, cooldown reduction

	private float damageBonus;
	private float magicResistanceBonus;
	private float criticalChanceBonus;
	private float criticalBonus;
	private float hitChanceBonus;
	private float dodgeChanceBonus;
	private float healBonus;
	
	private int hitPoints;
	private int strength = 10;
	private int dextirity = 10;
	private int intelligence = 10;

	public Stats() {
		restoreToFullHealth();
	}
	
	public void update(final float delta) {
		upgrades.begin();
		for(int i = 0, n = upgrades.size; i < n; i += 1) {
			upgrades.get(i).update(this, delta);
		}
		upgrades.end();
	}
	
	public Calculator calculator() {
		return calculator;
	}
	
	public void setDamageBonus(final float damageBonus) {
		this.damageBonus = damageBonus;
	}
	
	public float getDamageBonus() {
		return damageBonus;
	}
	
	public void setMagicResistanceBonus(final float magicResistanceBonus) {
		this.magicResistanceBonus = magicResistanceBonus;
	}
	
	public float getMagicResistanceBonus() {
		return magicResistanceBonus;
	}
	
	public float getCriticalChanceBonus() {
		return criticalChanceBonus;
	}

	public void setCriticalChanceBonus(final float criticalChanceBonus) {
		this.criticalChanceBonus = criticalChanceBonus;
	}

	public float getCriticalBonus() {
		return criticalBonus;
	}

	public void setCriticalBonus(final float criticalBonus) {
		this.criticalBonus = criticalBonus;
	}

	public float getHitChanceBonus() {
		return hitChanceBonus;
	}

	public void setHitChanceBonus(final float hitChanceBonus) {
		this.hitChanceBonus = hitChanceBonus;
	}

	public float getDodgeChanceBonus() {
		return dodgeChanceBonus;
	}

	public void setDodgeChanceBonus(final float dodgeChanceBonus) {
		this.dodgeChanceBonus = dodgeChanceBonus;
	}
	
	public float getHealBonus() {
		return healBonus;
	}
	
	public void setHealBonus(final float healBonus) {
		this.healBonus = healBonus;
	}
	
	public boolean isDead() {
		return hitPoints == 0;
	}

	public void setHitPoints(final int hitPoints) {
		final int max = getMaxHitPoints();
		
		if(hitPoints < 0) {
			this.hitPoints = 0;
			return;
		}
		else if(hitPoints > max) {
			this.hitPoints = max;
			return;
		}
		
		this.hitPoints = hitPoints;
	}
	
	public int getMaxHitPoints() {
		return 100 + strength * 10;
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
	
	public float getMagicResistance() {
		return intelligence * 0.015f;
	}
	
	public float getAttackRating() {
		return dextirity * 0.1f;
	}
	
	public float getDefenseRating() {
		return dextirity * 0.1f;
	}
	
	public float getCriticalChance() {
		return dextirity * 0.015f;
	}
	
	public float getDefensiveDodgeChance() {
		return dextirity * 0.015f;
	}
	
	public float getCooldownReductionPercent() {
		return intelligence * 0.1f;
	}

	public int getRawMeleeDamage() {
		return 5 + strength * 2;
	}
	
	public int getRawRangedDamage() {
		return (int)(dextirity * 1.5f);
	}
	
	public int getRawMagicDamage() {
		return (int)(intelligence * 1.5f);
	}
	
	/**
	 * Helper methods
	 * */
	public void heal(final int baseAmount, 
					 final boolean usesMagic) {
		
		if(hitPoints == 0) {
			return;
		}
		
		final int finalAmount = baseAmount;
		setHitPoints(hitPoints + finalAmount);
	}
	
	public void restoreToFullHealth() {
		if(hitPoints > 0) {
			hitPoints = getMaxHitPoints();
		}
	}
	
	
}
