package roll;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

//user���еĳ齱����ͨ��user����������Ϊһ���������߳�

public class Game implements Runnable{
	Rollframe frame;//����
	Clickmess c;//�洢�����Ϣ
	public Mass m=new Mass();
	int extag=-1;//��һ��mass�е�tag
	int exbutton=-1;//��һ����ť�ı��
	T3finished t3finished;
	
	//ObjectInputStream ois;
	ObjectOutputStream oos;
	Waitingthread wt;//��������״̬���߳�
	Sendthread st;
	Resultthread rt;
    public Game(String name,ObjectOutputStream oos){//���캯����name�ɵ����ߣ�user������
    	c=new Clickmess();
    	this.oos=oos;
    	t3finished=new T3finished();
		frame = new Rollframe(oos);
		frame.name=name;
		frame.setSize(Rollframe.wid+100,Rollframe.hit+100);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	    frame.setVisible(true); 
	    wt=new Waitingthread(frame);//���������߳�
	    st=new Sendthread(c, oos);
	    rt=new Resultthread(frame,m);
	    wt.start();
	//    st.start();
	    rt.start();
    }
    public void run(){
    	while(true){
  //  		System.out.println("tag "+extag);
    		if(extag!=m.tag||m.tag==0){//tag���¿�ʼִ��
  //  			System.out.println("dif");
    			extag=m.tag;
    			switch(extag){
    			case 2:{//tag��ʾ��ʼ��Ϸ����manager�����ź�
    				System.out.println("dif 2");
    				frame.invisiblet1();
    				frame.invisiblet3();
    				frame.refreshbutton();
    				frame.setablebutton();//��ʼ��Ϸ�Ժ�button�ſ���ʹ��
    				break;
    			}
    			case 3:{//tag3Ϊ������Ϸ���ɷ�������ʱ�������źŸ�manager��user
    				System.out.println("dif 3");
    				frame.setunablebutton();
    				frame.invisiblet1();
    				frame.visiblet3();
    				rt.begin(m.win,m.track);
    				break;
    			}
    			case 0:{//tag0Ϊuserѡ���button�����Ϣ
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
