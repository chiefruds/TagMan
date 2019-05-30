package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class TimeView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Font textFont;
	private Font numberFont;
	private int time;
	int size;

	public TimeView() {
		
		//initializing the time and size.
		time = 30;
		size = time * 10;
		
		//making the fonts for the text and numbers.
		textFont = new Font("serif", 10, 20);
		numberFont = new Font("serif", 10, 40);
		
		setPreferredSize(new Dimension(200, 600));
		setBackground(Color.BLACK);
	}
	
	//the update that gets the time from timeControl.
	@Override
	public void update(Observable timeControl, Object time) {
		this.time = (int) time;
		size = this.time * 10;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//the baseline is where the timer will start.
		int baseline = 500;
		
		//set the color to the amount of time left.
		if (time > 15) {
			g.setColor(Color.BLUE);
		} else if (time > 7) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.RED);
		}
		
		
		//drawing the timer and a white border over the timer.
		g.fillRect(70, baseline - size, 50, size);
		g.setColor(Color.WHITE);
		g.drawRect(70, baseline - size, 50, size);
		
		//drawing the rectangles for the text.
		g.setColor(Color.GRAY);
		g.fillRect(20, 20, 150, 70);
		g.fillRect(45, 560, 100, 30);
		
		
		//drawing the seconds left.
		g.setFont(numberFont);
		g.setColor(Color.YELLOW);
		g.drawString("" + time, 80, 65);

		g.setFont(textFont);
		g.drawString("seconds ", 65, 580);
	}

}
