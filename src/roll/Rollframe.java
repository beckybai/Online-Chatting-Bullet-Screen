package roll;
import Client.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.swing.plaf.metal.*;
import javax.swing.*;

public class Rollframe extends JFrame implements Serializable{
	public static int wid=700;//���ڴ�С
	public static int hit=510;
	
	ObjectInputStream ois;
	ObjectOutputStream oos;//user���ṩ�������
	
	JLayeredPane layeredPane;//���ɶ��jpanel
    Container container;
    JPanel penelcontent1;
    JPanel penelcontent2;
    JPanel penelcontent3;
    JPanel penelcontent4;
    
    JButton[] buttons=new JButton[16];//16��button����ѡ��
    public Clickmess c=new Clickmess();
    
    boolean buttonable=false;//���Ե��button���ڡ�ѡ��״̬��Ϊtrue
    int mycho=-1;//�Լ���ѡ����
    public String name="unkown";//�û�����
    int[] path;//�����·��
    boolean beginable=true;//���Ե����ʼ��ť
	
	public Rollframe(ObjectOutputStream oos){
		 path=new int[15];
		 this.oos=oos;
    	 container = getContentPane();  
         layeredPane = new JLayeredPane(); 
     //  container.setLayout(null);
         container.add(layeredPane); //layeredpaneװ��contentpane��
	     layeredPane.setSize(wid,hit); //���ý����С
	  // try{UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");}catch(Exception e){}
//CURSER
	     Image image = getToolkit().getImage("cu.png");
	     Cursor cursor=getToolkit().createCustomCursor(image, new Point(0,0),null);
	     setCursor(cursor);
//BG	     
	     final JPanel panelBg = new JPanel();  //����ͼƬ��
         ImageIcon imageIcon = new ImageIcon("bg1.png");
         final JLabel bg = new JLabel(imageIcon);  
         panelBg.setBounds(0,0,wid+100,hit+100);  
         panelBg.add(bg);  
         layeredPane.add(panelBg, new Integer(0)); //���ֵΪ0
//CONTENTPANEL         
         penelcontent1=new JPanel();//���η���4��panel���ֱ�Ϊ��ť��Բ�㣬button��С���
         penelcontent1.setBounds(50, 400, wid-50, hit-400);
         layeredPane.add(penelcontent1, new Integer(3)); 
         penelcontent1.setOpaque(false);
         
         penelcontent2=new JPanel();
         penelcontent2.setBounds(30,7,wid-50, 400);
         layeredPane.add(penelcontent2, new Integer(1)); 
         penelcontent2.setOpaque(false);
         penelcontent2.setBackground(Color.blue);
         
         penelcontent3=new JPanel();
         penelcontent3.setBounds(50, 0, wid-50, hit);
         layeredPane.add(penelcontent3, new Integer(2)); 
         penelcontent3.setOpaque(false);
         
         penelcontent4=new JPanel();
         penelcontent4.setBounds(50, 0, wid+50, hit+100);
         layeredPane.add(penelcontent4, new Integer(4)); 
         penelcontent4.setOpaque(false);
//BUTTONS
         showbuttons();//��ʾ��ť
//ROLLS
         showrolls();//��ʾľ׮
    }
//BUTTONS-F
	void showbuttons(){
		ImageIcon image = new ImageIcon("bottle0.png");
		ImageIcon image1 = new ImageIcon("bottle1.png");
		ImageIcon image2 = new ImageIcon("bottle2.png");
        penelcontent1.setLayout(new GridLayout(1,16));
		for(int i=0;i<buttons.length;i++){
			buttons[i]=new JButton(image);//���ð�ťͼƬ
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorderPainted(false);
			buttons[i].setRolloverIcon(image1);//��껬����ͼƬ
//			buttons[i].setPressedIcon(image1);
//			buttons[i].setMargin(new Insets(0,0,0,0));
			penelcontent1.add(buttons[i]);
			final int ii=i; 
			buttons[i].addMouseListener(new MouseAdapter() {//���ü�����
				@Override
				public void mouseClicked(MouseEvent arg0) {
		  /*          buttons[ii].setName("thisone");
		        	buttons[ii].setIcon(image2);
		      //      buttons[ii].setBackground(Color.RED);             */
					if(buttonable){//ֻ����2״̬�¿��Ե��
						sendclick(ii);
						mycho=ii;
						buttonable=false;
					}
				}
			});
		}
	}
//ROLLS-F
    void showrolls(){//��ʾľ׮ͼƬ��ʹ��G��layout
    	ImageIcon image = new ImageIcon("roll1.png");
        penelcontent2.setLayout(new GridLayout(15,31));
    	for(int i=0;i<15;i++){
    		for(int j=0;j<31;j++){
    			if(i%2==0){
    				if((j%2!=0)||(j<16-i-1)||(j>16+i+1)){
    					JLabel lb=new JLabel();
    					penelcontent2.add(lb);
    				}
    				else{
    					JLabel lb=new JLabel(image);
    					lb.setBackground(Color.yellow);
    					penelcontent2.add(lb);
    				}
    			}
    			else{
    				if((j%2==0)||(j<16-i-1)||(j>16+i+1)){
    					JLabel lb=new JLabel();
    					penelcontent2.add(lb);
    				}
    				else{
    					JLabel lb=new JLabel(image);
    					
    					lb.setBackground(Color.yellow);
    					penelcontent2.add(lb);
    				}
    			}
    			
    		}
    	}
    }
//SEND CLICK
    void sendclick(int ii){//���͵����Ϣ
    	c.b=true;
    	c.cupno=ii;
    	c.time=new Date();
    	Mass mm=new Mass(0,ii,0);
    	mm.c=c;
    	trans t=new trans("Mass",mm);//�����mass
    	try{oos.writeObject(t);}catch(Exception e){
    		System.out.println("click sending wrong");
    	}
    }
//THREAD1
    public void invisiblet1(){//����ʾwaiting����
    	penelcontent4.setVisible(false);
    	System.out.println("waiting cannot be see");
    }
    public void visiblet1(){//��ʾwaiting����
    	penelcontent4.setVisible(true);
    }
    public void t1flash(){//waiting����
    	int j=0;
    	ImageIcon[] icons=new ImageIcon[12];
    	for(int i=0;i<12;i++){
    		icons[i]=new ImageIcon("icons"+i+".png");
    	}
    	JLabel jl=new JLabel();    	
    	jl.setPreferredSize(penelcontent4.getSize());
    	
    	penelcontent4.add(jl,BorderLayout.CENTER);
    	
        while(true){
    		jl.setIcon(icons[j]);
    		j=(j+1)%12;
    		try{Thread.sleep(400);}catch(Exception e){}
    	}
    	
    }
//THREAD3
    public void invisiblet3(){//����ʾ�����
    	penelcontent3.setVisible(false);
    }
    public void visiblet3(){//��ʾ�����
    	penelcontent3.setVisible(true);
    }
    public void resultflash(int win,int[] path){//�������
    	//penelcontent3.add(new JLabel("winner is"+win),BorderLayout.CENTER);
    	 penelcontent3.setBounds(0, 0, wid+50, hit+100);
    	this.path=path;
    	if(win==mycho){//�ж��Ƿ�ʤ��
    		penelcontent3.add(new JLabel(new ImageIcon("winner.png")));
    		try{Thread.sleep(4000);}catch(Exception e){}
    	}
    	else{
    		penelcontent3.add(new JLabel(new ImageIcon("lose.png")));
    		try{Thread.sleep(4000);}catch(Exception e){}
    	}
    	mycho=-1;
    	penelcontent3.setVisible(false);
    }
    public void mresultflash(int win,int[] path){//manager���еĽ������
    	//penelcontent3.add(new JLabel("winner is"+win),BorderLayout.CENTER);
    	 penelcontent3.setBounds(0, 0, 100, 100);
    	this.path=path;
    	penelcontent3.add(new JLabel("bottle "+win+"is winner"));
    	mycho=-1;
    	penelcontent3.setVisible(false);
    }
//THREAD2
    public void setablebutton(){//������button
    	buttonable=true;
    }
    public void setunablebutton(){//��������button
    	for(int i=0;i<buttons.length;i++){
     // buttons[i].setEnabled(false);
    		buttonable=false;
    	}
    }
    public void setchosenbutton(int chosen){//��ʾ��ѡ��ť
  //  	System.out.println("setbutt"+chosen);
    	ImageIcon image2 = new ImageIcon("bottle2.png");
    	ImageIcon image3 = new ImageIcon("bottle3.png");
    	if(chosen==mycho){
    		buttons[chosen].setIcon(image3);
    	}
    	else{
    		buttons[chosen].setIcon(image2);
    	}  	
    }
    public void refreshbutton(){//���°�ť
    	for(int i=0;i<buttons.length;i++){
    		ImageIcon image1 = new ImageIcon("bottle0.png");
    		buttons[i].setIcon(image1);
    	}
    }
//MANAGER BUTTON
    public void managerbutton(){//manager�˵�beginѡ��
    	JPanel penelcontent5=new JPanel();
        penelcontent5.setBounds(50, 100, 100, 100);
        layeredPane.add(penelcontent5, new Integer(5)); 
        penelcontent5.setOpaque(false);
        
        JButton mj=new JButton(new ImageIcon("begin.png"));
        mj.setBackground(null);
        mj.setOpaque(false);
    	mj.setContentAreaFilled(false);
		mj.setBorderPainted(false);
        penelcontent5.add(mj);
        mj.addMouseListener(new MouseAdapter() {//���ü���
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(beginable){//ֻ������������ʱ����
					System.out.println("begin button");
					Mass mm=new Mass(2,0,0);
					try{oos.writeObject(new trans("Mass",mm));}catch(Exception e){
						System.out.println("manager send 2 wrong");
					}
					beginable=false;
					
				}
			}
		});
    }
    public void setbeginable(){//����������
    	beginable=true;
    }
 
}
