package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player extends MoveableEntity {

	private int originalX;
	private int originalY;

	public Player(int x, int y, Texture texture) {
		super(x, y, texture, true);
		originalX = x;
		originalY = y;
		setCollisionRectangle(new Rectangle(0, 0, 39, 128));
		setMaxVelocity(new Vector2(10f, 7.5f));
		setVelocity(new Vector2(0f, 0f)); 
		Array<AnimationFrame> frames = new Array<AnimationFrame>();
		frames.add(new AnimationFrame(0, 0, 128, 128));
		this.addAnimation("idle", frames, true, 0.2f);
		
		Array<AnimationFrame> attackFrames = new Array<AnimationFrame>();
		attackFrames.add(new AnimationFrame(1, 0, 128, 128));
		attackFrames.add(new AnimationFrame(2, 0, 128, 128));
		attackFrames.add(new AnimationFrame(3, 0, 128, 128));
		attackFrames.add(new AnimationFrame(2, 0, 128, 128));
		attackFrames.add(new AnimationFrame(1, 0, 128, 128));
		this.addAnimation("attack", attackFrames, false, 0.08f);
	}
	
	public void animationCallback() {
		switch(getState()) {
		case ATTACKING:
			setState(states.IDLE);
			setCurrentAnimation("idle");
		case IDLE:
		case DEAD:
			
		}
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
