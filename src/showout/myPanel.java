package showout;

/**
 *
 * @author fei
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class myPanel extends JPanel implements Runnable{
    public gundongProcesser s1;//閹恒儱褰堝姘З瀵懓绠�   
    public gundongProcesser s2;//閸屻劌鐡ㄥ锝呮躬閺勫墽銇氶惃鍕泊閸斻劌鑴婇獮锟�  
    public zhidingProcesser s3;//閹恒儱褰堢純顕��瀵懓绠�   
    public zhidingProcesser s4;//閸屻劌鐡ㄥ锝呮躬閺勫墽銇氶惃鍕枂妞よ泛鑴婇獮锟�   
    private int[] availableline1;//鐞涖劎銇氬В蹇庣鐞涘本妲搁崥锕�讲娴犮儲鍧婇崝鐘虫煀閻ㄥ嫭绮撮崝銊ヨ剨楠烇拷    
    private int[] availableline2;//鐞涖劎銇氬В蹇庣鐞涘本妲搁崥锕�讲娴犮儲鍧婇崝鐘虫煀閻ㄥ嫮鐤嗘い璺鸿剨楠烇拷    
    private int width;//鐏炲繐绠峰▽鍧楁毐閺傜懓鎮滈惃鍕剼缁辩姵鏆�   
    private int height;//鐏炲繐绠峰▽鍨啍閺傜懓鎮滈惃鍕剼缁辩姵鏆�    
    private Color c;//瑜版挸澧犻懗灞炬珯閼癸拷   
    private static Font font=new Font(Font.MONOSPACED,Font.BOLD,55);//瀵懓绠烽弰鍓с仛閻€劎娈戠�妞剧秼
    private static int LINEHEIGHT=50;//濮ｅ繋绔寸悰宀�畱閸嶅繒绀岀�锟� 
    private int LINE;//閹槒顢戦弫锟�  
    private Random ran;//闂呭繑婧�弫鎵晸閹存劕娅�  
    private static int index=700;//濠婃艾濮╁鐟扮娑撱倓閲滈崥宀冾攽閻ㄥ嫬鐡х粭锔胯閻ㄥ嫰妫块梾锟� 
    private int Position;//缂冾噣銆婂鐟扮閻ㄥ嫭妯夌粈杞扮秴缂冿拷  
    //
    public myPanel(gundongProcesser string1,zhidingProcesser string2){
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        width=(int)screensize.getWidth();
        height=(int)screensize.getHeight();
        ran=new Random();
        c=this.getBackground();
        s1=string1;
        s2=new gundongProcesser();
        s3=string2;
        s4=new zhidingProcesser();
        LINE=height/LINEHEIGHT;
        Position=width/2-50;
        availableline1=new int[LINE];
        availableline2=new int[LINE];
        for(int i=0;i<LINE;i++){
            availableline1[i]=0;
            availableline2[i]=0;
        }
        this.setBounds(0,0,width,height);
        this.setLayout(null);
        new Thread(this).start();
    }
    public int available1(){
        for(int i=0;i<LINE;i++){
            if(availableline1[i]==0){
            	availableline1[i]=1;
                return i;
            }
        }
        
        return -1;
    }
    public int available2(){
        for(int i=0;i<LINE;i++){
            if(availableline2[i]==0){
            	availableline2[i]=1;
                return i;
            }
        }
        
        return -1;
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D com2D = (Graphics2D) g;   
        com2D.setColor(getBackground());
        com2D.fillRect(0, 0, getSize().width, getSize().height);
        com2D.setFont(font);
        com2D.setColor(Color.BLACK);
        int size1=s2.size();
        //濠婃艾濮╁鐟扮閻ㄥ嫭妯夌粈锟�      
        for(int i=0;i<size1;i++){
            s2.get(i).position=s2.get(i).position-s2.get(i).speed;
            com2D.drawString(s2.get(i).content,s2.get(i).position,45+s2.get(i).line*LINEHEIGHT);
            availableline1[s2.get(i).line]=1;
            if(s2.get(i).position<=-s2.get(i).length){
                availableline1[s2.get(i).line]=0;
                s2.remove(i);
                i--;
                size1--;
                continue;
            }
            if(width-s2.get(i).position-s2.get(i).length>index){
                availableline1[s2.get(i).line]=0;
            }
        }
        int t=0;
        synchronized(s1){
            t=s1.size();
            for(int i=0;i<s1.size();i++){
                int nextline=available1();
                if(nextline==-1){
                    t=i;
                    break;
                }
                com2D.drawString(s1.get(i).content, width, 45+nextline*LINEHEIGHT);
                s1.get(i).line=nextline;
                s1.get(i).position=width;
                s1.get(i).speed=ran.nextFloat()+3;
                availableline1[nextline]=1;
                s2.add(s1.get(i));
            }
            for(int i=0;i<t;i++){
                s1.remove(0);
            }
        }
        //缂冾噣銆婂鐟扮閻ㄥ嫭妯夌粈锟�   
        int size2=s4.size();
        for(int i=0;i<size2;i++){
            com2D.drawString(s4.get(i).content,s4.get(i).position,45+s4.get(i).line*LINEHEIGHT);
            availableline2[s4.get(i).line]=1;
            if(s4.get(i).time==0){
                availableline2[s4.get(i).line]=0;
                s4.remove(i);
                i--;
                size2--;
                continue;
            }
            s4.get(i).time--;
        }
        synchronized(s3){
            t=s3.size();
            for(int i=0;i<s3.size();i++){
                int nextline=available2();
                if(nextline==-1){
                    t=i;
                }
                s3.get(i).position=(int)(Position-s3.get(i).length/2*27.5);
                com2D.drawString(s3.get(i).content,s3.get(i).position, 45+nextline*LINEHEIGHT);
                s3.get(i).line=nextline;
                availableline2[nextline]=1;
                s4.add(s3.get(i));
            }
            for(int i=0;i<t;i++){
                s3.remove(0);
            }
        }
    }

    public void run(){
        try{
            while(true){
                repaint();
                Thread.sleep(10);
            }
        }
        catch(Exception e){
            System.out.println("閿欒");
        }
    }
}
