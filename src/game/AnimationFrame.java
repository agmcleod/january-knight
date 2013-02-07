package game;

public class AnimationFrame {
	int column;
	int row;
	int width;
	int height;
	
	public AnimationFrame(int column, int row, int width, int height) {
		this.column = column;
		this.row = row;
		this.width = width;
		this.height = height;
	}
	
	public int getXCoordinate() {
		return column * width;
	}
	
	public int getYCoordinate() {
		return row * height;
	}
}
