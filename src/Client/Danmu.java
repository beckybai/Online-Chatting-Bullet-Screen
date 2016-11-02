/*2015-6-13 �׿� java ����ҵ*/

/*��ʾ��Ļ����������*/

package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import showout.Frame;
import showout.closeButton;
import showout.gundongProcesser;
import showout.zhidingProcesser;
import Client.user.MessageThread;

public class Danmu extends JFrame {

	private JPanel contentPane;
	private JTextField serverid;
	private JTextField port;
	private  boolean isconnect=false;
	private int portnum;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private gundongProcesser s;
    private zhidingProcesser s1;
    private DanmuThread dt;
    private boolean isInterrupted=false;
    private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
			new Danmu();
	}
	public void closes()
	{
		if (isconnect) {  
			trans tr=new trans("danmuclose");
			try {
				output.writeObject(tr);
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} 
		}  
		
	}
	/**
	 * Create the frame.
	 */
	public Danmu() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame=new JFrame();
		frame.addWindowListener(new WindowAdapter() {  
		public void windowClosing(WindowEvent e) {  
			closes();
			System.exit(0);// �˳�����  
		}}
		);
		frame.setVisible(true);
		frame.setSize(414, 158);
		frame.setResizable(false);
		frame.setLocation(200, 300);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(contentPane);
		JLayeredPane layeredPane = new JLayeredPane();
		s=new gundongProcesser();
        s1=new zhidingProcesser();
		
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		JButton btstart = new JButton("\u5F00\u542F");
		btstart.setBounds(151, 61, 89, 23);
		layeredPane.add(btstart);
		
		//������Ļģʽ
		btstart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isconnect==true)
				{
					//s=new gundongProcesser();
		            //s1=new zhidingProcesser();
		            Frame j=new Frame(s,s1,output);
		            j.setVisible(true);
		            trans trr=new trans("danmustart");
		            dt=new DanmuThread(input,output);
		            dt.start();
				try {
					output.writeObject(trr);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				else 
				{
					JOptionPane.showMessageDialog(contentPane, "û�����ӷ�����");
				}
			}
		});
		
//		
		JLabel label = new JLabel("\u5730\u5740");
		label.setBounds(10, 11, 46, 14);
		layeredPane.add(label);
		
		serverid = new JTextField("127.0.0.1");
		serverid.setBounds(37, 8, 86, 20);
		layeredPane.add(serverid);
		serverid.setColumns(10);
		
		JLabel label_1 = new JLabel("\u7AEF\u53E3");
		label_1.setBounds(133, 11, 46, 14);
		layeredPane.add(label_1);
		
		port = new JTextField("12346");
		port.setBounds(169, 8, 86, 20);
		layeredPane.add(port);
		port.setColumns(10);
		
		JButton btconnect = new JButton("\u8FDE\u63A5");
		btconnect.setBounds(274, 7, 89, 23);
		layeredPane.add(btconnect);
		
		//���ӷ�����
		btconnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isconnect) {
					JOptionPane.showMessageDialog(contentPane, "�Ѵ���������״̬����Ҫ�ظ�����!",  
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
						boolean flage=connect (IP,portnum);
						frame.setTitle("��Ļ����");  
						if(!flage)
						{
							throw new Exception("�����������ʧ��!"); 
						}
					}
					catch(Exception e3)
					{
						throw new Exception("��������δ����");
					}
//					System.out.println("3");
					JOptionPane.showMessageDialog(contentPane, "�ɹ�����!");  
				}
				catch(Exception ex)
				{
					System.out.println("hello?");
					JOptionPane.showMessageDialog(contentPane, ex.getMessage(),  
							"����", JOptionPane.ERROR_MESSAGE);  
				}
			}});
	}
	
	//�����������
	public boolean connect(String service,int port )
	{
		try{
			socket =new Socket(service, port);
			output=new ObjectOutputStream(socket.getOutputStream());
			input= new ObjectInputStream(socket.getInputStream());
			trans tr=new trans("DanMu");
			output.writeObject(tr);
//			System.out.println("nihaoaa");
			output.flush();
//			System.out.println("user connect bug");
			isconnect=true;
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(contentPane, "��˿ں�Ϊ��" + port + "    IP��ַΪ��" + socket.getLocalAddress()  
					+ "   �ķ���������ʧ��!" + "\r\n");
			isconnect = false;// δ������  
			return false;  
		}
	}
	
	//��������Ͽ�����
	public synchronized boolean close()
	{
		try {
			input.close();
			output.close();
			socket.close();
			dt.interrupt();
			isconnect = false;  
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	//��Ļ�̣߳����Ͻ�����Ϣ
	public class DanmuThread extends Thread
	{
		public void interrupt(){
			isInterrupted = true;
			super.interrupt();
		}
		ObjectInputStream input;
		ObjectOutputStream output;
		public DanmuThread(ObjectInputStream input,ObjectOutputStream output)
		{
			this.input=input;
		}
		
		public void run()
		{
			while(!isInterrupted())
			{
				trans tr;
				try {
					tr=(trans) input.readObject();
					if(tr.lable.equals("message"))
					{
						System.out.println(tr.type);
						if(tr.type==0)
						{
							synchronized(s){
								s.add(tr.context);
							}
							
						}
						else
							synchronized(s1){
								s1.add(tr.context);
							}
					}
					else if(tr.lable.equals("danmuclose"))
					{
						close();
					}
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
