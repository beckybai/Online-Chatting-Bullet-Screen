//��˶�

package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.IllegalFormatCodePointException;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.lang.model.element.NestingKind;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;

import org.omg.CORBA.PUBLIC_MEMBER;

import DFS.WordCheck;
import roll.Gmanager;
import javax.swing.JScrollPane;

public class Manager extends JFrame {
	private ManagerThread mt;
	private JPanel contentPane;
	private JTextField servicetext;
	private JTextField porttext;
	boolean isconnect=false;
	private Stack<String> news;
	int portnum;
	public Object Stir;
	public 		Socket socket;
	public JTextArea receive;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Gmanager gmanager;
	private Vector<trans> message; 
	private boolean isInterrupted=false;
	private File file;
	private FileInputStream fileinput;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new Manager();
	}



	/**
	 * Create the frame.
	 */
	public Manager() {
		Frame frame=new Frame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(509,362);
		frame.setResizable(false);
		frame.setVisible(true);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.add(contentPane);
		//��ֻ������һ���ܵķ�������
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);

		JLabel label = new JLabel("\u670D\u52A1\u5668");
		label.setBounds(29, 11, 46, 14);
		layeredPane.add(label);

		servicetext = new JTextField("127.0.0.1");
		servicetext.setBounds(73, 8, 86, 20);
		layeredPane.add(servicetext);
		servicetext.setColumns(10);

		JLabel lblNewLabel = new JLabel("\u7AEF\u53E3");
		lblNewLabel.setBounds(169, 11, 46, 14);
		layeredPane.add(lblNewLabel);

		porttext = new JTextField("12346");
		porttext.setBounds(215, 8, 86, 20);
		layeredPane.add(porttext);
		porttext.setColumns(10);

		JButton btconnect = new JButton("\u8FDE\u63A5");
		btconnect.setBounds(340, 7, 89, 23);
		layeredPane.add(btconnect);

		JButton btagree = new JButton("\u786E\u5B9A");
		btagree.setBounds(340, 276, 89, 23);
		layeredPane.add(btagree);

		JButton btdisagree = new JButton("\u62D2\u7EDD");
		btdisagree.setBounds(212, 276, 89, 23);
		layeredPane.add(btdisagree);

		JButton btgame = new JButton("\u6E38\u620F");
		btgame.setBounds(42, 270, 67, 34);
		layeredPane.add(btgame);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 42, 448, 223);
		layeredPane.add(scrollPane);
		
				receive = new JTextArea();
				scrollPane.setViewportView(receive);

		//��ʼ��Ϸ
		btgame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gmanager=new Gmanager("Manager",output);
				new Thread(gmanager).start();
			}
		});

		//���δͨ��
		btdisagree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//�����ʱ��Ļ�ϲ����û��������ݣ����޷����ȷ����ť������Ϣ
					if(receive.getText().trim()!=null&&receive.getText().trim()!=""&&!receive.getText().trim().equals("nobody is speaking"))
						send("error");

				} catch (EmptyStackException ew) {
					// TODO Auto-generated catch block
					receive.setText("��ʱû�е�Ļ������");
				}
			}
		});

		//���ͨ��
		btagree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					//�����ʱ��Ļ�ϲ����û��������ݣ����޷����ȷ����ť������Ϣ
					if(receive.getText().trim()!=null&&receive.getText().trim()!=""&&!receive.getText().trim().equals("nobody is speaking"))
						send("message");
				} catch (EmptyStackException e) {
					receive.setText("��ʱû�е�Ļ������");
				}
			}
		});

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
					String IP=servicetext.getText().trim();
					try{
						portnum =Integer.parseInt(porttext.getText().trim());
					}
					catch (NumberFormatException e2) {  
						throw new Exception("�˿ںŲ�����Ҫ��!�˿�Ϊ����!");  
					}
					boolean flage=true;
					flage=connect (IP,portnum);

					//contentPane.setName("Manager");  
					if(!flage)
					{
						throw new Exception("�����������ʧ��!"); 
					}
//					System.out.println("3");
					JOptionPane.showMessageDialog(contentPane, "�ɹ�����!");  
				}
				catch(Exception ex)
				{
					//					System.out.println("hello?");
					JOptionPane.showMessageDialog(contentPane, ex.getMessage(),  
							"����", JOptionPane.ERROR_MESSAGE);  
				}
			}}
				);

		//�رմ��ڡ��ر��߳�
		frame.addWindowListener(new WindowAdapter() {  
			public void windowClosing(WindowEvent e) {  
				if (isconnect) {  
//					System.out.println("�ػ���");
					closeConnection();// �ر�����  
				}  
				System.exit(0);// �˳�����  
			}  
		});



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

	//�������������Ϣ
	public void send(String label)
	{
		String anews=receive.getText().trim();
		trans ntr;
		String index=null;
		int num=0;
		if(label.equals("error"))
		{
			index="���ķ����а������дʻ㣬��ע���ô�";
			num=message.remove(0).type;
		}
		else if(label.equals("message"))
		{
			index=anews;
			num=message.remove(0).type;
		}
		ntr=new trans(label,index);
		ntr.type=num;
		StringTokenizer x=new StringTokenizer(anews,":");
		//����֪����ʱ���ݵ���˭�ȽϺ���
		ntr.setname(x.nextToken());
//		System.out.println(ntr.name);
//		System.out.println("Manageroutput");
		try {
			output.writeObject(ntr);
			output.flush();
			next();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//��ȡ��һ����Ϣ
	private void next() {
		if(news.size()==0)
		{
			receive.setText("nobody is speaking");
		}
		else 
			receive.setText(news.pop());
	}


	//���������������
	public boolean connect(String IP,int portnum){
		try{
			message=new Vector<trans>();
			socket=new Socket(IP,portnum);
			try {
				output=new ObjectOutputStream(socket.getOutputStream());
				input=new ObjectInputStream(socket.getInputStream());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String name="manager";
			trans mtr=new trans("managernameIP",name,IP);
			output.writeObject(mtr);
			output.flush();

			mt=new ManagerThread(socket);
//			System.out.println("234");
			mt.start();
			isconnect=true;
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(contentPane, "���ӳ���");
			return false;
		}

	}
	
	//�������̣߳����Ͻ�����Ϣ
	class ManagerThread extends Thread
	{
		Socket socket;
		public void interrupt(){
			isInterrupted = true;
			super.interrupt();
		}
		public ManagerThread(Socket socket)
		{
//			System.out.println("3456");
			this.socket=socket;
			try {
				news=new Stack<String>();
				//writer=new ObjectOutputStream(socket.getOutputStream());
				//input=new ObjectInputStream(socket.getInputStream());
//		System.out.println("3456");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run(){
			while(!isInterrupted)
			{
				try {
					//��������ַ���ȫ���ŵ�һ��ջ�У������Ⱥ�˳����д���
					trans tr=(trans) input.readObject();
//					System.out.println("Manager.ManagerThread.run()");
//					System.out.println(tr.getlable());
					if(tr.lable.equals("usermessage"))
					{

//						System.out.println("2333333�յ���Ϣ�ˣ�����");
						String waitstring=tr.getname()+":"+tr.context;
						message.addElement(tr);
//						System.out.println(tr.type);
						if((receive.getText().trim()==null||receive.getText().trim().equals("nobody is speaking")||receive.getText().trim().equals("")) && news.size()==0)
						{
//							System.out.println(waitstring);
							receive.setText(waitstring);
						}
						else
						{
//							System.out.println(waitstring);
							news.push(waitstring);
						}
					}
					else if(tr.lable.equals("Mass"))
					{

						gmanager.m=tr.mass;
					}

				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}