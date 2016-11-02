package roll;

import java.awt.Frame;

//用于显示结果的线程

public class Resultthread extends Thread{
    int win=-1;//胜利者编号
    Rollframe frame;
    Mass m;
    int path[];//小球经过的路径
    Resultthread(Rollframe frame,Mass m){
    	this.frame=frame;
    	this.m=m;
    	path=new int[15];
    }
    public void run(){
    	while(true){
    		if(win!=-1){
    			frame.resultflash(win,path);//播放user见到的结果动画，显示胜出者
    			win=-1;
    			if(m.tag!=2||m.tag!=0){
    				frame.visiblet1();//显示penel1
    			}
    		}
    		frame.setbeginable();
    	}
    }
    public void begin(int i,int[] p){//更新win值
    	win=i;
    	path=p;
    }
}
