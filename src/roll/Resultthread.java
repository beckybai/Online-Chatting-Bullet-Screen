package roll;

import java.awt.Frame;

//������ʾ������߳�

public class Resultthread extends Thread{
    int win=-1;//ʤ���߱��
    Rollframe frame;
    Mass m;
    int path[];//С�򾭹���·��
    Resultthread(Rollframe frame,Mass m){
    	this.frame=frame;
    	this.m=m;
    	path=new int[15];
    }
    public void run(){
    	while(true){
    		if(win!=-1){
    			frame.resultflash(win,path);//����user�����Ľ����������ʾʤ����
    			win=-1;
    			if(m.tag!=2||m.tag!=0){
    				frame.visiblet1();//��ʾpenel1
    			}
    		}
    		frame.setbeginable();
    	}
    }
    public void begin(int i,int[] p){//����winֵ
    	win=i;
    	path=p;
    }
}
