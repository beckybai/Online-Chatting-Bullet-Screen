package roll;

//��ʾ�ȴ�������߳�

public class Waitingthread extends Thread{
	Rollframe frame;
    public Waitingthread(Rollframe frame){
    	this.frame=frame;
    }
    public void run(){
    	frame.t1flash();//���ŵȴ�����Ķ���
    }
}
