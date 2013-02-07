package game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
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

public class Entity {
	private int x;
	private int y;
	private Texture textureImage;
	// used for storing various animations for when a sprite needs to be animated
	private ObjectMap<String, Animation> animations;
	// used for still images to be controlled for various states.
	private ObjectMap<String, TextureRegion> frames;
	private boolean animated;
	private float animationSpeed = 0.02f;
	private String focusedAnimation;
	private String callbackAnimation;
	private Rectangle collisionRectangle;
	private ShapeRenderer renderer;
	private boolean attacking = false;
	private float stateTime = 0f;
	
	public Entity() {
		
	}
	
	public Entity(int x, int y, Texture textureImage, boolean animated) {
		init(x, y, textureImage, animated);
	}
	
	public void addAnimation(String key, Array<AnimationFrame> animationFrames, boolean looping) {
		TextureRegion[] frames = new TextureRegion[animationFrames.size];
		Iterator<AnimationFrame> it = animationFrames.iterator();
		int i = 0;
		while(it.hasNext()) {
			AnimationFrame frame = it.next();
			frames[i] = new TextureRegion(textureImage, frame.getXCoordinate(), frame.getYCoordinate(), frame.width, frame.height);
			i++;
		}
		Animation a = new Animation(this.animationSpeed, frames);
		if(looping) {
			a.setPlayMode(Animation.LOOP);
		}
		else {
			a.setPlayMode(Animation.NORMAL);
		}
		
		this.animations.put(key, a);
		if(this.animations.size == 1) {
			focusedAnimation = key;
		}
	}

	public void addFrame(int x, int y, int width, int height, String name) {
		TextureRegion region = new TextureRegion(this.textureImage, x, y, width, height);
		this.frames.put(name, region);
	}
	
	public void attack() {
		if(!attacking) {
			attacking = true;
			setCurrentAnimation("attack", "idle");
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
	}

	public boolean isAnimated() {
		return animated;
	}

	public void render(SpriteBatch batch, OrthographicCamera camera) {
		TextureRegion currentFrame;
		stateTime += Gdx.graphics.getDeltaTime();
		if(this.animated) {
			Animation currentAnimation = this.animations.get(focusedAnimation);
			currentFrame = currentAnimation.getKeyFrame(stateTime, true);
			if(currentAnimation.isAnimationFinished(stateTime) && !callbackAnimation.isEmpty()) {
				focusedAnimation = callbackAnimation;
				if(focusedAnimation.equals("idle")) {
					this.attacking = false;
				}
			}
		}
		else {
			currentFrame = frames.get(focusedAnimation);
		}
		batch.draw(currentFrame, x, y);
		debug(camera);
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}

	public void setCollisionRectangle(Rectangle collisionRectangle) {
		this.collisionRectangle = collisionRectangle;
	}
	
	public void setCurrentAnimation(String name, String callbackAnimation) {
		this.focusedAnimation = name;
		this.callbackAnimation = callbackAnimation;
		stateTime = 0;
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
}
