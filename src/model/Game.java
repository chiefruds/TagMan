package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable implements Runnable {

	private ArrayList<Wall> wallArray;
	private ArrayList<Dash> dashArray;
	private TagMan tagMan;
	private int level;
	private int score;

	public Game() {
		score = 0;
		level = 1;

		// set the level to 1 when the game starts.
		setLevel(level);

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score += score;
	}

	public int getLevel() {
		return level;
	}

	// a method to set the level.
	public void setLevel(int lvl) {

		Dimension wallSize = new Dimension(100, 460);
		Dimension dashSize = new Dimension(15, 70);
		Point wallOne = new Point(0, 0);
		Point wallTwo = new Point(1199, 0);
		Point wallThree = new Point(0, 550);
		Point wallFour = new Point(1199, 550);
		Point wallFive = new Point(290, 300);

		level = lvl;

		// setting up level 1.
		if (level == 1) {
			tagMan = new TagMan();
			wallArray = new ArrayList<Wall>();
			dashArray = new ArrayList<Dash>();

			for (int i = 0; i < 4; i++) {
				wallArray.add(new Wall());
				wallArray.get(i).setSize(wallSize);
			}

			int dashPosX = 110;
			int dashMargin = 105;

			for (int i = 0; i < 10; i++) {
				dashArray.add(new Dash());

				dashPosX += dashMargin;
				dashArray.get(i).setLocation(new Point(dashPosX, 0));
				dashArray.get(i).setSize(dashSize);
			}

			wallArray.get(0).setLocation(wallOne);
			wallArray.get(1).setLocation(wallTwo);
			wallArray.get(2).setLocation(wallThree);
			wallArray.get(3).setLocation(wallFour);
		} else if (level == 2) {
			tagMan = new TagMan();
			wallArray = new ArrayList<Wall>();
			dashArray = new ArrayList<Dash>();

			for (int i = 0; i < 5; i++) {
				wallArray.add(new Wall());
				wallArray.get(i).setSize(new Dimension(100, 460));
			}

			int dashPosX = 110;
			int dashMargin = 105;

			for (int i = 0; i < 10; i++) {
				dashArray.add(new Dash());

				dashPosX += dashMargin;
				dashArray.get(i).setLocation(new Point(dashPosX, 0));
				dashArray.get(i).setSize(dashSize);
			}

			wallArray.get(0).setLocation(wallOne);
			wallArray.get(1).setLocation(wallTwo);
			wallArray.get(2).setLocation(wallThree);
			wallArray.get(3).setLocation(wallFour);
			wallArray.get(4).setLocation(wallFive);
			wallArray.get(4).setSize(new Dimension(30, 460));
		}
	}

	public ArrayList<Wall> getWallArray() {
		return wallArray;
	}

	public ArrayList<Dash> getDashArray() {
		return dashArray;
	}

	// update for the observers.
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(90);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setChanged();
			notifyObservers(this);
		}

	}

	public TagMan getTagMan() {
		return tagMan;
	}

}
