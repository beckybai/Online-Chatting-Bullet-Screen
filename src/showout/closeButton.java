/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package showout;

/**
 *
 * @author fei
 */
import javax.swing.*;

import sun.java2d.Disposer;
import Client.trans;

import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
public class closeButton extends JButton{
	ObjectOutputStream output;
	public void stop(){
		trans tr=new trans("danmuclose");
		try {
			output.writeObject(tr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public closeButton(ObjectOutputStream output){
    	this.output=output;
        this.setBackground(Color.black);
        //this.setIcon(new ImageIcon("F://java/WebApplication1/src/java/closeButton.png"));
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {          
            	stop();
            }
        });
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e){
                closeButton.this.setOpaque(true);
                closeButton.this.setContentAreaFilled(true);
            }
            @Override
            public void mouseExited(MouseEvent e){
                closeButton.this.setOpaque(false);
                closeButton.this.setContentAreaFilled(false);
            }
        });
        this.setOpaque(false);
        this.setContentAreaFilled(false);
    }
    /*
    @Override
    public void paint(Graphics g){
        setBackground(Color.black);
        g.drawString("close",0,0);
    }*/
}
