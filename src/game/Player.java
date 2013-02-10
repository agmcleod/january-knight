package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player extends MoveableEntity {

	private int originalX;
	private int originalY;
	private Weapon sword;

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
		
		initWeapon();
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
	
	public void initWeapon() {
		this.sword = new Weapon(new Rectangle(getX() + 35, getY() + 50, 60, 10), 15f);
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
	
	public void render(SpriteBatch batch, OrthographicCamera camera) {
		super.render(batch, camera);
		sword.debug(camera);
	}
	
	public void update() {
		super.update();
		sword.update(getX() + 35, getY() + 50);
	}
}
