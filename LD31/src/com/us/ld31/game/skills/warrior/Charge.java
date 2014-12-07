package com.us.ld31.game.skills.warrior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.PlayerCharacter;
import com.us.ld31.game.foestuff.Foe;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.utils.Log;
import com.us.ld31.utils.steps.scene.ActorSteps;
import com.us.ld31.utils.steps.scene.StepAction;

public class Charge implements Skill {
	
	private boolean skillSuccessful;
	
	private float[] cooldowns = {20,16,12};
	private float rangeInTiles = 10f;
	private float range;
	
	private Vector2 tmpV = new Vector2();;

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
			
			float trueRange = range + (player.getWidth()) / 2 + (player.getWidth()) / 2;
			
			float angle = tmpV.set(mouseX - playerX - player.getWidth() / 2, 
								   mouseY - playerY - player.getHeight() / 2)
							  .angle();
			
			float targetX = MathUtils.cosDeg(angle) * trueRange + playerX;
			float targetY = MathUtils.sinDeg(angle) * trueRange + playerY;
			
			//
			//Charge!!!!!
			//
			gameWorld.getGameUi().getMessages().showWarning("charge!!!");
			player.addAction(StepAction.obtain(ActorSteps.moveTo(targetX, targetY, 0.2f)));
			skillSuccessful = true;
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
				skillSuccessful = true;
			} else {
				//gameWorld.getGameUi().getMessages().showWarning("target not in range");
				skillSuccessful = false;
			}
		} 
		return 0;
		//return skillSuccessful ? cooldowns[skillLevel - 1] : cooldowns[skillLevel - 1] / 2f;
	}

}
