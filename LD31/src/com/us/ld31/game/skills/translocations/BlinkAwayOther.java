package com.us.ld31.game.skills.translocations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.us.ld31.game.GameWorld;
import com.us.ld31.game.foestuff.Foe;
import com.us.ld31.game.skills.Skill;
import com.us.ld31.utils.tiles.Tile;

public class BlinkAwayOther implements Skill {

	private float cooldown = 20;
	private int rangeInTiles = 6;
	
	@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight()-Gdx.input.getY();
		Foe targetActor = gameWorld.getFoeManager().getFoeByCoords(mouseX, mouseY);
		if(targetActor != null) {
			Array<Tile> tiles = targetActor.getTilesInRange(rangeInTiles);
			for(Tile t : tiles) {
				if(!t.isWalkable()) {
					tiles.removeValue(t, false);
				}
			}
			//Remove all the tiles which would bring target closer to the caster
			Array<Tile> availableTargetTiles = new Array<Tile>();
			if(user.getX() <= targetActor.getX()) {  //If caster is to the left to the target
				for(Tile t : tiles) {
					if(t.getX() >= targetActor.getX()) {
						if(user.getY() <= targetActor.getY()) {
							if(t.getY() >= targetActor.getY()) {
								availableTargetTiles.add(t);
								t.getColor().a = 0;
							}
						} else {
							if(t.getY() < targetActor.getY()) {
								availableTargetTiles.add(t);
								t.getColor().a = 0;
							}
						}
					}
				}
			} else {  //If caster is to the right...
				for(Tile t : tiles) {
					if(t.getX() < targetActor.getX()) {
						if(user.getY() <= targetActor.getY()) {
							if(t.getY() >= targetActor.getY()) {
								availableTargetTiles.add(t);
								t.getColor().a = 0;
							}
						} else {
							if(t.getY() < targetActor.getY()) {
								availableTargetTiles.add(t);
								t.getColor().a = 0;
							}
						}
					}
				}
			}
			int t = MathUtils.random(availableTargetTiles.size-1);
			System.out.println(t);
			System.out.println(availableTargetTiles.get(t).getX() + " " + availableTargetTiles.get(t).getY());
			availableTargetTiles.get(t).getColor().a = 0.5f;
			targetActor.setX(availableTargetTiles.get(t).getX());
			targetActor.setY(availableTargetTiles.get(t).getY());
		} else {
			gameWorld.getGameUi().getMessages().showWarning("Invalid target!");
		}
		
		return cooldown;
	}
	
	/*@Override
	public float activate(Actor user, GameWorld gameWorld, int skillLevel) {
		boolean canLand = false;
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight()-Gdx.input.getY();
		Actor targetActor = gameWorld.getFoeManager().getFoeByCoords(mouseX, mouseY);
		
		if(targetActor != null) {
			float dir = (new Vector2(targetActor.getX()-user.getX(), targetActor.getY()-user.getY())).angle();
			int degreeOffset = 30;
			dir += MathUtils.random(0, degreeOffset*2)-degreeOffset;
			float range = rangeInTiles*gameWorld.getWorldMap().getTileSize();
			//range = MathUtils.random(range*0.3f, range);
			
			float lastAvailableX = targetActor.getX();
			float lastAvailableY = targetActor.getY();
			for(int i=0; i<rangeInTiles; i++) {
				float r=i*gameWorld.getWorldMap().getTileSize();
				float targetX = targetActor.getX() + r*MathUtils.cosDeg(dir);
				float targetY = targetActor.getY() + r*MathUtils.sinDeg(dir);
				int t = gameWorld.getWorldMap().getTile(targetX, targetY);
				int indexX = (int) Math.floor(targetX / gameWorld.getWorldMap().getTileSize());
				int indexY = (int) Math.floor(targetY / gameWorld.getWorldMap().getTileSize());
				if(t < gameWorld.getWorldMap().getChildren().size && t >= 0) {
				if(gameWorld.getWorldMap().isWalkable(indexX, indexY)) {
					if(t < gameWorld.getWorldMap().getChildren().size && t >= 0) {
						if(((Tile)gameWorld.getWorldMap().getChildren().get(t)).isWalkable()) {
							lastAvailableX = targetX;
							lastAvailableY = targetY;
							canLand = true;
//							mouseX = targetX;
//							mouseY = targetY;
						}
					}
				}
				}
			}
			/*float targetX = targetActor.getX() + range*MathUtils.cosDeg(dir);
			float targetY = targetActor.getY() + range*MathUtils.sinDeg(dir);
			int t = gameWorld.getWorldMap().getTile(targetX, targetY);
			int indexX = (int) Math.floor(targetX / gameWorld.getWorldMap().getTileSize());
			int indexY = (int) Math.floor(targetY / gameWorld.getWorldMap().getTileSize());
			if(gameWorld.getWorldMap().isWalkable(indexX, indexY)) {
				if(t < gameWorld.getWorldMap().getChildren().size && t >= 0) {
					if(((Tile)gameWorld.getWorldMap().getChildren().get(t)).isWalkable()) {
						canLand = true;
						mouseX = targetX;
						mouseY = targetY;
					}
				}
			}*/
		/*} else {
			//TODO: Warning msg and sound
		}
		
		if(canLand) {
			targetActor.setX(mouseX);
			targetActor.setY(mouseY);
		}
		
		return cooldown;
	}*/

}
