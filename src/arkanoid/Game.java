package arkanoid;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener {
	// KeyListener: for detecting the arrow keys
	// ActionListener: for moving the bal

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Hit {
		NO_HIT, TOP, BOTTOM, LEFT, RIGHT
	}

	public static int BACKGROUND_WIDTH = 437;
	public static int BACKGROUND_HEIGHT = 450;
	public static int MARGIN = 35;
	public static int MARGIN_TOP = Main.HEIGHT - BACKGROUND_HEIGHT + 15;
	public static int MARGIN_BETWEEN_BRICKS = 3;
	public static int BRICK_ROW = 3;
	public static int BRICK_COL = 5;

	public static int BACKGROUND_X = 0;
	public static int BACKGROUND_Y = 40;

	public static String FONT_NAME = "Monospaced";
	public static int FONT_SIZE_BIG = 17;
	public static int FONT_SIZE_SMALL = 15;

	public static boolean DEBUG_SHAPES = false;
	public static int DEBUG_SHAPE_MARGIN = 5;
	public static boolean DEBUG_INIT_BRICKS = true;
	public static boolean DEBUG_INIT_BACKGROUND = true;

	private boolean play = false;
	private int score = 0;
	private int totalBricks = BRICK_ROW * BRICK_COL;
	private Timer timer;
	private int delay = 10;

	private Image backgroundImage;
	private Image paddleImage;
	private Image ballImage;
	private Image brickImage;

	private Ball ball;
	private Paddle paddle;
	private Brick[][] bricks = new Brick[BRICK_ROW][BRICK_COL];

	

	public Game() throws IOException {

		// Images
		backgroundImage = ImageIO.read(Game.class.getResource("/background.png"));
		paddleImage = ImageIO.read(Game.class.getResource("/paddle.png"));
		ballImage = ImageIO.read(Game.class.getResource("/ball.png"));
		brickImage = ImageIO.read(Game.class.getResource("/brick.png"));

		// Game Objects
		ball = new Ball();
		paddle = new Paddle();
		this.initBlocks();

		// Settings
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		timer = new Timer(delay, this);
		timer.start();

	}

	public void paint(Graphics g) {

		// Code to avoid lag on linux
		Toolkit.getDefaultToolkit().sync();

		// Background
		g.setColor(Color.black);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		if (DEBUG_INIT_BACKGROUND) {
			g.drawImage(backgroundImage, Game.BACKGROUND_X, Game.BACKGROUND_Y, Game.BACKGROUND_WIDTH,
					Game.BACKGROUND_HEIGHT, this);
		}

		// the paddle
		g.drawImage(paddleImage, this.paddle.getX(), Paddle.PADDLE_TOP, Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT,
				this);

		// the ball
		g.drawImage(ballImage, this.ball.getX(), this.ball.getY(), Ball.SIZE, Ball.SIZE, this);

		// the bricks
		this.drawBricks(g);

		// HELPERS
		if (Game.DEBUG_SHAPES) {
			drawBallHelper(g);
			drawPaddleHelper(g);
			drawBricksHelper(g);
		}

		// The score
		this.drawScore(g);

		// Victory or Game Over
		if (this.isVicotry()) {
			this.drawVictory(g);
		} else if (this.isGameOver()) {	
			this.drawGameOver(g);
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// timer.start();
		if (play) {
			this.ball.move();
			this.bounceBrick();
			this.bounce();
		}
		repaint();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			this.play = true;
			this.paddle.moveRight();
			break;

		case KeyEvent.VK_LEFT:
			this.play = true;
			this.paddle.moveLeft();
			break;

		case KeyEvent.VK_ENTER:
			if (!play) {
				//this.ball = new Ball();
				//this.paddle = new Paddle();
				//this.initBlocks();
				//this.score = 0;
				//this.totalBricks = BRICK_ROW * BRICK_COL;
				//this.timer.stop();
				this.setVisible(false);
				Main.closeGame();
				Main.saveScore(score);
				Main.menu.setVisible(true);
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void drawRectangle(Graphics g, Rectangle r) {
		double rLocationX = r.getLocation().getX();
		double rLocationY = r.getLocation().getY();
		double rWidth = r.getWidth();
		double rHeight = r.getHeight();

		Rectangle rTop = new Rectangle((int) rLocationX, (int) rLocationY, (int) rWidth, (int) Game.DEBUG_SHAPE_MARGIN);
		Rectangle rBottom = new Rectangle((int) rLocationX, (int) rLocationY + (int) rHeight - Game.DEBUG_SHAPE_MARGIN,
				(int) rWidth, (int) Game.DEBUG_SHAPE_MARGIN);
		Rectangle rLeft = new Rectangle((int) rLocationX, (int) rLocationY + Game.DEBUG_SHAPE_MARGIN,
				(int) Game.DEBUG_SHAPE_MARGIN, (int) rHeight - Game.DEBUG_SHAPE_MARGIN * 2);
		Rectangle rRight = new Rectangle((int) rLocationX + (int) rWidth - Game.DEBUG_SHAPE_MARGIN,
				(int) rLocationY + Game.DEBUG_SHAPE_MARGIN, Game.DEBUG_SHAPE_MARGIN,
				(int) rHeight - Game.DEBUG_SHAPE_MARGIN * 2);

		g.setColor(Color.green);
		g.drawRect(rTop.x, rTop.y, rTop.width, rTop.height);
		g.setColor(Color.red);
		g.drawRect(rBottom.x, rBottom.y, rBottom.width, rBottom.height);
		g.setColor(Color.cyan);
		g.drawRect(rLeft.x, rLeft.y, rLeft.width, rLeft.height);
		g.setColor(Color.yellow);
		g.drawRect(rRight.x, rRight.y, rRight.width, rRight.height);

	}

	public void drawBallHelper(Graphics g) {
		this.drawRectangle(g, this.ball.getShape());
	}

	public void drawPaddleHelper(Graphics g) {
		this.drawRectangle(g, this.paddle.getShape());
	}

	public void drawBricksHelper(Graphics g) {
		for (int j = 0; j < Game.BRICK_ROW; j++) {
			for (int i = 0; i < Game.BRICK_COL; i++) {
				if (this.bricks[j][i].isAlive()) {
					this.drawRectangle(g, this.bricks[j][i].getShape());
				}
			}
		}
	}
	
	public void drawBricks(Graphics g) {
		for (int j = 0; j < Game.BRICK_ROW; j++) {
			for (int i = 0; i < Game.BRICK_COL; i++) {
				if (this.bricks[j][i].isAlive()) {
					g.drawImage(brickImage, this.bricks[j][i].getX(), this.bricks[j][i].getY(), Brick.BRICK_WIDTH,
							Brick.BRICK_HEIGHT, this);
				}
			}
		}
	}

	public void drawGameOver(Graphics g) {
		String gameOverMsg = "Game Over. Your score is " + score;
		String pressEnterMsg = "Press ENTER to exit";
		this.play = false;
		g.setColor(Color.white);
		g.setFont(new Font(Game.FONT_NAME, Font.BOLD, Game.FONT_SIZE_BIG));
		int gameOverMsgWidth = g.getFontMetrics().stringWidth(gameOverMsg);
		g.drawString(gameOverMsg, (Main.WIDTH - gameOverMsgWidth) / 2, Main.HEIGHT / 2);
		g.setFont(new Font(Game.FONT_NAME, Font.BOLD, Game.FONT_SIZE_SMALL));
		int pressEnterMsgWidth = g.getFontMetrics().stringWidth(pressEnterMsg);
		g.drawString(pressEnterMsg, (Main.WIDTH - pressEnterMsgWidth) / 2, Main.HEIGHT / 2 + 30);
	}

	public void drawVictory(Graphics g) {
		String gameOverMsg = "You Win!";
		String pressEnterMsg = "Press ENTER to exit";
		this.play = false;
		g.setColor(Color.green);
		g.setFont(new Font(Game.FONT_NAME, Font.BOLD, Game.FONT_SIZE_BIG));
		int gameOverMsgWidth = g.getFontMetrics().stringWidth(gameOverMsg);
		g.drawString(gameOverMsg, (Main.WIDTH - gameOverMsgWidth) / 2, Main.HEIGHT / 2);
		g.setFont(new Font(Game.FONT_NAME, Font.BOLD, Game.FONT_SIZE_SMALL));
		int pressEnterMsgWidth = g.getFontMetrics().stringWidth(pressEnterMsg);
		g.drawString(pressEnterMsg, (Main.WIDTH - pressEnterMsgWidth) / 2, Main.HEIGHT / 2 + 30);

	}

	public void drawScore(Graphics g) {
		String scoreMsg = "Score: " + score;
		g.setColor(Color.white);
		g.setFont(new Font(Game.FONT_NAME, Font.BOLD, Game.FONT_SIZE_SMALL));
		int scoreWidth = g.getFontMetrics().stringWidth(scoreMsg);
		g.drawString(scoreMsg, Main.WIDTH - scoreWidth - Game.MARGIN, Game.MARGIN_TOP / 2);

		scoreMsg = "Bricks: " + totalBricks;
		g.setColor(Color.white);
		g.setFont(new Font(Game.FONT_NAME, Font.BOLD, Game.FONT_SIZE_SMALL));
		scoreWidth = g.getFontMetrics().stringWidth(scoreMsg);
		g.drawString(scoreMsg, Game.MARGIN_TOP, Game.MARGIN_TOP / 2);
	}

	public Hit isIntersection(Rectangle b, Rectangle p) {
		Hit hitted = Hit.NO_HIT;

		double bLocationX = b.getLocation().getX();
		double bLocationY = b.getLocation().getY();
		double bWidth = b.getWidth();
		double bHeight = b.getHeight();

		double pLocationX = p.getLocation().getX();
		double pLocationY = p.getLocation().getY();
		double pWidth = p.getWidth();
		double pHeight = p.getHeight();

		Rectangle bTop = new Rectangle((int) bLocationX, (int) bLocationY, (int) bWidth, (int) Game.DEBUG_SHAPE_MARGIN);
		Rectangle bBottom = new Rectangle((int) bLocationX, (int) bLocationY + (int) bHeight - Game.DEBUG_SHAPE_MARGIN,
				(int) bWidth, (int) Game.DEBUG_SHAPE_MARGIN);
		Rectangle bLeft = new Rectangle((int) bLocationX, (int) bLocationY + Game.DEBUG_SHAPE_MARGIN,
				(int) Game.DEBUG_SHAPE_MARGIN, (int) bHeight - Game.DEBUG_SHAPE_MARGIN * 2);
		Rectangle bRight = new Rectangle((int) bLocationX + (int) bWidth - Game.DEBUG_SHAPE_MARGIN,
				(int) bLocationY + Game.DEBUG_SHAPE_MARGIN, Game.DEBUG_SHAPE_MARGIN,
				(int) bHeight - Game.DEBUG_SHAPE_MARGIN * 2);

		Rectangle pTop = new Rectangle((int) pLocationX, (int) pLocationY, (int) pWidth, (int) Game.DEBUG_SHAPE_MARGIN);
		Rectangle pBottom = new Rectangle((int) pLocationX, (int) pLocationY + (int) pHeight - Game.DEBUG_SHAPE_MARGIN,
				(int) pWidth, (int) Game.DEBUG_SHAPE_MARGIN);
		Rectangle pLeft = new Rectangle((int) pLocationX, (int) pLocationY + Game.DEBUG_SHAPE_MARGIN,
				(int) Game.DEBUG_SHAPE_MARGIN, (int) pHeight - Game.DEBUG_SHAPE_MARGIN * 2);
		Rectangle pRight = new Rectangle((int) pLocationX + (int) pWidth - Game.DEBUG_SHAPE_MARGIN,
				(int) pLocationY + Game.DEBUG_SHAPE_MARGIN, Game.DEBUG_SHAPE_MARGIN,
				(int) pHeight - Game.DEBUG_SHAPE_MARGIN * 2);

		if (b.intersects(p)) {
			if (bRight.intersects(pLeft)) {
				hitted = Hit.LEFT;
				System.out.println("LEFT");
			} else if (bLeft.intersects(pRight)) {
				hitted = Hit.RIGHT;
				System.out.println("RIGHT");
			} else if (bBottom.intersects(pTop)) {
				hitted = Hit.TOP;
				System.out.println("TOP");
			} else if (bTop.intersects(pBottom)) {
				hitted = Hit.BOTTOM;
				System.out.println("BOTTOM");
			}
		}
		return hitted;
	}

	public Hit bounce() {
		Hit hittedAt = isIntersection(this.ball.getShape(), this.paddle.getShape());
		if (hittedAt == Hit.LEFT || hittedAt == Hit.RIGHT || this.ball.getX() < Game.MARGIN
				|| this.ball.getX() > Main.WIDTH - Game.MARGIN - Ball.SIZE) {
			this.ball.bounceX();
		} else if (hittedAt == Hit.TOP || this.ball.getY() < Game.MARGIN_TOP) {
			this.ball.bounceY();

		}
		return hittedAt; 
	}

	public void initBlocks() {
		int posx = Brick.BRICK_START_X;
		int posy = Brick.BRICK_START_Y;
		for (int j = 0; j < BRICK_ROW; j++) {
			posy = Brick.BRICK_START_Y + j * (Brick.BRICK_HEIGHT + Brick.BRICK_MARGIN);
			for (int i = 0; i < BRICK_COL; i++) {
				posx = Brick.BRICK_START_X + i * (Brick.BRICK_WIDTH + Brick.BRICK_MARGIN);
				this.bricks[j][i] = new Brick(posx, posy);
			}
			posx = Brick.BRICK_START_X;
		}
	}


	public Hit bounceBrick() {
		Hit bounce = Hit.NO_HIT; 
		for (int j = 0; j < Game.BRICK_ROW; j++) {
			for (int i = 0; i < Game.BRICK_COL; i++) {
				if (this.bricks[j][i].isAlive()) {
					Hit hittedAt = isIntersection(this.ball.getShape(), this.bricks[j][i].getShape());
					if (hittedAt != Hit.NO_HIT) {
						this.bricks[j][i].setAlive(false);
						this.score += 5;
						this.totalBricks--;
						if (hittedAt == Hit.LEFT || hittedAt == Hit.RIGHT) {
							this.ball.bounceX();
							bounce = hittedAt; 
							break;
						} else if (hittedAt == Hit.TOP || hittedAt == Hit.BOTTOM) {
							this.ball.bounceY();
							bounce = hittedAt; 
							break;
						}
					}

				}
			}
		}
		return bounce;
	}

	public boolean isGameOver() {
		boolean gameOver = false;
		if (this.ball.getY() > Main.HEIGHT) {
			gameOver = true;
			this.play = false;
		}
		return gameOver;
	}

	public boolean isVicotry() {
		boolean victory = false;
		if (this.totalBricks == 0) {
			victory = true;
			this.play = false;
		}
		return victory;
	}
	
	//Test method 
	public void setBall(Ball b) {
		this.ball = b; 
	}
	//Test method
	public Brick getBrick(int j , int i) {
		return this.bricks[j][i];
	}
	//Test method
	public void setOneBrick(Brick b) {
		this.bricks = new Brick[BRICK_ROW][BRICK_COL];
		this.bricks[0][0] = b; 
	}
	

}
