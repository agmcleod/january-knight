package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {
	
	private Texture playerTexture;
	
	public Player(int x, int y) {
		super();
		playerTexture = new Texture(Gdx.files.internal("assets/player.png"));
		init(x, y, 64, 128, playerTexture, false);
		addFrame(0, 0, 64, 128);
	}
}
