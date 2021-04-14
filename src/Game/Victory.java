package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Victory extends JFrame implements ActionListener{	
	JButton start;      										//创建按钮               	
	JButton about;
	JButton exit;	
	ImageIcon startbg;											//创建图片变量			
	ImageIcon exitbg;	
	Image bg1;
	int score;
	//构造方法
	public Victory(int score) {									
		this.newObject();										//调用方法
		this.setFrame();										//调用方法	
		this.score = score;
		JButton[] but={start,exit};                   	//建立一个数组存放按钮
		for(int i=0;i<=1;i++) {
			but[i].setBounds(300+i*600,500,240,70); 				//设置按钮出现位置及大小
			this.add(but[i]); 									//把按钮添加到窗口中 
			but[i].addActionListener(this); 					//增加 监听
		}			
		this.setVisible(true);									//将窗口显示出来		
	}
	//用来new对象的方法
	public void newObject() {
		startbg = new ImageIcon("image/zailai2.png");
		exitbg  = new ImageIcon("image/tuichu2.png");
		bg1     = new ImageIcon("image/youxishengli.png").getImage();
		start   = new JButton("再来一次",startbg);		
		exit    = new JButton("舍萝退出",exitbg);
	}
	//用来对窗口进行一些设置的方法
	public void setFrame() {
		this.setSize(1500, 800);									//设置窗口大小
		this.setTitle("守护成功");
		this.setLocationRelativeTo(null);						//设置窗口出现在屏幕中央
		this.setResizable(false);								//设置窗口大小不可改变
		this.setLayout(null);									//取消窗口jfrm的默认布局管理器	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //单击关闭按钮，结束程序
	}	
	//事件发生时的处理方法 
	public void  actionPerformed(ActionEvent e) {                   
		JButton btn = (JButton)e.getSource();                   //创建按钮对象并获取它的资源	
		if(btn==start) { 
			this.dispose();
			try {
				new Gameinterface();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} 
		else if(btn==exit) { 
			//创建一个确认对话框
			int n=JOptionPane.showConfirmDialog(this,"不要再守护人家了吗了吗？","关闭游戏",JOptionPane.YES_NO_OPTION);    //showConfirmDialog确认对话框。框内框
			//如果按下yes按钮出现的操作
			if(n==JOptionPane.YES_OPTION) {
				this.dispose();                         //关闭这个界面
			}		
		}
	}
	public void paint(Graphics g) {
		g.drawImage(bg1,0,0,1500,800,null);
		start.repaint();
		exit.repaint();
		g.setFont(new Font("楷体",Font.ITALIC,60));           //而是需要该类的一个实例化对象，并且对对像进行调用，而this就是指代的对象                
		g.setColor(Color.yellow);                              //上面两个00表示初始位置
		
		g.drawString("你的成绩是:"+score, 550,450);              //调整结束界面的score出现位置
	}
	


}
