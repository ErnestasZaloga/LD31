package com.us.ld31.game.skills;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class SkillInfo {

	public static class Builder {
		private String name;
		private String description;
		private Skill skill;
		private TextureRegion icon;
		
		public Builder icon(final TextureRegion icon) {
			this.icon = icon;
			return this;
		}
		
		public Builder skill(final Skill skill) {
			this.skill = skill;
			return this;
		}
		
		public Builder name(final String name) {
			this.name = name;
			return this;
		}
		
		public Builder descrption(final String description) {
			this.description = description;
			return this;
		}
		
		public SkillInfo build() {
			if(name == null) {
				throw new RuntimeException("name cannot be null");
			}
			else if(description == null) {
				throw new RuntimeException("description cannot be null");
			}
			else if(skill == null) {
				throw new RuntimeException("skill cannot be null");
			}
			else if(icon == null) {
				throw new RuntimeException("icon cannot be null");
			}
			
			final SkillInfo skillInfo = new SkillInfo(name, description, skill, icon);
			
			name = null;
			description = null;
			skill = null;
			icon = null;
			
			return skillInfo;
		}
	}
	
	public final String name;
	public final String description;
	public final Skill skill;
	public final TextureRegion icon;
	
	private SkillInfo(final String name, 
					  final String description,
					  final Skill skill,
					  final TextureRegion icon) {
		
		this.name = name;
		this.description = description;
		this.skill = skill;
		this.icon = icon;
	}
	
}
