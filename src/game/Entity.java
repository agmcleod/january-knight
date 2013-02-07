package game;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
	private int x;
	private int y;
	private Texture textureImage;
	// used for storing various animations for when a sprite needs to be animated
	private Array<Animation> animations;
	// used for still images to be controlled for various states.
	private Array<TextureRegion> frames;
	private boolean animated;
	private float animationSpeed = 0.02f;
	private int focusedAnimation = 0;
	private Rectangle collisionRectangle;
	private ShapeRenderer renderer;
	
	public Entity() {
		
	}
	
	public Entity(int x, int y, Texture textureImage, boolean animated) {
		init(x, y, textureImage, animated);
	}
	
	public void addAnimation(int[][] coords) {
		TextureRegion[] frames = new TextureRegion[coords.length];
		for(int i = 0; i < coords.length; i++) {
			frames[i] = new TextureRegion(textureImage, coords[i][0] * 32, coords[i][1] * 32, 32, 32);
		}
		this.animations.add(new Animation(this.animationSpeed, frames));
	}

	public void addFrame(int x, int y, int width, int height) {
		TextureRegion region = new TextureRegion(this.textureImage, x, y, width, height);
		this.frames.add(region);
	}
	
	public void debug(OrthographicCamera camera) {
		renderer.begin(ShapeType.Rectangle);
		renderer.setProjectionMatrix(camera.combined);
		renderer.setColor(Color.WHITE);
		renderer.rect(x + collisionRectangle.x, y + collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
		renderer.end();
	}
	
	public Array<Animation> getAnimations() {
		return animations;
	}

	public Rectangle getCollisionRectangle() {
		return this.collisionRectangle;
	}
	
	public Rectangle getWorldCollisionRectangle() {
		return new Rectangle(x + collisionRectangle.x, y + collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
	}

	public Array<TextureRegion> getFrames() {
		return frames;
	}
	
	public int getRightX() {
		return (int) collisionRectangle.width + x;
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
			animations = new Array<Animation>();
		}
		else {
			frames = new Array<TextureRegion>();
		}
		renderer = new ShapeRenderer();
	}

	public boolean isAnimated() {
		return animated;
	}

	public void render(float stateTime, SpriteBatch batch, OrthographicCamera camera) {
		TextureRegion currentFrame;
		if(this.animated) {
			Animation currentAnimation = this.animations.get(focusedAnimation);
			currentFrame = currentAnimation.getKeyFrame(stateTime, true); 
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

	public void setAnimations(Array<Animation> animations) {
		this.animations = animations;
	}

	public void setCollisionRectangle(Rectangle collisionRectangle) {
		this.collisionRectangle = collisionRectangle;
	}

	public void setFrames(Array<TextureRegion> frames) {
		this.frames = frames;
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
