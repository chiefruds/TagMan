package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import control.MainController;
import model.Dash;
import model.Game;
import model.TagMan;
import model.Wall;

public class PlayView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private TagManPainterPlain tagManPainter;
	private Game game;
	private Rectangle player;
	private Color bgColor;
	private TagMan man;

	private MainController mainController;

	private ArrayList<Wall> wallArray;
	private ArrayList<Dash> dashArray;
	private ArrayList<Rectangle> wallRectArray;
	private ArrayList<Rectangle> dashRectArray;

	private boolean wallHit;
	private boolean dashHit;
	private boolean gameStarted;
	private boolean gameWon;
	private boolean levelWon;

	private int playerX;
	private int playerY;
	private int playerW;
	private int playerH;

	private final int PLAYER_SPEED;
	private final int FINISH_AREA;
	private final int START_AREA;
	private final int TOP_SCREEN_END;
	private final int BOTTOM_SCREEN_END;
	private final int PLAYER_DIAGONAL_SPEED;
	private final int PLAYER_WALL_MARGIN;
	private final int PLAYER_DASH_MARGIN;

	public PlayView(MainController mainController) {

		this.mainController = mainController;

		// all final variables are here initialized.
		PLAYER_SPEED = 15;
		FINISH_AREA = 1210;
		START_AREA = 100;
		TOP_SCREEN_END = 0;
		BOTTOM_SCREEN_END = 1000;
		PLAYER_DIAGONAL_SPEED = (int) (PLAYER_SPEED / 3) * 2;
		PLAYER_WALL_MARGIN = 62;
		PLAYER_DASH_MARGIN = 59;

		// all of the booleans are here initialized.
		wallHit = false;
		dashHit = false;
		gameWon = false;
		levelWon = false;
		gameStarted = false;

		// the background color is here initialized.
		bgColor = Color.decode("#211e3b");

		// setting up the JPanel.
		setPreferredSize(new Dimension(1300, 1000));
		setBackground(bgColor);
		setFocusable(true);

		// this is the keyListener where are the KeyEvents of this program are handled.
		addKeyListener(new KeyAdapter() {

			// booleans to check if a key is pressed.
			boolean rightKeyPressed = false;
			boolean upKeyPressed = false;
			boolean downKeyPressed = false;

			public void keyPressed(KeyEvent e) {

				wallHit = false;
				// if statements to check if esc, s or l is pressed.
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					// when s is pressed game started will become true and the timer is stopped.
					if (!gameStarted && !gameWon && !levelWon) {
						gameStarted = true;
						mainController.startTimer();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_L) {
					// only if levelWon is true will this event do something.
					if (levelWon) {
						if (game.getLevel() == 1) {
							game.setLevel(2);
						} else {
							gameWon = true;
						}
					}

					levelWon = false;
				}

				// if statements to check if the keys or numpads are being pressed, for the
				// numpads booleans aren't needed.
				if (dashHit == false && gameStarted == true) {
					if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						rightKeyPressed = true;
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						upKeyPressed = true;
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						downKeyPressed = true;
					} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
						moveRight();
					} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
						moveUp();
					} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
						moveDown();
					} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
						moveRightAndUp();
					} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
						moveRightAndDown();
					}

					// according to the booleans that are true the player will move.
					if (rightKeyPressed && upKeyPressed) {
						moveRightAndUp();
					} else if (rightKeyPressed && downKeyPressed) {
						moveRightAndDown();

					} else if (rightKeyPressed) {
						moveRight();
					} else if (downKeyPressed) {
						moveDown();

					} else if (upKeyPressed) {
						moveUp();
					}
					man.setLocation(new Point(playerX, playerY));
					repaint();

				}
			}

			// when a key is released the boolean linked to that key will turn false.
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightKeyPressed = false;
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					upKeyPressed = false;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					downKeyPressed = false;
				}
			}
		});
	}

	// a method to move right.
	private void moveRight() {

		// a rectangle is made with the position the player wants to move to.
		player = new Rectangle(playerX + PLAYER_SPEED, playerY, playerW, playerH);

		// checking if the position the player wants to move to intersects with a dash
		// or wall.
		for (int i = 0; i < wallArray.size(); i++) {
			if (player.intersects(wallRectArray.get(i))) {
				playerX = wallRectArray.get(i).x - PLAYER_WALL_MARGIN;
				if (playerX < 0) {
					playerX = 0;
				}
				wallHit = true;
			}
		}

		for (int i = 0; i < dashArray.size(); i++) {
			if (player.intersects(dashRectArray.get(i))) {
				playerX = dashRectArray.get(i).x - PLAYER_DASH_MARGIN;
				wallHit = true;
			}
		}

		// checking if the player has reached the finish.
		if (playerX >= FINISH_AREA) {

			game.setScore(mainController.getTime());
			mainController.stopTimer();

			wallHit = true;
			levelWon = true;

			gameStarted = false;
		}

		// if wallHit is false the player will move
		if (!wallHit) {
			playerX += PLAYER_SPEED;
		}
	}

	// a method to move up.
	private void moveUp() {
		player = new Rectangle(playerX, playerY - PLAYER_SPEED, playerW, playerH);

		for (int i = 0; i < wallArray.size(); i++) {
			if (player.intersects(wallRectArray.get(i))) {
				wallHit = true;
			}
		}
		for (int i = 0; i < dashArray.size(); i++) {
			if (player.intersects(dashRectArray.get(i))) {
				playerY = dashRectArray.get(i).y + dashRectArray.get(i).height;
				wallHit = true;
			}
		}
		// an if statement to check if the player is going out of the frame.
		if (playerY - PLAYER_SPEED <= TOP_SCREEN_END) {
			playerY = 0;
			wallHit = true;
		}

		if (!wallHit) {
			playerY -= PLAYER_SPEED;
		}
	}

	// a method to move down.
	private void moveDown() {
		player = new Rectangle(playerX, playerY + PLAYER_SPEED, playerW, playerH);
		int bottomScreenMargin = 90;
		for (int i = 0; i < wallArray.size(); i++) {
			if (player.intersects(wallRectArray.get(i))) {
				wallHit = true;
			}
		}

		for (int i = 0; i < dashArray.size(); i++) {
			if (player.intersects(dashRectArray.get(i))) {
				playerY = dashRectArray.get(i).y;
				wallHit = true;
			}
		}

		// checking if the player is going out of the frame.
		if (playerY + bottomScreenMargin >= BOTTOM_SCREEN_END) {
			playerY = 940;
			wallHit = true;
		}

		if (!wallHit) {
			playerY += PLAYER_SPEED;
		}
	}

	// a method to move diagonal up.
	private void moveRightAndUp() {
		player = new Rectangle(playerX + PLAYER_DIAGONAL_SPEED, playerY - PLAYER_DIAGONAL_SPEED, playerW, playerH);

		for (int i = 0; i < wallArray.size(); i++) {
			if (player.intersects(wallRectArray.get(i))) {
				if (!(playerX <= START_AREA || playerX >= FINISH_AREA - 10)) {
					playerX = wallRectArray.get(i).x - PLAYER_WALL_MARGIN;
				}
				if (playerX < 0) {
					playerX = 0;
				}
				wallHit = true;
			}
		}

		for (int i = 0; i < dashArray.size(); i++) {
			if (player.intersects(dashRectArray.get(i))) {
				playerX = dashRectArray.get(i).x - PLAYER_DASH_MARGIN;
				wallHit = true;
			}
		}

		// checking if the player is going out of bounds.
		if (playerY - PLAYER_DIAGONAL_SPEED <= 0) {
			playerY = 0;
			wallHit = true;
		}

		if (!wallHit) {
			playerX += PLAYER_DIAGONAL_SPEED;
			playerY -= PLAYER_DIAGONAL_SPEED;
		}
	}

	// a method to move diagonal down.
	private void moveRightAndDown() {
		int bottomScreenMargin = 90;
		player = new Rectangle(playerX + PLAYER_DIAGONAL_SPEED, playerY + PLAYER_DIAGONAL_SPEED, playerW, playerH);

		for (int i = 0; i < wallArray.size(); i++) {
			if (player.intersects(wallRectArray.get(i))) {

				if (!(playerX <= START_AREA || playerX + 60 >= FINISH_AREA - 10)) {
					playerX = wallRectArray.get(i).x - PLAYER_WALL_MARGIN;
				}

				if (playerX < 0) {
					playerX = 0;
				}
				wallHit = true;
			}
		}

		for (int i = 0; i < dashArray.size(); i++) {
			if (player.intersects(dashRectArray.get(i))) {
				playerX = dashRectArray.get(i).x - PLAYER_DASH_MARGIN;
				wallHit = true;
			}
		}

		if (playerY + bottomScreenMargin >= BOTTOM_SCREEN_END) {
			playerY = 940;
			wallHit = true;
		}

		if (!wallHit) {
			playerX += PLAYER_DIAGONAL_SPEED;
			playerY += PLAYER_DIAGONAL_SPEED;
		}
	}

	public boolean isGameWon() {
		return gameWon;
	}

	public boolean isLevelWon() {
		return levelWon;
	}

	// the update from game.
	public void update(Observable o, Object game) {

		// initializing variables.
		this.game = (Game) game;
		tagManPainter = new TagManPainterPlain();
		man = this.game.getTagMan();
		wallArray = this.game.getWallArray();
		dashArray = this.game.getDashArray();

		// make rectangles for the dashes and the walls.
		makeRectangleArrays();

		// setting the player in start position.
		if (man.getLocation() == null) {
			man.setLocation(new Point(10, 470));
			man.setSize(new Dimension(60, 60));
		}

		// getting the player location.
		getPlayerLocation();
		runGame();
		repaint();

	}

	// a method to move the dashes and check for the intersection with the player.
	private void runGame() {
		if (gameStarted) {
			moveDashes();
			checkDashIntersection();
		}
	}

	// a method to make rectangles from the dashes and walls to check interception.
	private void makeRectangleArrays() {

		wallRectArray = new ArrayList<Rectangle>();
		dashRectArray = new ArrayList<Rectangle>();

		player = new Rectangle(playerX, playerY, playerW, playerH);
		for (int i = 0; i < wallArray.size(); i++) {
			wallRectArray.add(new Rectangle(wallArray.get(i).getLocation().x, wallArray.get(i).getLocation().y,
					wallArray.get(i).getSize().width, wallArray.get(i).getSize().height));
		}

		for (int i = 0; i < dashArray.size(); i++) {
			dashRectArray.add(new Rectangle(dashArray.get(i).getLocation().x, dashArray.get(i).getLocation().y,
					dashArray.get(i).getSize().width, dashArray.get(i).getSize().height));
		}
	}

	// a method to check the intersection between the player and the dashes.
	private void checkDashIntersection() {

		for (int i = 0; i < dashArray.size(); i++) {
			player = new Rectangle(playerX, playerY - dashArray.get(i).getDashSpeed(), playerW, playerH);
			int dashArrayX = dashArray.get(i).getLocation().x;
			int dashArrayY = dashArray.get(i).getLocation().y;
			int dashArrayH = dashArray.get(i).getSize().height;

			// an if statement to make sure the player isn't painted over the dashes.
			if (player.intersects(dashRectArray.get(i))) {
				if ((playerY <= (dashArrayY + dashArrayH)) && (playerY >= dashArrayY) && (dashArrayX > playerX + 1)) {
					dashArray.get(i).setLocation(new Point(dashArrayX, playerY - playerH));
				}
				dashHit = true;
				mainController.stopTimer();

			}
		}
	}

	// a method to put the playerLocation in int variables.
	private void getPlayerLocation() {
		playerX = man.getLocation().x;
		playerY = man.getLocation().y;
		playerW = man.getSize().width;
		playerH = man.getSize().height;
	}

	// a method to move the dashes if dashHit is true.
	private void moveDashes() {
		if (!dashHit) {
			for (int i = 0; i < dashArray.size(); i++) {
				if (dashArray.get(i).isFalling()) {
					int dashArrayX = dashArray.get(i).getLocation().x;
					int dashArrayY = dashArray.get(i).getLocation().y;
					dashArrayY += dashArray.get(i).getDashSpeed();
					dashArray.get(i).setLocation(new Point(dashArrayX, dashArrayY));
				}
			}
		}
	}

	// a method to paint the walls.
	private void paintWalls(Graphics g) {
		for (int i = 0; i < wallArray.size(); i++) {
			g.setColor(Color.GRAY);
			g.fillRect(wallArray.get(i).getLocation().x, wallArray.get(i).getLocation().y,
					wallArray.get(i).getSize().width, wallArray.get(i).getSize().height);

			g.setColor(Color.WHITE);
			g.drawRect(wallArray.get(i).getLocation().x, wallArray.get(i).getLocation().y,
					wallArray.get(i).getSize().width, wallArray.get(i).getSize().height);
		}
	}

	// a method to paint the dashes.
	private void paintDashes(Graphics g) {
		for (int i = 0; i < dashArray.size(); i++) {
			g.setColor(Color.RED);
			g.fillRect(dashArray.get(i).getLocation().x, dashArray.get(i).getLocation().y,
					dashArray.get(i).getSize().width, dashArray.get(i).getSize().height);

			g.setColor(Color.WHITE);
			g.drawRect(dashArray.get(i).getLocation().x, dashArray.get(i).getLocation().y,
					dashArray.get(i).getSize().width, dashArray.get(i).getSize().height);
		}
	}

	// a method to paint the text.
	private void paintText(Graphics g) {

		int linePosX = 400;
		int lineOneY = 300;
		int lineTwoY = 400;
		int lineThreeY = 500;
		int lineFourY = 600;
		int lineFiveY = 700;

		Font textFont = new Font("serif", 10, 50);
		g.setColor(Color.YELLOW);
		g.setFont(textFont);

		if (dashHit) {
			g.drawString("TagMan is hit", linePosX, lineOneY);
			g.drawString("game over!", linePosX, lineTwoY);
			g.drawString("your score was: " + game.getScore(), linePosX, lineThreeY);
			g.drawString("hit ESC to close the game", linePosX, lineFourY);
		} else if (levelWon) {
			g.drawString("level finished", linePosX, lineOneY);
			g.drawString("your score: " + game.getScore(), linePosX, lineTwoY);
			g.drawString("to continue click L", linePosX, lineFourY);
		} else if (gameWon) {
			g.drawString("good Job!", linePosX, lineOneY);
			g.drawString("you won the game!", linePosX, lineTwoY);
			g.drawString("your score was: " + game.getScore(), linePosX, lineThreeY);
			g.drawString("your level was:" + game.getLevel(), linePosX, lineFourY);
			g.drawString("hit ESC to close the game", linePosX, lineFiveY);
		} else if (!gameStarted) {
			if (game != null) {
				if (game.getLevel() == 1) {
					g.drawString("Welcome to tagMan!", linePosX, lineOneY);
					g.drawString("move with arrows or numpad", linePosX, lineTwoY);
					g.drawString("LEVEL " + game.getLevel(), linePosX, lineThreeY);
					g.drawString("hit S to start", linePosX, 700);
				} else {
					g.drawString("level: " + game.getLevel(), linePosX, lineThreeY);
					g.drawString("press S to start", linePosX, lineFourY);
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (game != null) {

			paintWalls(g);
			paintDashes(g);
			tagManPainter.paint(g, this, game.getTagMan());
		}
		paintText(g);

	}

}
