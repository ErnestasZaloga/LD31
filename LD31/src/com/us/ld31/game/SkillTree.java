package com.us.ld31.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.game.skills.Skill;

public class SkillTree {

	public static class SkillNode {
		private SkillTree skillTree;
		private SkillNode parent;
		private final Array<SkillNode> children = new Array<SkillNode>();
		private boolean enabled;
		
		private Skill skill;
		private float cooldown;
		
		private SkillNode() {}
		
		private SkillNode getInstance(final SkillTree skillTree, 
									  final SkillNode parent) {
			
			final SkillNode skillNode = new SkillNode();
			skillNode.skillTree = skillTree;
			skillNode.skill = skill;
			skillNode.parent = parent;
			
			for(int i = 0; i < children.size; i += 1) {
				skillNode.children.add(children.get(i).getInstance(skillTree, skillNode));
			}
			
			return skillNode;
		}
		
		public void activate(final GameWorld gameWorld) {
			cooldown = skill.activate(skillTree.owner, gameWorld, 1);  //XXX: THIS IS HARDCODED!!!!!!!!!!!!!!!!!!
		}
		
		public void enable() {
			enabled = true;
		}
		
		public boolean isEnabled() {
			return enabled;
		}
		
		public void setSkill(final Skill skill) {
			this.skill = skill;
		}
		
		public Skill getSkill() {
			return skill;
		}
		
		public void createSkillNode(final Skill skill) {
			final SkillNode child = new SkillNode();
			child.parent = this;
			child.skill = skill;
			child.skillTree = skillTree;
			
			children.add(child);
		}
		
		public SkillNode getParent() {
			return parent;
		}
		
		public Array<SkillNode> getNodes() {
			return children;
		}
		
		public void update(final float delta) {
			cooldown -= delta;
			if(cooldown < 0) {
				cooldown = 0;
			}
			
			for(int i = 0; i < children.size; i += 1) {
				children.get(i).update(delta);
			}
		}
	}
	
	private Actor owner;
	private SkillNode rootNode = new SkillNode();
	
	public SkillTree() {
		rootNode.skillTree = this;
	}
	
	public SkillTree getInstance(final Actor owner) {
		final SkillTree skillTree = new SkillTree();
		
		skillTree.owner = owner;
		skillTree.rootNode = rootNode.getInstance(skillTree, null);
		
		return skillTree;
	}
	
	public SkillNode getRoot() {
		return rootNode;
	}

	public void update(final float delta) {
		rootNode.update(delta);
	}
}
