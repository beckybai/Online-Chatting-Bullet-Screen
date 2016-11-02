package roll;

import java.io.ObjectOutputStream;

//在选择状态中用于发送选择结果的线程
//现在没有调用

public class Sendthread extends Thread{
	Clickmess c;
	ObjectOutputStream oos;
    public Sendthread(Clickmess c,ObjectOutputStream oos){
    	this.c=c;
    	this.oos=oos;
    }
    public void run(){
    	while(true){
    		if(c.b==true){
    			Mass mm=new Mass();
    			mm.c=c;
    			try{oos.writeObject(mm);}catch(Exception e){}
    		}
    	}
    }
}
