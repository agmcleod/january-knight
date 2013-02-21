package game;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class WorldCollision {
	private Player player;
	public WorldCollision(Player player) {
		this.player = player;
	}
	
	public void checkIfEntityIsOnGround(Level level, MoveableEntity entity) {
		boolean result = false;
		Iterator<Rectangle> it = level.getCollisionTiles().iterator();
		Rectangle resultingGround = null;
		while(it.hasNext()) {
			Rectangle r = it.next();
			if(r.overlaps(entity.getWorldCollisionRectangle())) {
				result = true;
				resultingGround = r;
			}
		}
		entity.setTouchingOnFoot(result);
		if(entity.isFalling() && result && resultingGround != null) {
			entity.setY((int) (resultingGround.y + resultingGround.height));
		}
	}
	
	public void checkIfPlayerTouchesBySide(Level level) {
		Iterator<Rectangle> it = level.getCollisionTiles().iterator();
		boolean noTouching = true;
		while(it.hasNext()) {
			Rectangle r = it.next();
			if((player.getY() >= r.y && player.getY() < (r.y + r.height)) 
					|| (player.getTopY() >= r.y && player.getTopY() <= (r.y + r.height))) {
				// touches on right of player
				if(entityTouchesRightOfRect(player, r) && !entityTouchesLeftOfRect(player, r)) {
					player.setTouchingOnRight(true);
					player.setTouchingOnLeft(false);
					noTouching = false;
					break;
				}
				// touches on the left
				else if(!entityTouchesRightOfRect(player, r) && entityTouchesLeftOfRect(player, r)) {
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
	
	public boolean entityTouchesLeftOfRect(Entity entity, Rectangle rect) {
		boolean result = (entity.getCollisionX() >= rect.x && entity.getCollisionX() <= (rect.x + rect.width));
		return result;
	}
	
	public boolean entityTouchesRightOfRect(Entity entity, Rectangle rect) {
		boolean result = (entity.getCollisionRightX() >= rect.x && entity.getCollisionRightX() <= (rect.x + rect.width));
		return result;
	}
	
	public int[] getMinMaxValues(Vector2 lowerLeft, Vector2 lowerRight, Vector2 upperLeft, Vector2 upperRight, Vector2 axis) {
		double x = 0;
		double y = 0;
		double[] scalars = new double[4];
		
		// top right
		double projection = ((upperRight.x * axis.x + upperRight.y * axis.y) / (Math.pow(axis.x, 2) + Math.pow(axis.y, 2)));
		x = projection * axis.x;
		y = projection * axis.y;
		scalars[0] = x * upperRight.x + y * upperRight.y;
		
		// top left
		projection = ((upperLeft.x * axis.x + upperLeft.y * axis.y) / (Math.pow(axis.x, 2) + Math.pow(axis.y, 2)));
		x = projection * axis.x;
		y = projection * axis.y;
		scalars[1] = x * upperLeft.x + y * upperLeft.y;
		
		// bottom left
		projection = ((lowerLeft.x * axis.x + lowerLeft.y * axis.y) / (Math.pow(axis.x, 2) + Math.pow(axis.y, 2)));
		x = projection * axis.x;
		y = projection * axis.y;
		scalars[2] = x * lowerLeft.x + y * lowerLeft.y;
		
		// bottom right
		projection = ((lowerRight.x * axis.x + lowerRight.y * axis.y)/ (Math.pow(axis.x, 2) + Math.pow(axis.y, 2)));
		x = projection * axis.x;
		y = projection * axis.y;
		scalars[3] = x * lowerRight.x + y * lowerRight.y;
		
		double max = 0;
		double min = scalars[0];
		for(int i = 0; i < scalars.length; i++) {
			if(scalars[i] > max) {
				max = scalars[i];
			}
			
			if(scalars[i] < min) {
				min = scalars[i];
			}
		}
		return (new int[] { (int) min, (int) max });
	}
	
	public void weaponTouchesEntities(Rectangle box, float angle, Level currentLevel) {
		Vector2 axis1 = new Vector2(), axis2 = new Vector2(), axis3 = new Vector2(), axis4 = new Vector2();
		
		float a = (angle) * (MathUtils.PI/180);
		
		Vector2 upperRight = new Vector2(box.x + box.width, box.y + box.height);
		Vector2 upperLeft = new Vector2(box.x, box.y + box.height);
		Vector2 lowerRight = new Vector2(box.x + box.width, box.y);
		Vector2 lowerLeft = new Vector2(box.x, box.y);
		
		// rotate the weapon box by its angle
		upperRight.x = MathUtils.cos(a) * (upperRight.x - lowerLeft.x) - MathUtils.sin(a) * (upperRight.y - lowerLeft.y) + lowerLeft.x;
		upperRight.y = MathUtils.sin(a) * (upperRight.x - lowerLeft.x) + MathUtils.cos(a) * (upperRight.y - lowerLeft.y) + lowerLeft.y;
		
		upperLeft.x = MathUtils.cos(a) * (upperLeft.x - lowerLeft.x) - MathUtils.sin(a) * (upperLeft.y - lowerLeft.y) + lowerLeft.x;
		upperLeft.y = MathUtils.sin(a) * (upperLeft.x - lowerLeft.x) + MathUtils.cos(a) * (upperLeft.y - lowerLeft.y) + lowerLeft.y;
		
		lowerRight.x = MathUtils.cos(a) * (lowerRight.x - lowerLeft.x) - MathUtils.sin(a) * (lowerRight.y - lowerLeft.y) + lowerLeft.x;
		lowerRight.y = MathUtils.sin(a) * (lowerRight.x - lowerLeft.x) + MathUtils.cos(a) * (lowerRight.y - lowerLeft.y) + lowerLeft.y;
		
		Iterator<MoveableEntity> it = currentLevel.getEnemies().iterator();
		while(it.hasNext()) {
			MoveableEntity entity = it.next();
			axis1.x = entity.getCollisionRightX() - entity.getCollisionX();
			axis1.y = entity.getCollisionTopY() - entity.getCollisionTopY();
			
			axis2.x = entity.getCollisionRightX() - entity.getCollisionX();
			axis2.y = entity.getCollisionTopY() - entity.getCollisionY();
			
			axis3.x = upperLeft.x - lowerLeft.x;
			axis3.y = upperLeft.y - lowerLeft.y;
			
			axis4.x = upperLeft.x - upperRight.x;
			axis4.y = upperLeft.y - upperRight.y;
			
			Vector2[] axis = new Vector2[4];
			axis[0] = axis1;
			axis[1] = axis2;
			axis[2] = axis3;
			axis[3] = axis4;
			boolean result = true;
			for(int i = 0; i < axis.length; i++) {
				Vector2 currentAxis = axis[i];
				int[] boxMinMax = getMinMaxValues(new Vector2(box.x, box.y), lowerRight, upperLeft, upperRight, currentAxis);
				int[] entityMinMax = getMinMaxValues(entity.getLowerLeft(), entity.getLowerRight(), entity.getUpperLeft(), entity.getUpperRight(), currentAxis);
				
				if(entityMinMax[0] > boxMinMax[0] || entityMinMax[1] < boxMinMax[1]) {
					result = false;
					break;
				}
			}
			System.out.println(result);
		}
	}
}
