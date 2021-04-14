package Game;

import java.awt.Image;

public abstract class Element {
	public int x,y;
	public int width,height;
	public Image img;
	public Element(int x, int y, int width, int height, Image img) {    
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img=img;		
	}
	public int getX() {
	    return x;
	}
	public void setX(int x) {
	    this.x = x;
	}
	public int getY() { 
	    return y;
	}
	public void setY(int y) {
	    this.y = y;
	}
}
