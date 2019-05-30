package model;

import java.awt.Dimension;
import java.awt.Point;

public abstract class GameObject {
	protected Dimension size;
	protected Point location;
	
	protected Dimension getSize() {
		return size;
	}
	
	protected Point getLocation() {
		return location;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
}
