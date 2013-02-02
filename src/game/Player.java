package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends MoveableEntity {

	private int originalX;
	private int originalY;

	public Player(int x, int y) {
		super(x, y,new Texture(Gdx.files.internal("assets/hero.png")));
		originalX = x;
		originalY = y;
		setEntityDimensions(new Vector2(44, 128));
		setMaxVelocity(new Vector2(10f, 7.5f));
		setVelocity(new Vector2(0f, 0f));

		addFrame(44, 0, 39, 128);
	}

	public boolean reset() {
		if(getTopY() < 0) {
			setX(originalX);
			setY(originalY);
			return true;
		}
		else {
			return false;
		}
	}	
}
