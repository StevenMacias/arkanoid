package arkanoid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Rectangle;

import org.junit.Before;
import org.junit.jupiter.api.Test;

public class BallTest {
	private Ball ball; 
	@Before
	public void setUp() throws Exception {
		this.ball = new Ball(); 
	}

	@Test
	public void testBall() {
		assertEquals(this.ball.getX(), Ball.STARTING_X);
		assertEquals(this.ball.getY(), Ball.STARTING_Y);
		assertEquals(this.ball.getDirx(), Ball.SPEED_X);
		assertEquals(this.ball.getDiry(), Ball.SPEED_Y);
	}


	@Test
	public void testMove() {
		this.ball.move();
		assertEquals(this.ball.getX(), Ball.STARTING_X+this.ball.getDirx());
		assertEquals(this.ball.getY(), Ball.STARTING_Y+this.ball.getDiry());
		assertEquals(this.ball.getDirx(), Ball.SPEED_X);
		assertEquals(this.ball.getDiry(), Ball.SPEED_Y);	
	}

	@Test
	public void testGetShape() {
		Rectangle rect = new Rectangle(this.ball.getX(),this.ball.getY(),Ball.SIZE, Ball.SIZE );
		Rectangle newRect = this.ball.getShape(); 
		assertEquals(rect, newRect);
	}

	@Test
	public void testBounceX() {
		this.ball.bounceX();
		this.ball.move();
		//X coordinate has to changed
		assertEquals(Ball.STARTING_X+(Ball.SPEED_X*-1), this.ball.getX());
		//Y coordinate do not has change
		assertEquals(Ball.STARTING_Y+(Ball.SPEED_X), this.ball.getY());
	}

	@Test
	public void testBounceY() {
		this.ball.bounceY();
		this.ball.move();
		//X coordinate do not has to changed
		assertEquals(Ball.STARTING_X+(Ball.SPEED_Y), this.ball.getX());
		//Y coordinate do not has change
		assertEquals(Ball.STARTING_Y+(Ball.SPEED_Y*-1), this.ball.getY());
	}

}
