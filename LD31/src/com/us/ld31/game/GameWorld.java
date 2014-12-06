package com.us.ld31.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.us.ld31.LD31;
import com.us.ld31.utils.Astar;
import com.us.ld31.utils.TouchListener;

public class GameWorld extends Group {

	private final LD31 app;
	private final Character character;
	private final Astar astar;
	
	public GameWorld(final LD31 app) {
		this.app = app;
		
		character = new Character();
		addListener(new TouchListener() {
			@Override
			public void touched() {
				character.attack();
			}
		});
		
		character.setTileSize(32);
		character.setRegion(app.assets.tileGrass);
		
		Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.GREEN);
		pixmap.fillCircle(5, 5, 5);
		
		astar = new Astar(0, 0, null);
		
		Foe foe = new Foe(new TextureRegion(new Texture(pixmap)));
		
		addActor(foe);
	}
	
	public void begin() {
		addActor(character);
		character.begin();
		character.setPosition(getWidth() / 2f, getHeight() / 2f);
	}
	
	@Override
	public void act(final float delta) {
		super.act(delta);
		
		if(isTouchable()) {
			for(int i = 0; i < Character.MovementDirection.list.length; i += 1) {
				final Character.MovementDirection direction = Character.MovementDirection.list[i];
				if(Gdx.input.isKeyJustPressed(direction.key)) {
					character.movement(direction, true);
				}
				else if(!Gdx.input.isKeyPressed(direction.key) && character.isMoving(direction)) {
					character.movement(direction, false);
				}
			}
		}
	}
	
}
