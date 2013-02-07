package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class DragonSpawn extends MoveableEntity {

	public DragonSpawn(int x, int y, Texture texture) {
		super(x, y, texture, true);
		setCollisionRectangle(new Rectangle(0, 0 ,128, 128));
		setMaxVelocity(new Vector2(12f, 9f));
		setVelocity(new Vector2(0f, 0f));
		
		Array<AnimationFrame> frames = new Array<AnimationFrame>();
		frames.add(new AnimationFrame(0, 2, 128, 128));
		addAnimation("idle", frames, true);
	}

}
