package com.us.ld31.utils.tiles;

<<<<<<< HEAD
=======
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
>>>>>>> 42dbc330e5cd86ea06ad8a596db7e5dd0a2faa49
import com.badlogic.gdx.scenes.scene2d.Group;

public class WorldMap extends Group {
	
	private final int tilesY = 40;
<<<<<<< HEAD
	private int tilesX;
	private float tileWH;
=======
	private float width = Gdx.graphics.getWidth();
	private float height = Gdx.graphics.getHeight();
	private float tileWH = height / 40;
	private int tilesX = MathUtils.ceil(Math.abs(width/tileWH)); 
>>>>>>> 42dbc330e5cd86ea06ad8a596db7e5dd0a2faa49
	
	public WorldMap() {
		
	}

	public float getTileSize() {
		return tileWH;
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		tileWH = height / 40;
		tilesX = Math.round(width/tileWH)+1;
	}

	public boolean isWalkable(final int x, final int y) {
		return true;
	}
	
	public int getTilesX() {
		return tilesX;
	}
	
	public int getTilesY() {
		return tilesY;
	}
	
}
