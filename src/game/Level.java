package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

public class Level {
	private TiledMap tiledMap;
	private TileAtlas tileAtlas;
	private TileMapRenderer tileMapRenderer;
	
	public Level(String filename) {
		tiledMap = TiledLoader.createMap(Gdx.files.internal("assets/" + filename));
		tileAtlas = new TileAtlas(tiledMap, Gdx.files.internal("assets"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 8, 8);
	}
	
	public void dispose() {
		tileMapRenderer.dispose();
		tileAtlas.dispose();
	}
	
	public void render(OrthographicCamera camera) {
		tileMapRenderer.render(camera);
	}
}
