package roll;

import java.awt.Frame;

//manager显示结果的线程，调用了不同的动画函数，其余和resultthread类似

public class MResultthread extends Thread{
    int win=-1;
    Rollframe frame;
    Mass m;
    int path[];
    MResultthread(Rollframe frame,Mass m){
    	this.frame=frame;
    	this.m=m;
    	path=new int[15];
    }
    public void run(){
    	while(true){
    		if(win!=-1){//win被更新即调用
    			frame.mresultflash(win,path);//manager端显示的动画
    			win=-1;
    			if(m.tag!=2||m.tag!=0){
    				frame.visiblet1();
    			}
    		}
    		frame.setbeginable();//begin按钮解锁
    	}
    }
    public void begin(int i,int[] p){//用于更新win
    	win=i;
    	path=p;
    }
}
