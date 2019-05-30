package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Game;

public class GameView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Font textFont;
	private Font numberFont;
	private Game game;

	public GameView() {
		
		//the fonts for the text and the numbers.
		textFont = new Font("serif", 10, 20);
		numberFont = new Font("serif", 10, 40);
		
		setPreferredSize(new Dimension(200, 400));
		setBackground(Color.BLACK);

	}

	//the update from game.
	@Override
	public void update(Observable o, Object game) {
		this.game = (Game) game;
		repaint();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//draw the rectangles.
		g.setColor(Color.GRAY);
		g.fillRect(40, 25, 100, 40);
		g.fillRect(20, 75, 150, 70);
		g.fillRect(40, 250, 100, 40);
		g.fillRect(20, 300, 150, 70);

		//draw the score and text.
		g.setColor(Color.YELLOW);
		g.setFont(textFont);
		g.drawString("score: ", 70, 50);
		g.drawString("level: ", 70, 275);
		
		//draw the numbers for the score and text.
		if (game != null) {
			g.setFont(numberFont);
			g.drawString("" + game.getScore(), 85, 125);
			g.drawString("" + game.getLevel(), 85, 350);
		}

		//a line to indicate the border between gameView and timeView.
		g.setColor(Color.GRAY);
		g.fillRect(0, this.getHeight() - 10, this.getWidth(), 20);
	}

}
