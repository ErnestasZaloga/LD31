package com.us.ld31.game.skills.warrior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.PlayerCharacter;
import com.us.ld31.game.foestuff.Foe;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.utils.steps.scene.ActorSteps;
import com.us.ld31.utils.steps.scene.StepAction;

public class Charge implements Skill {
	
	private float cooldown = 15f;
	private float rangeInTiles = 10f;
	private float range;

	@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		float tileSize = gameWorld.getWorldMap().getTileSize();
		range = tileSize * rangeInTiles;
		
		PlayerCharacter player = gameWorld.getCharacter();
		float playerX = player.getX();
		float playerY = player.getY();
		//
		// If user is Player character
		//
		if(user instanceof PlayerCharacter) {
			Foe target = gameWorld.getFoeManager().getFoeByCoords(mouseX, mouseY);
			
			if(target != null) {
				float trueRange = range + (target.getWidth() + target.getHeight()) / 4 + 
						   					(player.getWidth() + player.getHeight()) / 4;
				
				if(Vector2.dst(target.getX(), target.getY(), playerX, playerY) <= trueRange) {
					//
					//Charge!!!!!
					//
					gameWorld.getGameUi().getMessages().showWarning("charge!!!");
					player.addAction(StepAction.obtain(ActorSteps.moveTo(target.getX(), target.getY(), 0.2f)));
				} else {
					gameWorld.getGameUi().getMessages().showWarning("target not in range");
				}
			} else {
				gameWorld.getGameUi().getMessages().showWarning("Invalid target");
			}
		} 
		//
		// If user is Foe
		//
		else if(user instanceof Foe) {
			float trueRange = range + (user.getWidth() + user.getHeight()) / 4 + 
   										(player.getWidth() + player.getHeight()) / 4;

			if(Vector2.dst(user.getX(), user.getY(), playerX, playerY) <= trueRange) {
				//TODO success
				gameWorld.getGameUi().getMessages().showWarning("charge");
				gameWorld.getProjectileFactory().createPowerAttack(user.getX(), user.getY(), 0);
			} else {
				//gameWorld.getGameUi().getMessages().showWarning("target not in range");
			}
		} 
		return cooldown;
	}

}
