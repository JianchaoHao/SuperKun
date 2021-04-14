package Game;

import java.awt.Image;
import java.awt.Rectangle;

public class PinkZhuan extends Element{ 
	public PinkZhuan(int x, int y, int width, int height, Image img) {
		super(x, y, width, height, img);
	}
	public Rectangle getRect() {                                      //得到矩形范围
		return new Rectangle(x, y, 45,45);     //可以说是长方形的构造函数。x，y指的是左上角对应的坐标
	}
}
