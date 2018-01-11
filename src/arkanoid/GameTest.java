package arkanoid;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arkanoid.Game.Hit;

class GameTest {
	private Paddle paddle;
	private Brick brick;
	private Ball ball;
	private Game game;

	@BeforeEach
	void setUp() throws Exception {
		this.ball = new Ball();
		this.paddle = new Paddle();
		this.brick = new Brick(0, 0);
		this.game = new Game();
	}

	@Test
	void testIsIntersection() {
		this.ball.bounceY();
		this.ball.move();
		// Test Intersection Top
		Hit hittedAt = this.game.isIntersection(this.ball.getShape(), this.paddle.getShape());
		Hit expectedResult = Hit.TOP;
		assertEquals(expectedResult, hittedAt);
		
		//Test intersection Bottom
		//Test intersection Left
		//Test intersection Right

	}

	@Test
	void testBounce() {
		//Bounce Paddle
		assertEquals(Ball.SPEED_Y, this.ball.getDiry());
		// Change starting direction
		this.ball.bounceY();
		// Nothing happens because there is no colissions
		assertEquals(Ball.SPEED_Y * -1, this.ball.getDiry());
		// Crashes with the paddle
		this.ball.move();
		this.ball.bounceY();
		// Returns to the original position
		assertEquals(Ball.SPEED_Y, this.ball.getDiry());
		
		//Bounce Left Wall
		//Bounce Right Wall
		//Bounce Left Paddle 
		//Bouce Right Paddle

	}

	@Test
	void testInitBlocks() {
		Brick b = this.game.getBrick(0, 0);
		assertTrue(b.isAlive());
		for (int j = 0; j < Game.BRICK_ROW; j++) {
			for (int i = 0; i < Game.BRICK_COL; i++) {
				b = this.game.getBrick(j, i);
				b.setAlive(false);
			}
		}
		b = this.game.getBrick(0, 0);
		assertFalse(b.isAlive());
	}

	@Ignore
	void testBounceBrick() {
		this.brick = new Brick(Ball.SIZE, Ball.SIZE); 
		this.game.setOneBrick(this.brick);
		this.ball = new Ball(0,Ball.SIZE);
		//Bounce Brick Top 
		//Bounce Brick Bottom
		//Bounce Brick Left
		//Bounce Brick Right
		
	}

	@Test
	void testIsGameOver() {
		assertFalse(this.game.isGameOver());
		this.game.setBall(new Ball(Main.WIDTH + Ball.SIZE, Main.HEIGHT + Ball.SIZE));
		assertTrue(this.game.isGameOver());
	}

	@Test
	void testIsVicotry() {
		assertFalse(this.game.isVicotry());
		for (int j = 0; j < Game.BRICK_ROW; j++) {
			for (int i = 0; i < Game.BRICK_COL; i++) {
				Brick b = this.game.getBrick(j, i);
				b.setAlive(false);
			}
		}
		assertFalse(this.game.isVicotry());
	}

}
