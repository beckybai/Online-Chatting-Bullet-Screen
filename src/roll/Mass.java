package roll;

import java.io.Serializable;

//�洢����Ϸ�йص���Ϣ�������ڷ��������û�֮�䴫�ݣ�����ʱ�����trans����

public class Mass implements Serializable{
    public int tag=-1;//��ʾ��ĳһ��Ϸ״̬�ĵ���
    public int win=-1;//ʤ���ߵı��
    public int chosen=-1;//ѡ���button���
    public int[] track=new int[15];//�����������С��·��
    public Clickmess c=new Clickmess();//�����Ϣ
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
