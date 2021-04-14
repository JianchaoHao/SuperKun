package Game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Bullet {
	public Image bullets_img;
	public int x;
	public int y;
	public int speed;
	public int width,height;
	
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
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	//对子弹进行一些设置
	public Bullet(int x ,int y) {
		this.x = x;
		this.y = y;
		this.width = 60;
		this.height = 60;
		bullets_img = new ImageIcon("image/zidan.png").getImage();
		speed = 8;
	}
	//设置子弹的画法
	public void drawpaintSelf(Graphics g) {
		g.drawImage(bullets_img, x+=speed, y+10,width,height, null);
	}
}
