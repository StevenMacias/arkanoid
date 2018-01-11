package arkanoid;

import java.awt.Rectangle;

public class Paddle {
	public static int SPEED_X = 10; ; 
	public static int PADDLE_WIDTH = 88; 
	public static int PADDLE_HEIGHT = 22; 
	public static int PADDLE_TOP = Main.HEIGHT-Paddle.PADDLE_HEIGHT;
	public static int STARTING_X = Main.WIDTH / 2 - Paddle.PADDLE_WIDTH/2; 
	public static int STARTING_Y = Paddle.PADDLE_TOP;
	
	private int x; 
	private int y;
	
	public Paddle() {
		this.x = STARTING_X; 
		this.y = STARTING_Y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public Rectangle getShape()
	{
		return new Rectangle(this.x, this.y, Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT); 
	}
	
	public void moveRight() {
		if (this.x+Paddle.PADDLE_WIDTH >= Main.WIDTH-Game.MARGIN) {
			this.x = Main.WIDTH-Game.MARGIN-Paddle.PADDLE_WIDTH;
		} else {
			this.x += SPEED_X; 
		}
	}
	
	public void moveLeft() {
		if (this.x < Game.MARGIN){
			this.x = Game.MARGIN; 
		} else {
			this.x -= SPEED_X; 
		}
	}
	
}
