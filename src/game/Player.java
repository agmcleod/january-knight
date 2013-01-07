package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {
	
	private Texture playerTexture;
	
	public Player(int x, int y) {
		super();
		playerTexture = new Texture(Gdx.files.internal("assets/hero.png"));
		init(x, y, 128, 128, playerTexture, false);
		addFrame(0, 0, 128, 128);
	}
}
