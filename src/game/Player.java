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
		this.addAnimation("attack", attackFrames, false, 0.09f);
		
		initWeapon();
	}
	
	public void animationCallback() {
		switch(getState()) {
		case ATTACKING:
			int frame = getCurrentAnimation().getFrameNumber(this.stateTime);
			sword.setCurrentPosition(frame);
			setState(states.IDLE);
			setCurrentAnimation("idle");
			sword.setCurrentPosition(0);
		case IDLE:
		case DEAD:
			
		}
	}
	
	public Weapon getWeapon() {
		return this.sword;
	}
	
	public void initWeapon() {
		this.sword = new Weapon(new Rectangle(getX() + 35, getY() + 50, 40, 8), 8f);
		this.sword.addPosition(10, 0, 37);
		this.sword.addPosition(25, 5, 17);
		this.sword.addPosition(35, 18, 15);
		this.sword.addPosition(25, 5, 17);
		this.sword.addPosition(10, 0, 37);
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
		if(getState() == states.ATTACKING) {
			int frame = getCurrentAnimation().getFrameNumber(this.stateTime);
			sword.setCurrentPosition(frame + 1);
		}
		sword.debug(camera);
	}
	
	public void update() {
		super.update();
		sword.update(getX() + 35, getY() + 50);
	}
}
