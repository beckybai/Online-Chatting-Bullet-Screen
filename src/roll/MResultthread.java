package roll;

import java.awt.Frame;

//manager��ʾ������̣߳������˲�ͬ�Ķ��������������resultthread����

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
    		if(win!=-1){//win�����¼�����
    			frame.mresultflash(win,path);//manager����ʾ�Ķ���
    			win=-1;
    			if(m.tag!=2||m.tag!=0){
    				frame.visiblet1();
    			}
    		}
    		frame.setbeginable();//begin��ť����
    	}
    }
    public void begin(int i,int[] p){//���ڸ���win
    	win=i;
    	path=p;
    }
}
