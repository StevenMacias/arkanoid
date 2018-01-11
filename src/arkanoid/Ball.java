package arkanoid;

import java.awt.Rectangle;

public class Ball {
	public static int SIZE = (int) (Paddle.PADDLE_HEIGHT*1); 
	public static int SPEED_X = -1; 
	public static int SPEED_Y = -1; 
	public static int STARTING_X = Paddle.STARTING_X + Paddle.PADDLE_WIDTH/2 - Ball.SIZE/2; 
	public static int STARTING_Y = Paddle.PADDLE_TOP - Ball.SIZE; 
	
	
	private int x; 
	private int y; 
	private int dirx; 
	private int diry; 
	
	public Ball() {
		this.x = Ball.STARTING_X;
		this.y = Ball.STARTING_Y;
		this.dirx = Ball.SPEED_X; //going left
		this.diry = Ball.SPEED_Y; //going up
	}
	
	//Test constructor; 
	public Ball(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.dirx = Ball.SPEED_X; //going left
		this.diry = Ball.SPEED_Y; //going up
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDirx() {
		return dirx;
	}
	
	public int getDiry() {
		return diry;
	}
	
	public void move() {
		this.x += dirx; 
		this.y += diry; 
	}
	
	public Rectangle getShape()
	{
		return new Rectangle(this.x, this.y, Ball.SIZE, Ball.SIZE); 
	}
	
	public void bounceX() {
		this.dirx *= -1;
	}
	
	public void bounceY() {
		this.diry *= -1;
	}
	

}
