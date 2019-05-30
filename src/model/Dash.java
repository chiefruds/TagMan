package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

public class Dash extends GameObject {

	private boolean falling;
	private int speed;

	public Dash() {
		//the boolean falling checks if the dash is falling and the int speed holds the speed of the dash.
		falling = false;
		Random rand = new Random();
		speed = rand.nextInt(15) + 5;
	}

	
	//getters and setters.
	
	public int getDashSpeed() {
		return speed;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public Dimension getSize() {
		return size;
	}

	public Point getLocation() {
		return location;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
}
