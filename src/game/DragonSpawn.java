package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DragonSpawn extends MoveableEntity {

	public DragonSpawn(int x, int y, Texture texture) {
		super(x, y, texture);
		setCollisionRectangle(new Rectangle(0, 0 ,128, 128));
		setMaxVelocity(new Vector2(12f, 9f));
		setVelocity(new Vector2(0f, 0f));
		
		addFrame(0, 256, 128, 128);
	}

}
