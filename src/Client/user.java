/*user
 * becky
 * 2015-6-13
 * vesion1.0
 */

package Client;
import roll.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.SaharaSkin;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel;
import org.pushingpixels.substance.api.watermark.SubstanceImageWatermark;
import org.pushingpixels.substance.internal.animation.IconGlowTracker;
import org.w3c.dom.UserDataHandler;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class user extends JFrame {
private JButton btnSend ;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private JPanel contentPane;
	private JTextField serverid;
	private JTextField port;
	private int form;
	private JTextField nametext;
	private JTextArea receive;
	private Boolean isconnect=false;
	private int portnum;
	private JTextArea sendtext;
	final JCheckBox chooseup;
	final JCheckBox choosefloat;
	public String myname;
	private MessageThread mt;
	private Game game;
	private Mass masssend;
	private boolean isgame=false;

	
	
	public static void main(String[] args) 
	{
		new user();
		
	}

	public user() {
		final JFrame frame =new JFrame("��Ļ������");
		frame.setLocation(200, 300);
		frame.setSize(486, 338);
		frame.setResizable(false);
		frame.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.getContentPane().add(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));

		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane);

		JLabel label = new JLabel("\u670D\u52A1\u5668");
		label.setBounds(10, 11, 46, 14);
		layeredPane.add(label);

		serverid = new JTextField("127.0.0.1");
		serverid.setBounds(48, 8, 59, 20);

		layeredPane.add(serverid);
		serverid.setColumns(10);

		JLabel label_1 = new JLabel("\u7AEF\u53E3");
		label_1.setBounds(117, 11, 46, 14);
		layeredPane.add(label_1);

		port = new JTextField("12346");
		port.setBounds(142, 8, 46, 20);
		layeredPane.add(port);
		port.setColumns(10);

		ImageIcon icon=new ImageIcon("2.png");
		JButton connect = new JButton("����");
		connect.setBounds(325, 7, 89, 23);
		layeredPane.add(connect);
		


		chooseup = new JCheckBox("\u7F6E\u9876");
		chooseup.setBounds(393, 201, 89, 23);
		layeredPane.add(chooseup);

		choosefloat = new JCheckBox("\u6D6E\u52A8");
		choosefloat.setBounds(332, 201, 59, 23);
		layeredPane.add(choosefloat);

		JLabel label_2 = new JLabel("\u59D3\u540D");
		label_2.setBounds(195, 11, 28, 14);
		layeredPane.add(label_2);

		nametext = new JTextField("���");
		nametext.setBounds(229, 8, 86, 20);
		layeredPane.add(nametext);
		nametext.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u5927\u5BB6\u7684\u53D1\u8A00", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		scrollPane.setBounds(10, 36, 430, 146);
		layeredPane.add(scrollPane);

		receive = new JTextArea();
		receive.setForeground(Color.DARK_GRAY);
		receive.setLineWrap(true);
		receive.setBounds(10, 36, 430, 146);
		scrollPane.setViewportView(receive);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new TitledBorder(null, "\u7F16\u8F91\u53D1\u9001\u5185\u5BB9", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setToolTipText("\u53D1\u8A00");
		scrollPane_1.setBounds(10, 213, 272, 70);
		layeredPane.add(scrollPane_1);
		
				sendtext = new JTextArea();
				scrollPane_1.setViewportView(sendtext);
				sendtext.setLineWrap(true);
				sendtext.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_ENTER)
						{
							
							btnSend.doClick();
						}
						
					}
					
					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode()==KeyEvent.VK_ENTER)
						{
							btnSend.doClick();
							sendtext.setCaretPosition(sendtext.getDocument().getLength());
						}
						
					}
				});
		JButton btgame = new JButton("\u6E38\u620F");
		btgame.setBounds(348, 265, 89, 23);
		layeredPane.add(btgame);
		
		btnSend = new JButton("send");
		btnSend.setBounds(348, 231, 89, 23);
		layeredPane.add(btnSend);
		
		//��Ϸ������ť
		btgame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				isgame=true;
				game=new Game(nametext.getText().trim(),output);
				new Thread(game).start();
			}
		});
		
		//��Ļѡ��ť
		chooseup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chooseup.isSelected())
				{
					boolean failed=false;
					choosefloat.setSelected(failed);					
				}
			}
		});

		//���ӷ�������ť
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isconnect) {
					JOptionPane.showMessageDialog(frame, "�Ѵ���������״̬����Ҫ�ظ�����!",  
							"����", JOptionPane.ERROR_MESSAGE);  
					return;  
				}  
				try{
					String IP=serverid.getText().trim();
					try{
						portnum =Integer.parseInt(port.getText().trim());
					}
					catch (NumberFormatException e2) {  
						throw new Exception("�˿ںŲ�����Ҫ��!�˿�Ϊ����!");  
					}
					catch (Exception e2) {
						throw new Exception ("wrong!!!");
					}
					try{
						myname =nametext.getText().trim();
						boolean flage=connect (myname,IP,portnum);
						frame.setTitle(myname);  
						if(!flage)
						{
							throw new Exception("�����������ʧ��!"); 
						}
					}
					catch(Exception e3)
					{
						throw new Exception("��������û������");
					}
					System.out.println("3");
					JOptionPane.showMessageDialog(frame, "�ɹ�����!");  
				}
				catch(Exception ex)
				{
					System.out.println("hello?");
					JOptionPane.showMessageDialog(frame, ex.getMessage(),  
							"����", JOptionPane.ERROR_MESSAGE);  
				}
			}});


		//��ĸѡ��ť--����
		choosefloat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(choosefloat.isSelected())
				{
					boolean failed=false;
					chooseup.setSelected(failed);					
				}

			}
		});

		//�رմ��ڰ�ť
		frame.addWindowListener(new WindowAdapter() {  
			public void windowClosing(WindowEvent e) {  
				if (isconnect) {  
					closeConnection();// �ر�����  
				}  
				System.exit(0);// �˳�����  
			}  
		});


		//������Ϣ��ť
		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				send();
			}
		});
		}

	//�������������Ϣ
	public void send(){
		if(!isconnect)
		{
			JOptionPane.showMessageDialog(contentPane, "��û�����ӷ�����");
		}
		String message=sendtext.getText().trim();
		int choose=0;
		// ������з��ã���chooseΪ1��Ĭ�Ϲ���Ϊ0��
		if(chooseup.isSelected())
			choose=1;

		if(message==null || message.equals(""))
		{
			JOptionPane.showMessageDialog(contentPane, "�������ݲ���Ϊ��");
		}
		else
		{
			System.out.println("988");
			trans tr=new trans("usermessage",message,choose);
			try {
				output.writeObject(tr);
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sendtext.setText(null);
	}

	
	//��������Ͽ�����
	public synchronized boolean closeConnection() {  

		try {  
			trans tr=new trans("close");
			output.writeObject(tr);
			mt.interrupt();
			// �ͷ���Դ  
			if (input != null) {  
				input.close();  
			}  
			if (output != null) {  
				output.close();  
			}  
			if (socket != null) {  
				socket.close();  
			}  
			isconnect = false;  
			return true;  
		} catch (IOException e1) {  
			e1.printStackTrace();  
			isconnect = true;  
			return false;  
		}  
	}

	//���������������
	public boolean connect(String name,String service,int port )
	{
		try{
			socket =new Socket(service, port);
			output=new ObjectOutputStream(socket.getOutputStream());
			input= new ObjectInputStream(socket.getInputStream());
			trans tr=new trans("nameIP",name,socket.getLocalAddress().toString());
			output.writeObject(tr);
			//System.out.println("nihaoaa");
			output.flush();
			mt=new MessageThread(input,receive,name);
			mt.start();
			//System.out.println("user connect bug");
			isconnect=true;
			return true;
		}
		catch(Exception e)
		{
			receive.append("��˿ں�Ϊ��" + port + "    IP��ַΪ��" + socket.getLocalAddress()  
					+ "   �ķ���������ʧ��!" + "\r\n");  
			isconnect = false;// δ������  
			return false;  
		} 
	}

	
	//������Ϣ���ܵ��߳�
	class MessageThread extends Thread
	{
		private ObjectInputStream reader;
		private JTextArea textArea;
		private String iname;
		private boolean isInterrupted=false;
		public void interrupt(){
			isInterrupted = true;
			super.interrupt();
		}
		public MessageThread(ObjectInputStream reader,JTextArea textArea,String name)
		{
			this.iname=name;
			this.reader=reader;
			this.textArea=textArea;			
		}
		public void run()
		{
			while(!isInterrupted)
				try {
					trans tr;
					tr=(trans)reader.readObject();
					//������Ϣ��������ʾ
					if(tr.lable.equals("message"))
					{
						//System.out.println("bienaole");
						StringTokenizer st = new StringTokenizer(tr.context, ":");
						String newname=st.nextToken();
						//System.out.println(iname+" "+newname);
						if(newname.equals(iname))
						{
							//System.out.println("tisinghuo");
							tr.context="�ң�"+st.nextToken();
						}
						textArea.append(tr.context+"\r\n");
					}
					//���Բ����Ϲ淶
					else if(tr.lable.equals("error"))
					{
						//System.out.println(tr.context);
						textArea.append(tr.context+"\r\n");
					}
					//���û�����
					else if(tr.lable.equals("goodbye"))
					{
						//System.out.println("goodbyleuser");
						textArea.append(tr.context+"\r\n");
					}
					else if(tr.lable.equals("Mass"))
					{  //System.out.println("get mass");
						if(isgame==true)
							game.m=tr.mass;
					}
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
	