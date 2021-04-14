package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Maininterface extends JFrame implements ActionListener{	
	JButton start;      										//创建按钮               	
	JButton about;
	JButton exit;	
	ImageIcon startbg;											//创建图片变量			
	ImageIcon aboutbg;
	ImageIcon exitbg;	
	JLabel jlab;												//创建背景
	ImageIcon bg1;
	//构造方法
	public Maininterface() {									
		this.newObject();										//调用方法
		this.setFrame();										//调用方法		
		JButton[] but={start,about,exit};                   	//建立一个数组存放按钮
		for(int i=0;i<=2;i++) {
			but[i].setBounds(300,300+i*90,250,70); 				//设置按钮出现位置及大小
			this.add(but[i]); 									//把按钮添加到窗口中 
			but[i].addActionListener(this); 					//增加监听
		}
		this.add(jlab);											//将背景加入到窗口中				
		this.setVisible(true);									//将窗口显示出来		
	}
	//用来new对象的方法
	public void newObject() {
		startbg = new ImageIcon("image/kaishiyouxibeijing.jpg");
		aboutbg = new ImageIcon("image/guanyuyouxibeijing.jpg");
		exitbg  = new ImageIcon("image/tuichuyouxibeijing.jpg");
		bg1     = new ImageIcon("image/zhujiemianbeijng.jpg");
		start   = new JButton("开始游戏",startbg);		
		about   = new JButton("关于游戏",aboutbg);
		exit    = new JButton("退出游戏",exitbg);
		jlab = new JLabel(bg1);
	}
	//用来对窗口进行一些设置的方法
	public void setFrame() {
		this.setSize(900, 640);									//设置窗口大小
		this.setTitle("Superkun");
		this.setLocationRelativeTo(null);						//设置窗口出现在屏幕中央
		this.setResizable(false);								//设置窗口大小不可改变
		this.setLayout(null);									//取消窗口jfrm的默认布局管理器	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //单击关闭按钮，结束程序
		jlab.setOpaque(true);									//设置背景图片不透明
		jlab.setSize(900, 640);									//设置背景图片大小
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
		else if(btn ==about) {
			//创建一个消息对话框
			JOptionPane.showMessageDialog(this, "键盘方向键←→控制角色移动，↑键跳跃(可踩击敌人),空格发射律师函\n制作人：一组 郝建潮", "关于游戏", JOptionPane.CLOSED_OPTION);     //this指在这个面板当中         后面的JOptionPane.CLOSED_OPTION表示只有关闭这个选项
		}
		else if(btn==exit) { 
			//创建一个确认对话框
			int n=JOptionPane.showConfirmDialog(this,"你快乐了吗？","Superkun",JOptionPane.YES_NO_OPTION);  
			//如果按下yes按钮出现的操作
			if(n==JOptionPane.YES_OPTION) {
				this.dispose();                         //关闭这个界面
			}		
		}
	}
}