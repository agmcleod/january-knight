package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MyGame extends Game {
	
	private GameScreen gameScreen;
	
	public MyGame() {
		this.gameScreen = new GameScreen(this);
	}
	
	@Override
	public void create() {
		setScreen(gameScreen);
	}
	
	@Override
	public void dispose() {
		gameScreen.dispose();
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	
	public static void main(String args[]) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 800;
		cfg.height = 640;
		LwjglApplication app = new LwjglApplication(new MyGame(), cfg);
	}
}
