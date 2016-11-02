package showout;

/**
 *
 * @author fei
 */
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.BreakIterator;

import Client.trans;

import com.sun.awt.*;
import com.sun.org.apache.xml.internal.utils.StopParseException;

public class Frame extends JFrame{
	//閻€劋绨径鍕倞閹锋牕濮╂禍瀣╂閿涘矁銆冪粈娲炊閺嶅洦瀵滄稉瀣閻ㄥ嫬娼楅弽鍥风礉閻╃顕禍宥獸rame
	int xOld = 0;
	int yOld = 0;
	public  ObjectOutputStream output;
	
	public Frame(gundongProcesser s1,zhidingProcesser s2,ObjectOutputStream output) {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() { 
			public void windowClosing(WindowEvent e) {  
				System.out.println("2222222222222222");
			}  
		});
		this.output=output;
		this.setLayout(null);
		//婢跺嫮鎮婇幏鏍уЗ娴滃娆�       
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOld = e.getX();
				yOld = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;
				Frame.this.setLocation(xx, yy);
			}
		});
		//閼惧嘲绶辩仦蹇撶婢堆冪毈
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		int width=(int)screensize.getWidth();
		int height=(int)screensize.getHeight();
		//JLayeredPane閻€劋绨ǎ璇插娑撱倓閲滈崶鎯х湴閻ㄥ嫸绱濇稉锟介嚋閻€劋绨鐟扮閺勫墽銇氶敍灞肩娑擃亞鏁ゆ禍搴″彠闂傤厽瀵滈柦锟�       
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, width, height);
		this.add(layeredPane);

		//閸忔娊妫撮幐澶愭尦閻ㄥ嫪绔寸仦鍌︾礉閺堬拷绗傜仦鍌滄畱layer
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 0, width, height);
		buttonPanel.setLayout(null);
		buttonPanel.setOpaque(false);
		layeredPane.add(buttonPanel,JLayeredPane.PALETTE_LAYER);
		//閸忔娊妫撮幐澶愭尦
		closeButton close=new closeButton(output);
		close.setBounds(width-40, 10, 30, 30);
		close.setBorderPainted(false);
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		buttonPanel.add(close);

		//濞ｈ濮炲鐟扮鐏烇拷     
		myPanel mainPanel=new myPanel(s1,s2);
		layeredPane.add(mainPanel,JLayeredPane.DEFAULT_LAYER);

		//鐎电rame閻ㄥ嫯顔曠純锟�       
		this.setBounds(0,0,width,height);
		this.setUndecorated(true);
		AWTUtilities.setWindowOpacity(this, 0.3f);
		this.setAlwaysOnTop(true);
	}
}
