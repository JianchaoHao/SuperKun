package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gameinterface extends JFrame implements KeyListener{					//继承JFrame并增加监听接口
	public JPanel jP;																//新建一个私有JPanel，命名为jp目的是为了避免考虑双缓冲问题，因为游戏界面在JFrame上会闪烁
	public int x1 = 0;
	public int y1 = 0;
	public int width1 = 3000;
	public int height1 = 800;	
	Map map = new Map();
	Kunkun kunkun = new Kunkun(map, this);
	public ArrayList<Element> eneryList = new ArrayList<Element>();
	public	ArrayList<Bullet> Bullets = new ArrayList<Bullet>();

	//构造对JFrame设置的方法
	public Gameinterface()throws Exception{	
		this.setSize(1500, 800);									 				//设置窗口大小
		this.setTitle("Superkun");													//设置窗口标题
		this.setResizable(false);													//设置窗口大小不可改变
		this.setLayout(null);														//取消面板jfrm的默认布局管理器	
		this.setLocationRelativeTo(null);											//设置窗口出现在屏幕中央
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);								//单击关闭按钮，结束程序
		this.setVisible(true);														//将窗口显示出来
		jP = new game(this);														//新建一个面板
		this.add(jP);																//把面板添加到窗口上
		this.addKeyListener(this);													//增加监听	
	}
	//构造方法对JPanel进行设置
	class game extends JPanel {
		public Image bg2 = new ImageIcon("image/bg2.png").getImage();			
		public JFrame jf;	
		//构造游戏界面
		public game(JFrame jf) {													//传入
			this.setSize(1500,800);													//设置窗口大小
			this.setLocation(0, 0);													//设置面板位置
			this.setVisible(true);													//将面板显示出来	
		}
		//构造画笔，画背景，画kunkun
		public void paint(Graphics g) {			
			g.drawImage(bg2,-kunkun.x,y1,width1,height1,this);						//画背景
			kunkun.drawSelf(g);														//调用kunkun画自己的方法
			g.setFont(new Font("楷体",Font.ITALIC,30));								//设置字体的样式、大小
			g.setColor(Color.RED);													//设置字体颜色
			g.drawString("score:"+kunkun.score, 50, 30);							//在屏幕左上角输出实时得分
			//画地图
			eneryList = map.getList();
			for (int i = 0; i < eneryList.size(); i++) {									
				Element nowObject = eneryList.get(i);
				g.drawImage(nowObject.img, nowObject.x-kunkun.x, nowObject.y ,nowObject.width, nowObject.height, null);
			}				
			//画子弹
			Bullets = kunkun.getBullets();
			for (int i = 0; i < Bullets.size(); i++) {
				Bullet nowBullet = Bullets.get(i);
				if (nowBullet.getX() > 3000 || nowBullet.getY() < 0 || nowBullet.getY() > 800) {
					Bullets.remove(i);
				} else {
					nowBullet.drawpaintSelf(g);
				}		
			}
			repaint();															//在kunkun存在的每个位置重画kunkun		
		}
	}
	//点击事件
	public void keyPressed(KeyEvent e) {										
		kunkun.moveStart(e);
	}
	//松开事件
	public void keyReleased(KeyEvent e) {										
		kunkun.moveStop(e);	
		kunkun.makeBullet(e);
	}
	public void keyTyped(KeyEvent e) {	
	}
}