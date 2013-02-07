package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends MoveableEntity {

	private int originalX;
	private int originalY;

	public Player(int x, int y, Texture texture) {
		super(x, y, texture);
		originalX = x;
		originalY = y;
		setCollisionRectangle(new Rectangle(44, 0, 39, 128));
		setMaxVelocity(new Vector2(10f, 7.5f));
		setVelocity(new Vector2(0f, 0f));

		//addFrame(44, 0, 39, 128);
		addFrame(0, 0, 128, 128);
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
