package roll;

//显示等待画面的线程

public class Waitingthread extends Thread{
	Rollframe frame;
    public Waitingthread(Rollframe frame){
    	this.frame=frame;
    }
    public void run(){
    	frame.t1flash();//播放等待画面的动画
    }
}
