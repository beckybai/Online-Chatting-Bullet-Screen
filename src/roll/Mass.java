package roll;

import java.io.Serializable;

//存储与游戏有关的信息，用于在服务器和用户之间传递，传递时组合在trans类中

public class Mass implements Serializable{
    public int tag=-1;//表示对某一游戏状态的调用
    public int win=-1;//胜利者的编号
    public int chosen=-1;//选择的button编号
    public int[] track=new int[15];//服务器计算的小球路径
    public Clickmess c=new Clickmess();//点击信息
    public Mass(){
    	tag=-1;
    	win=-1;
    	chosen=-1;
    	c=new Clickmess();
    }
    public Mass(int tag,int chosen,int win){
    	this.tag=tag;
    	this.win=win;
    	this.chosen=chosen;
    	Clickmess c=new Clickmess();
    }
}
