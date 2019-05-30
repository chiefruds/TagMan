package model;

import java.awt.Dimension;
import java.awt.Point;

public class TagMan extends GameObject {
	
	public TagMan() {
		
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
