package view;

import java.awt.Color;
import java.awt.Graphics;

import model.TagMan;

public class TagManPainterPlain {

	private int playerX;
	private int playerY;
	private int playerW;
	private int playerH;

	public void paint(Graphics g, PlayView view, TagMan man) {
		
		//the colors for the TagMan.
		Color outerColor;
		Color middleColor;
		Color centerColor;

		if (man.getLocation() != null) {
			//setting the location of the TagMan in variables.
			playerX = man.getLocation().x;
			playerY = man.getLocation().y;
			playerW = man.getSize().width;
			playerH = man.getSize().height;

			//when the game is won or the level is won show different collors else show the normal colors.
			if(view.isGameWon() || view.isLevelWon()) {
				outerColor = Color.decode("#05583E");
				middleColor = Color.decode("#6BB28E");
				centerColor = Color.decode("#99FFCC");
			} else {
			outerColor = Color.RED;
			middleColor = Color.decode("#E25F17");
			centerColor = Color.decode("#E2C517");
			}
			
			//draw three ovals to draw the player.
			g.setColor(outerColor);
			g.fillOval(playerX, playerY, playerW, playerH);
			g.setColor(middleColor);
			g.fillOval(playerX + 5, playerY + 5, (int)(playerW / 1.2), (int) (playerH / 1.2));
			g.setColor(centerColor);
			g.fillOval(playerX + 15, playerY + 15, (int)(playerW / 2), (int) (playerH / 2));
		}
	}

}
