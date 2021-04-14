package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;


public class Kunkun extends Thread{	
	public int life;																		//声明kunkun的生命
	public int score;																		//声明得分
	public int x,y;																		    //声明x和y
	public int width,height;									 		 					//声明width和height
	public int xSpeed,ySpeed;				 							 					//声明speed
	public Map map;																			//声明地图
	public Image img;											 							//声明图片		
	public Gameinterface gi;																//声明游戏界面gi
	public boolean jumpFlag = true;															//声明能否跳跃为true
	public boolean up = false,left=false,right=false; 										//声明各个按键初始为false
	public String dir ="up";																//声明字符串变量dir初值为up
	private String Dir_Right = "right",Dir_Left = "left",Dir_Up="up",Dir_Down="down";		//声明四个字符串变量
	ArrayList<Bullet>Bullets = new ArrayList<Bullet>();     	         				    // 创建数组列表对象Bullets
	ArrayList<Bullet>RestBullets = new ArrayList<Bullet>();           						// 创建数组列表对象RestBullets
	//构造函数对kunkun进行设置
	public Kunkun(Map map,Gameinterface gi) {	
		x = 0;													//一系列赋值
		y = 600;
		score = 0;
		life = 1;
		xSpeed = 3;
		ySpeed = 2;
		width = 55;
		height = 55;
		this.map = map;
		this.gi = gi;
		img = new ImageIcon("image/jingzhikun.png").getImage();
		Bullets = new ArrayList<Bullet>();
		this.Gravity();
	}	
	//kunkun画自己的方法
	public void drawSelf(Graphics g) {
		g.drawImage(img,x,y,width,height,null);								//画背景
		drawBullet(g);														//画子弹
		Kunkunmove();														//调用kunkun的
		bulletHit_Element();												//调用子弹和障碍物的碰撞
		gameover();															//调用游戏结束的方法
	}	
	//kunkun移动的规则方法
	public void Kunkunmove() {	
			if(left){                                                    	//按左键时
				if (hit(Dir_Left)) {										//如果撞上障碍物
					this.xSpeed = 0;										//X轴上速度为0，即产生碰撞上的效果	
				}
				if (this.x > 0) {											//若没撞上障碍物且x>0
					this.x -= this.xSpeed;									//kunkun向左移动
				}
				this.xSpeed = 3;											//恢复x轴上的速度为3
			}
			if(right){       									  
				if (hit(Dir_Right)) {
					this.xSpeed = 0;
				}
				if (this.x <= 1450) {
					this.x += this.xSpeed;
				}
				this.xSpeed = 3;
			}
			if(up) {                 									 
				if(this.y <= 0) {											//若Y<=0
					this.ySpeed = 0;										//Y轴上的速度为0
				}
				if(jumpFlag && !isGravity){									//如果不在地上
					jumpFlag=false;											//不能跳跃
					new Thread(){											//新的线程									
						public void run(){
							jump();											//调用跳跃的方法
							jumpFlag=true;									//可以跳跃
						}
					}.start();												//调用start方法启动线程
				}
			}
			if(y>=800) {													//如果掉出屏幕下端，生命值-1
				life--;
			}
			try {
				this.sleep(15);												//游戏画面更新的速度
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	//向上跳的函数
	public void jump(){
		int jumpHeigh=0;													//定义初始高度为0												
		for (int i = 0; i < 100; i++) {										//循环且设置跳跃高度为100
			this.y-=ySpeed;																										
			jumpHeigh++;
			if(hit(Dir_Up)){												//碰撞到了上边，跳出循环停止跳跃，实现顶墙
				break;				
			}
			try {
				Thread.sleep(6);											//跳跃时上升的速度
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
		for (int i = 0; i <jumpHeigh; i++) {							
			this.y += ySpeed;
			if(hit(Dir_Down)){
				this.ySpeed=0;
			}
			try {
				Thread.sleep(6);											//跳跃时下落的速度			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
		this.ySpeed=2;														//还原速度
	}
	//检查是否贴地
	public boolean isGravity=false;   
	//重力线程
	public void Gravity(){
		new Thread(){
			public void run(){			
				while(true){
					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}					
					while(true){
						if(!jumpFlag){										//如果不能跳跃，跳出循环			
							break;
						}					
						if(hit(Dir_Down)){									//如果下方碰撞，跳出循环
							break;
						}
						y+=ySpeed;																						
						try {
							sleep(10);										//下落每一个ySpeed间的时间间隔
						} catch (InterruptedException e) {
							e.printStackTrace();
						}	
					}
				}
			}
		}.start();	
	}
	//kunkun和砖块以及篮球的碰撞
	public boolean hit(String dir) {										//传入参数字符串“dir”
		Rectangle KunkunRec = null;											
		KunkunRec = new Rectangle(this.x, this.y-10,25, 30);				//kunkun矩形
		for (int i = 0; i < map.list.size(); i++) {
			Element obstacl = map.list.get(i);
			Rectangle rect = null;
			if (dir.equals("left")) {
				rect = new Rectangle(obstacl.x-x+5, obstacl.y-20,obstacl.width, obstacl.height);
			}
			if (dir.equals("right")) {
				rect = new Rectangle(obstacl.x-x-12, obstacl.y-20,obstacl.width, obstacl.height);
			}
			if (dir.equals("up")) {
				rect = new Rectangle(obstacl.x-x, obstacl.y,obstacl.width, obstacl.height);
			}
			if (dir.equals("down")) {
				rect = new Rectangle(obstacl.x-x, obstacl.y-30,obstacl.width, obstacl.height);
			}	
			if (KunkunRec.intersects(rect)) {
				   
				//kunkun与篮球碰撞，吃掉篮球
				if(map.list.get(i) instanceof Basketball) {
					map.list.remove(i);
					score += 100;
					return false;
				}
				//kunkun与qiaoabiluo碰撞，吃掉qiaoboluo，游戏结束
				else if(map.list.get(i) instanceof Qiaobiluo) {
					
					life ++;
					return false;
				}
				if(map.list.get(i) instanceof Jianci) {
					map.list.remove(i);
					score += 100;
					life --;
					return false;
				}
				else {						
					return true;
				}
			}

		}
		return false;
	}
	
	//子弹和地图的碰撞
	public void bulletHit_Element() {
		Rectangle bulletRec = null;
		for (int m = 0; m < Bullets.size(); m++) {
			Bullet bullet = Bullets.get(m);		 
			bulletRec = new Rectangle(bullet.getX(), bullet.getY()-10, 30, 30);				//子弹矩形的大小		
			for (int i = 0; i < map.getList().size() && m < Bullets.size(); i++) {
				Element obstacl = map.getList().get(i);
				Rectangle rect = null;
				rect = new Rectangle(obstacl.x-x-20, obstacl.y-15, obstacl.width, obstacl.height);
				if(bulletRec.intersects(rect)) {
					if(map.list.get(i) instanceof PinkZhuan) {
						Bullets.remove(m);
					}
					else if (map.list.get(i) instanceof Qiaobiluo) {
						Bullets.remove(m);
					}
					else if (map.list.get(i) instanceof Brick) {
						Bullets.remove(m);			
					}
					else if (map.list.get(i) instanceof Jianci) {
						score +=100;
						Bullets.remove(m);
						map.list.remove(i);			
					}
				}
			}
		}
	}

	//点击事件（动作开始的方法）
	public void moveStart(KeyEvent e) { 								  
		if(e.getKeyCode()==KeyEvent.VK_UP){                               //按下up
			dir = "up";
			if(hit(Dir_Down)){
				up = true;
			}else {
				up = false;
			}
			img = new ImageIcon("image/tiaokun.png").getImage();		  //按下时更改图片
		
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){                             //按下left
			dir = "left";
			left=true;
			img = new ImageIcon("image/left.gif").getImage();			  //按下时更改图片
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){                            //按下right
			dir = "right";
			right=true;
			img = new ImageIcon("image/right.gif").getImage();			  //按下时更改图片
		}
	}
	//松开事件（动作结束的方法）
	public void moveStop(KeyEvent e) {                                   
		if(e.getKeyCode()==KeyEvent.VK_UP){                               //松开up
			up=false;
			img = new ImageIcon("image/jingzhikun.png").getImage();		  //松开时切换为静止图
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){                             //松开left
			left=false;
			img = new ImageIcon("image/jingzhikun.png").getImage();		  //松开时切换为静止图
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){                            //松开right
			right=false;
			img = new ImageIcon("image/jingzhikun.png").getImage();		  //松开时切换为静止图
		}
	}
	//画子弹的方法
	public void drawBullet(Graphics g) {
		for (int i = 0; i < Bullets.size(); i++) {
			Bullets.get(i).drawpaintSelf(g);
		}
	}
	//得到子弹对象的方法
	public ArrayList<Bullet> getBullets() {
		return Bullets;
	}
	//发射子弹的方法
	public void makeBullet(KeyEvent e) {                                
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {                      //按下space，生成一个子弹
			setBullets();
		}
	}
	//生成子弹的方法
	public void setBullets() {                                      
		Bullets.add(new Bullet(x+30, y));                          //加入Allbullet并设置相对于我的飞机的位置
	}
	//游戏结束的方法
	public void gameover() {
		if (life >= 2) {
			gi.dispose();
			new Victory(score);
		}
		if(life <= 0) {
			gi.dispose();
			new Defeat(score);
		}
	}
}