package roll;

import java.io.ObjectOutputStream;

import javax.swing.JFrame;

//Ϊmanager���е���Ϸ������������ʾ���ܺ�game����

public class Gmanager implements Runnable{
	Rollframe frame;
	Clickmess c;
	public Mass m=new Mass();
	int extag=-1;
	int exbutton=-1;
	
	//ObjectInputStream ois;
	ObjectOutputStream oos;
	Waitingthread wt;
	Sendthread st;
	MResultthread rt;//����manager����ʾ������߳�
    public  Gmanager(String name,ObjectOutputStream oos){
    	c=new Clickmess();
    	this.oos=oos;
		frame = new Rollframe(oos);
		frame.name=name;
		frame.setSize(Rollframe.wid+100,Rollframe.hit+100);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	    frame.setVisible(true); 
	    frame.managerbutton();
	    wt=new Waitingthread(frame);
	    st=new Sendthread(c, oos);
	    rt=new MResultthread(frame,m);
	    wt.start();
	//    st.start();
	    rt.start();
    }
    public void run(){
    	while(true){//����tagѡ��
  //  		System.out.println("tag "+extag);
    		if(extag!=m.tag||m.tag==0){
  //  			System.out.println("dif");
    			extag=m.tag;
    			switch(extag){
    			case 2:{
    				System.out.println("dif 2");
    				frame.invisiblet1();
    				frame.invisiblet3();
    				frame.refreshbutton();
    				//frame.setablebutton();
    				break;
    			}
    			case 3:{
    				System.out.println("dif 3");
    				frame.setunablebutton();
    				frame.invisiblet1();
    				frame.visiblet3();
    		//		frame.setbeginable();
    				rt.begin(m.win,m.track);//���е���mresultthread
    				break;
    			}
    			case 0:{
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
