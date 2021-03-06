package game;

import game.Animation.AnimationEvent;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity implements Animation.AnimationEventListener {
	private int x;
	private int y;
	private Texture textureImage;
	// used for storing various animations for when a sprite needs to be animated
	private ObjectMap<String, Animation> animations;
	// used for still images to be controlled for various states.
	private ObjectMap<String, TextureRegion> frames;
	private boolean animated;
	private String focusedAnimation;
	private String callbackAnimation;
	private Rectangle collisionRectangle;
	private ShapeRenderer renderer;
	protected float stateTime = 0f;
	protected boolean flicker = false;
	private float flickerTime = 0;
	private states state;
	private boolean visible = true;
	
	enum states {
		IDLE, ATTACKING, DEAD
	}
	
	public Entity() {
		
	}
	
	public Entity(int x, int y, Texture textureImage, boolean animated) {
		init(x, y, textureImage, animated);
	}
	
	public void addAnimation(String key, Array<AnimationFrame> animationFrames, boolean looping, float animationSpeed) {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		Iterator<AnimationFrame> it = animationFrames.iterator();
		while(it.hasNext()) {
			AnimationFrame frame = it.next();
			frames.add(new TextureRegion(textureImage, frame.getXCoordinate(), frame.getYCoordinate(), frame.width, frame.height));
		}
		Animation a = new Animation(animationSpeed, frames, looping);
		a.addEventListener(this);
		
		this.animations.put(key, a);
		if(this.animations.size == 1) {
			focusedAnimation = key;
		}
	}

	public void addFrame(int x, int y, int width, int height, String name) {
		TextureRegion region = new TextureRegion(this.textureImage, x, y, width, height);
		this.frames.put(name, region);
	}
	
	public void animationCallback() {
		
	}
	
	public void attack() {
		if(getState() != states.ATTACKING) {
			setCurrentAnimation("attack");
			setState(states.ATTACKING);
		}
	}
	
	public void debug(OrthographicCamera camera) {
		renderer.begin(ShapeType.Rectangle);
		renderer.setProjectionMatrix(camera.combined);
		renderer.setColor(Color.WHITE);
		renderer.rect(x + collisionRectangle.x, y + collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
		renderer.end();
	}
	
	public ObjectMap<String, Animation> getAnimations() {
		return animations;
	}

	public Rectangle getCollisionRectangle() {
		return this.collisionRectangle;
	}
	
	public int getCollisionRightX() {
		return (int) (collisionRectangle.width + collisionRectangle.x) + x;
	}
	
	public int getCollisionX() {
		return (int) collisionRectangle.x + x;
	}
	
	public int getCollisionTopY() {
		return (int) (collisionRectangle.height + collisionRectangle.y) + y; 
	}
	
	public int getCollisionY() {
		return (int) collisionRectangle.y + y;
	}
	
	public Animation getCurrentAnimation() {
		return this.animations.get(focusedAnimation);
	}
	
	public Vector2 getLowerLeft() {
		Rectangle r = getWorldCollisionRectangle();
		return new Vector2(r.x, r.y);
	}
	
	public Vector2 getLowerRight() {
		Rectangle r = getWorldCollisionRectangle();
		return new Vector2(r.x + r.width, r.y);
	}
	
	public Vector2 getUpperLeft() {
		Rectangle r = getWorldCollisionRectangle();
		return new Vector2(r.x, r.y + r.height);
	}
	
	public Vector2 getUpperRight() {
		Rectangle r = getWorldCollisionRectangle();
		return new Vector2(r.x + r.width, r.y + r.height);
	}
	
	public Rectangle getWorldCollisionRectangle() {
		return new Rectangle(x + collisionRectangle.x, y + collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
	}

	public ObjectMap<String, TextureRegion> getFrames() {
		return frames;
	}

	public Texture getTextureImage() {
		return textureImage;
	}
	
	public int getTopY() {
		return y + (int) collisionRectangle.height;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void init(int x, int y, Texture textureImage, boolean animated) {
		this.setX(x);
		this.setY(y);
		this.setTextureImage(textureImage);
		this.setAnimated(animated);
		if(this.animated) {
			animations = new ObjectMap<String, Animation>();
		}
		else {
			frames = new ObjectMap<String, TextureRegion>();
		}
		renderer = new ShapeRenderer();
		callbackAnimation = "";
		setState(states.IDLE);
	}

	public boolean isAnimated() {
		return animated;
	}
	
	public boolean isFlickering() {
		return this.flicker;
	}
	
	@Override
	public void onAnimationEnded(AnimationEvent e) {
		animationCallback();
	}

	public void render(SpriteBatch batch) {
		TextureRegion currentFrame;
		float t = Gdx.graphics.getDeltaTime();
		stateTime += t;
		if(this.animated) {
			Animation currentAnimation = getCurrentAnimation();
			currentFrame = currentAnimation.getKeyFrame(stateTime);
		}
		else {
			currentFrame = frames.get(focusedAnimation);
		}
		if(!this.isFlickering() || (this.isFlickering() && this.visible)) {
			batch.draw(currentFrame, x, y);
		}
		if(this.isFlickering()) {
			updateFlickerTime(t);
		}
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}

	public void setCollisionRectangle(Rectangle collisionRectangle) {
		this.collisionRectangle = collisionRectangle;
	}
	
	public void setCurrentAnimation(String name) {
		focusedAnimation = name;
		stateTime = 0;
	}
	
	public void setFlicker(boolean flicker) {
		this.flicker = flicker;
	}

	public void setTextureImage(Texture textureImage) {
		this.textureImage = textureImage;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public states getState() {
		return state;
	}

	public void setState(states state) {
		this.state = state;
	}
	
	public void updateFlickerTime(float t) {
		flickerTime += t;
		this.visible = !this.visible;
		if(flickerTime > 0.7) {
			flickerTime = 0;
			this.flicker = false;
		}
	}
}
