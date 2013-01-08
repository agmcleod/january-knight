package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player extends Entity {
	
	private Texture playerTexture;
	private PolygonShape collisionBox;
	private BodyDef bodyDef;
	private Body body;
	
	public Player(int x, int y, World world) {
		super();
		playerTexture = new Texture(Gdx.files.internal("assets/hero.png"));
		init(x, y, 128, 128, playerTexture, false, world);
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x * GameScreen.WORLD_TO_BOX, y * GameScreen.WORLD_TO_BOX);
		body = getWorld().createBody(bodyDef);
		collisionBox = new PolygonShape();
		collisionBox.setAsBox(32 * GameScreen.WORLD_TO_BOX, 64 * GameScreen.WORLD_TO_BOX, new Vector2(64 * GameScreen.WORLD_TO_BOX, 64 * GameScreen.WORLD_TO_BOX), 0.0f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = collisionBox;
		fixtureDef.density = 10f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0f;
		
		body.createFixture(fixtureDef);
		collisionBox.dispose();
		
		addFrame(0, 0, 128, 128);
	}
	
	public void dispose() {
		playerTexture.dispose();
	}
	
	public void update() {
		this.setX((int) ((body.getPosition().x) * GameScreen.BOX_TO_WORLD));
		this.setY((int) ((body.getPosition().y) * GameScreen.BOX_TO_WORLD));
	}
}
