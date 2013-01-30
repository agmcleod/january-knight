package game;

import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class WorldCollision {
	private Player player;
	public WorldCollision(Player player) {
		this.player = player;
	}
	
	public void checkIfPlayerIsOnGround(Level level) {
		boolean result = false;
		Iterator<Rectangle> it = level.getCollisionTiles().iterator();
		Rectangle resultingGround = null;
		while(it.hasNext()) {
			Rectangle r = it.next();
			if(r.overlaps(player.getCollisionRectangle())) {
				result = true;
				resultingGround = r;
			}
		}
		player.setTouchingOnFoot(result);
		if(result && resultingGround != null) {
			player.setY((int) (resultingGround.y + resultingGround.height));
		}
	}
	
	public void checkIfPlayerTouchesBySide(Level level) {
		Iterator<Rectangle> it = level.getCollisionTiles().iterator();
		boolean noTouching = true;
		while(it.hasNext()) {
			Rectangle r = it.next();
			if((player.getY() > r.y && player.getY() < (r.y + r.height)) 
					|| (player.getTopY() > r.y && player.getTopY() < (r.y + r.height))) {
				// touches on right of player
				if(player.getRightX() > r.x && player.getRightX() < (r.x + r.width)) {
					player.setTouchingOnRight(true);
					player.setTouchingOnLeft(false);
					noTouching = false;
					break;
				}
				// touches on the left
				else if(player.getX() > r.x && player.getX() < (r.x + r.width)) {
					player.setTouchingOnLeft(true);
					player.setTouchingOnRight(false);
					noTouching = false;
					break;
				}
			}
		}
		if(noTouching) {
			player.setTouchingOnRight(false);
			player.setTouchingOnLeft(false);
		}
	}
}
