package roll;

import java.io.ObjectOutputStream;

//��ѡ��״̬�����ڷ���ѡ�������߳�
//����û�е���

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
