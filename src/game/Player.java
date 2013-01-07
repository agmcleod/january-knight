package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {
	
	private Texture playerTexture;
	private PolygonShape collisionBox;
	
	public Player(int x, int y, World world) {
		super();
		playerTexture = new Texture(Gdx.files.internal("assets/hero.png"));
		init(x, y, 128, 128, playerTexture, false, world);
		
		Body body = getBody();
		collisionBox = new PolygonShape();
		collisionBox.setAsBox(64, 64);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = collisionBox;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0f; 
		
		body.createFixture(fixtureDef);
		
		addFrame(0, 0, 128, 128);
	}
	
	public void dispose() {
		playerTexture.dispose();
		collisionBox.dispose();
	}
}
