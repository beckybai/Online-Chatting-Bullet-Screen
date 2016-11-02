/*
 * �׿�---����������----
 *2015-6-13
 *22:11
 *1.0
 */


package Server;
import roll.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import Client.*;

import javax.swing.JOptionPane;
import javax.xml.stream.events.StartDocument;

import org.omg.CORBA.PRIVATE_MEMBER;

import DFS.*;
public class Center {

	public static void main(String[] args) {
		new Center();
	}

	private int[] track;
	//�൱�ڵ������̣߳���ÿ�������threadͨѶ��
	private ArrayList<userThread> users;
	private userThread manager=null;
	private userThread danmur=null;
	public WordCheck checker;
	ServerSocket serverSocket;
	private boolean hasdanmu;
	int winner;
	private int gamer=0;
	boolean runing=false;
	private File file;

	public Center(){	
		checker=new WordCheck();
		track=new int[15];
		try {
			if(!runing)
			{
				serverSocket=new ServerSocket(12346);//Ĭ���߳�12346
				InetAddress addr = InetAddress.getLocalHost();
				String ip=addr.getHostAddress().toString();
				System.out.println(ip);
				runing=false;
			}
			else
			{
				JOptionPane.showConfirmDialog(null, "�˶˿��Ѿ�����");
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		StartService();
	}

	//����������
	public void StartService(){
		users=new ArrayList<Center.userThread>();
		while(true)
		{
			try {
				Socket socket =serverSocket.accept();
				userThread ut=new userThread(socket);
				String label=ut.getlabel();
//				System.out.println(label);
				ut.start();		
			} catch (Exception e) {
				System.out.println("severSocket������������");
				e.printStackTrace();
			}
		}
	}


	//�û��߳�
	class userThread extends Thread
	{
		private Socket socket;
		private ObjectInputStream read;
		private ObjectOutputStream write;
		private People consumer;
		private String label;
		private boolean isInterrupted=false;

		@Override
		public void interrupt(){
			isInterrupted = true;
			super.interrupt();
		}
		public String getname(){
			return consumer.getName();
		}
		public String getlabel(){
			return label;
		}
		public ObjectInputStream getReader(){
			return read;
		}
		public ObjectOutputStream getWriter(){
			return write;
		}
		public userThread(Socket socket)
		{
			this.socket=socket;
			try {
				read=new ObjectInputStream(socket.getInputStream());
				write=new ObjectOutputStream(socket.getOutputStream());
				try {
					trans tr;
					tr = (trans)(read.readObject());
//					System.out.println("center-userThread");
//					System.out.println(tr.getlable());
					//�����ߵ�����ip
					if(tr.lable.equals("nameIP"))
					{

//						System.out.println("USER'S");
						users.add(this);
						consumer=new People(tr.name,tr.IP);
						label="user";
					}
					//����������ip
					else if(tr.lable.equals("managernameIP"))
					{
//						System.out.println("MANAGEER'S");
						if(manager==null)
						{
							label="manager";
							manager=this;
							file=tr.file;
//							System.out.println("22222222222222222222222222222");
						}
						else {
//							System.out.println("ֻ����һ��manager��");
						}
					}
					else if(tr.lable.equals("DanMu"))
					{
//						System.out.println("DanMu");
						danmur=this;
						label="danmur";
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//���Ͻ��ո��߳�����Ѷ�š�
		public void run()
		{
			String s=this.getlabel();
//			System.out.println("ninini"+s);

			//�û��̣߳��ж����������͵��߳�
			if(s.equals("user"))
				while(!isInterrupted)
				{
					try {
						trans tr=(trans)read.readObject();
//						System.out.println("�ɹ�����");
						//�����û�����Ϣ
						if(tr.getlable().equals("usermessage"))
						{
							//�������Զ����
							if(checker.Boolcheck(tr.getcontext()))
							{
								tr.setname(getname());
								//								System.out.println("center-run-��Ҫ������Ϣ�����ǿ��ð���");
								//������Ա��δ����
								if(manager==null)
								{
									trans trr=new trans("error","������Ա��û�����ߣ��δ��ʼ����Ⱥ�");
									this.getWriter().writeObject(trr);
								}
								//�������Ա������Ϣ
								else
								{
									manager.getWriter().writeObject(tr);
									manager.getWriter().flush();
								}
							}
							//�Զ����δͨ��
							else {
								trans trr=new trans("error","���ķ��԰������дʻ㣬��ע���ô�");
								this.getWriter().writeObject(trr);
							}
						}

						//��ʾ���û��Ѿ����ߣ��رո��û��߳�
						else if (tr.lable.equals("close"))
						{
							read.close();
							write.close();
							socket.close();
							for (int i = users.size() - 1; i >= 0; i--) {  
								if (users.get(i).getname().equals(getname())) {  
									userThread temp = users.get(i);  
									users.remove(i);// ɾ�����û��ķ����߳�  
									temp.interrupt();// ֹͣ���������߳�    
								}
							}

//							System.out.println("goodbyecenter");
							for (int i = users.size() - 1; i >= 0; i--) {  
								trans tr3=new trans("goodbye",getname()+"�뿪��");
								users.get(i).getWriter().writeObject(tr3);  
								users.get(i).getWriter().flush();  
							}

						}
						//������Ϸ����
						else if(tr.lable.equals("Mass"))
						{
							for(int i=0;i<users.size();i++)
							{
								users.get(i).getWriter().writeObject(tr);
								users.get(i).getWriter().flush();
							}
							try {
//								System.out.println("send user mass to manager");
								manager.getWriter().writeObject(tr);

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Mass mass=tr.mass;
							int x=mass.tag;
							switch (x) {
							case 0:
								gamer++;
								break;
							case 2:
								gamer=0;
								class Timee extends Thread{

									public void run()
									{
										try{Thread.sleep(60000);}catch(Exception e){}

										Mass mass=new Mass(3,0,winner);
										mass.track=track;
										trans tr4=new trans("Mass",mass);

										for(int i=0;i<users.size();i++)
										{
											try {
												users.get(i).getWriter().writeObject(tr4);
												users.get(i).getWriter().flush();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
										try {
											manager.getWriter().writeObject(tr4);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}

								new Timee().start();

								Random random=new Random();
								int w=0;
								for(int i=0;i<15;i++)
								{
									track[i]=random.nextInt(1);
									w+=track[i];
								}
								winner=w;
								//while(System.currentTimeMillis()-startMili!=1000*60)

								break;
							case 3:
								break;

							default:
								break;
							}

						}
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			//����Ǵ�manager����������Ϣ
			else if(s.equals("manager"))
				while(!isInterrupted)
				{
					//					System.out.println("����");
					trans tr;
					try {
						tr = (trans)read.readObject();
						//						System.out.println("fu"+tr.lable);
						//						System.out.println(tr.name);
						//�������Ϣ���򴫵���Ϣ
						if(tr.lable.equals("message"))
						{
							for(int i=0;i<users.size();i++)
							{
//								System.out.println(users.get(i).getname());
								users.get(i).getWriter().writeObject(tr);
								users.get(i).getWriter().flush();
							}
							//��������Ļ��Ϣ������Ļ�˷�����Ϣ
							if(hasdanmu)
							{
//								System.out.println(tr.type);
								danmur.getWriter().writeObject(tr);
							}
						}

						//���дʻ���ˣ������쳣
						else if(tr.lable.equals("error"))
						{
							for(int i=0;i<users.size();i++)
							{
								if(users.get(i).getname().equals(tr.name))
								{	
									users.get(i).getWriter().writeObject(tr);
									users.get(i).getWriter().flush();
								}
							}
						}

						//��Ϸ��Ϣ����
						else if(tr.lable.equals("Mass"))
						{
							for(int i=0;i<users.size();i++)
							{
								users.get(i).getWriter().writeObject(tr);
								users.get(i).getWriter().flush();
//								System.out.println("send mass to user"+i);
							}
							try {
	//							System.out.println("send mass to manager");
								manager.getWriter().writeObject(tr);

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Mass mass=tr.mass;
							int x=mass.tag;
							switch (x) {
							case 0:
								gamer++;
								break;
							case 2:
								gamer=0;

								class Timee extends Thread{

									public void run()
									{
										try{Thread.sleep(60000);}catch(Exception e){}

										Mass mass=new Mass(3,0,winner);
										mass.track=track;
										trans tr4=new trans("Mass",mass);

										for(int i=0;i<users.size();i++)
										{
											try {
												users.get(i).getWriter().writeObject(tr4);
												users.get(i).getWriter().flush();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
										try {
											manager.getWriter().writeObject(tr4);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}

								new Timee().start();

								Random random=new Random();
								int w=0;
								for(int i=0;i<15;i++)
								{
									track[i]=random.nextInt(1);
									w+=track[i];
								}
								winner=w;
								//while(System.currentTimeMillis()-startMili!=1000*60)

								break;
							case 3:
								break;

							default:
								break;
							}

						}

						//������Ա����
						else if (tr.lable.equals("close"))
						{
							read.close();
							write.close();
							socket.close(); 
							manager.interrupt();// ֹͣ���������߳�   
							manager=null;
//							System.out.println("goodbyecenter");
							for (int i = users.size() - 1; i >= 0; i--) {  
								trans tr3=new trans("goodbye","����Ա������");
								users.get(i).getWriter().writeObject(tr3);  
								users.get(i).getWriter().flush();  
							}

						}
					} 
					catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			//��Ļ�߳�
			else if(s.equals("danmur"))
			{
				while(!isInterrupted)
				{
					trans tr5;
					try {
						tr5 = (trans) read.readObject();
						if(tr5.lable.equals("danmuclose"))
						{
							write.writeObject(tr5);
							hasdanmu=false;
							read.close();
							write.close();
							socket.close(); 
							this.interrupt();// ֹͣ���������߳�    
						}
						else
						{
							hasdanmu=true;
						}

					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
			}


		}
	}

}
