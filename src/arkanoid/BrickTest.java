package arkanoid;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BrickTest {
	private Brick brick; 
	@BeforeEach
	public void setUp() throws Exception {
		this.brick = new Brick(0,0);
	}

	@Test
	public void testBrick() {
		assertEquals(0, this.brick.getX());
		assertEquals(0, this.brick.getY());
		assertEquals(true, this.brick.isAlive());
	}


	@Test
	public void testSetAlive() {
		assertEquals(true, this.brick.isAlive());
		this.brick.setAlive(false);
		assertEquals(false, this.brick.isAlive());
	}

	@Test
	public void testGetShape() {
		Rectangle rect = new Rectangle(this.brick.getX(),this.brick.getY(),Brick.BRICK_WIDTH, Brick.BRICK_HEIGHT );
		Rectangle newRect = this.brick.getShape(); 
		assertEquals(rect, newRect);
	}

}
