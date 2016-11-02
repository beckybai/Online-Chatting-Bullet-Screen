package roll;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

//user运行的抽奖程序，通过user程序启动，为一个独立的线程

public class Game implements Runnable{
	Rollframe frame;//窗口
	Clickmess c;//存储点击信息
	public Mass m=new Mass();
	int extag=-1;//上一个mass中的tag
	int exbutton=-1;//上一个按钮的编号
	T3finished t3finished;
	
	//ObjectInputStream ois;
	ObjectOutputStream oos;
	Waitingthread wt;//以下三个状态的线程
	Sendthread st;
	Resultthread rt;
    public Game(String name,ObjectOutputStream oos){//构造函数，name由调用者（user）传入
    	c=new Clickmess();
    	this.oos=oos;
    	t3finished=new T3finished();
		frame = new Rollframe(oos);
		frame.name=name;
		frame.setSize(Rollframe.wid+100,Rollframe.hit+100);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	    frame.setVisible(true); 
	    wt=new Waitingthread(frame);//启动三个线程
	    st=new Sendthread(c, oos);
	    rt=new Resultthread(frame,m);
	    wt.start();
	//    st.start();
	    rt.start();
    }
    public void run(){
    	while(true){
  //  		System.out.println("tag "+extag);
    		if(extag!=m.tag||m.tag==0){//tag更新开始执行
  //  			System.out.println("dif");
    			extag=m.tag;
    			switch(extag){
    			case 2:{//tag表示开始游戏，由manager发出信号
    				System.out.println("dif 2");
    				frame.invisiblet1();
    				frame.invisiblet3();
    				frame.refreshbutton();
    				frame.setablebutton();//开始游戏以后button才可以使用
    				break;
    			}
    			case 3:{//tag3为结束游戏，由服务器计时并发出信号给manager与user
    				System.out.println("dif 3");
    				frame.setunablebutton();
    				frame.invisiblet1();
    				frame.visiblet3();
    				rt.begin(m.win,m.track);
    				break;
    			}
    			case 0:{//tag0为user选择的button标号信息
    	//			System.out.println("dif 0");
    				if(m.chosen!=exbutton){
    					exbutton=m.chosen;
    					frame.setchosenbutton(exbutton);
    					break;
    				}   				
    			}
    			}
    		}
    	}
    }
}
