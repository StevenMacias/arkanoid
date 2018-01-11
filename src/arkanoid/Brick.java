package arkanoid;

import java.awt.Rectangle;

public class Brick {
	public static int BRICK_START_X = 60;
	public static int BRICK_START_Y = 	65;
	public static int BRICK_MARGIN = 5;
	public static int BRICK_WIDTH = 60;
	public static int BRICK_HEIGHT = 25;
	
	private int x;
	private int y; 
	private boolean alive;
	
	public Brick(int x, int y) {
		this.x = x; 
		this.y = y;
		this.alive = Game.DEBUG_INIT_BRICKS; 
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}


	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public Rectangle getShape()
	{
		return new Rectangle(this.x, this.y, Brick.BRICK_WIDTH, Brick.BRICK_HEIGHT); 
	}
	
	
}
