package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndScreen implements Screen {
	
	private BitmapFont font;
	private SpriteBatch batch;
	private MyGame game;
	private boolean playerWon;
	
	public EndScreen(MyGame game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		String message = "You lost the game, because you died.";
		if(playerWon) {
			message = "You won the game by killing the dragons!";
		}
		font.draw(batch, message, 50, Gdx.graphics.getHeight() / 2 + 50);
		font.draw(batch, "Press Escape to quit or Enter to play again", 50, Gdx.graphics.getHeight() / 2);
		batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		else if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			game.setScreen(game.getGameScreen());
		}
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	
	public void setPlayerWon(boolean value) {
		playerWon = value;
	}

	@Override
	public void show() {
		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"), Gdx.files.internal("assets/font.png"), false);
		batch = new SpriteBatch();
	}

}
