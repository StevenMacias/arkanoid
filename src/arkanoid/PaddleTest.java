package arkanoid;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaddleTest {
	private Paddle paddle; 
	@BeforeEach
	void setUp() throws Exception {
		this.paddle = new Paddle(); 
	}

	@Test
	void testPaddle() {
		assertEquals(Paddle.STARTING_X, this.paddle.getX());
		assertEquals(Paddle.STARTING_Y, this.paddle.getY());
	}

	@Test
	void testGetShape() {
		Rectangle rect = new Rectangle(this.paddle.getX(),this.paddle.getY(),Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT);
		Rectangle newRect = this.paddle.getShape(); 
		assertEquals(rect, newRect);
	}

	@Test
	void testMoveRight() {
		this.paddle.moveRight();
		assertEquals(Paddle.STARTING_X+Paddle.SPEED_X, this.paddle.getX());
	}

	@Test
	void testMoveLeft() {
		this.paddle.moveLeft();
		assertEquals(Paddle.STARTING_X-Paddle.SPEED_X, this.paddle.getX());
	}

}
