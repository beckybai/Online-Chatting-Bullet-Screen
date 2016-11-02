/*
 * 白可---服务器程序----
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
	//相当于单开的线程，与每个进入的thread通讯。
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
				serverSocket=new ServerSocket(12346);//默认线程12346
				InetAddress addr = InetAddress.getLocalHost();
				String ip=addr.getHostAddress().toString();
				System.out.println(ip);
				runing=false;
			}
			else
			{
				JOptionPane.showConfirmDialog(null, "此端口已经连接");
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		StartService();
	}

	//启动服务器
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
				System.out.println("severSocket启动出现问题");
				e.printStackTrace();
			}
		}
	}


	//用户线程
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
					//发送者的姓名ip
					if(tr.lable.equals("nameIP"))
					{

//						System.out.println("USER'S");
						users.add(this);
						consumer=new People(tr.name,tr.IP);
						label="user";
					}
					//管理者姓名ip
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
//							System.out.println("只能有一个manager！");
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

		//不断接收各线程来的讯号。
		public void run()
		{
			String s=this.getlabel();
//			System.out.println("ninini"+s);

			//用户线程，判断是哪种类型的线程
			if(s.equals("user"))
				while(!isInterrupted)
				{
					try {
						trans tr=(trans)read.readObject();
//						System.out.println("成功接收");
						//来自用户的消息
						if(tr.getlable().equals("usermessage"))
						{
							//服务器自动审查
							if(checker.Boolcheck(tr.getcontext()))
							{
								tr.setname(getname());
								//								System.out.println("center-run-我要传递信息了你们看好啊！");
								//管理人员还未上线
								if(manager==null)
								{
									trans trr=new trans("error","管理人员还没有上线，活动未开始，请等候");
									this.getWriter().writeObject(trr);
								}
								//向审核人员发送消息
								else
								{
									manager.getWriter().writeObject(tr);
									manager.getWriter().flush();
								}
							}
							//自动审核未通过
							else {
								trans trr=new trans("error","您的发言包含敏感词汇，请注意用词");
								this.getWriter().writeObject(trr);
							}
						}

						//表示该用户已经下线，关闭该用户线程
						else if (tr.lable.equals("close"))
						{
							read.close();
							write.close();
							socket.close();
							for (int i = users.size() - 1; i >= 0; i--) {  
								if (users.get(i).getname().equals(getname())) {  
									userThread temp = users.get(i);  
									users.remove(i);// 删除此用户的服务线程  
									temp.interrupt();// 停止这条服务线程    
								}
							}

//							System.out.println("goodbyecenter");
							for (int i = users.size() - 1; i >= 0; i--) {  
								trans tr3=new trans("goodbye",getname()+"离开了");
								users.get(i).getWriter().writeObject(tr3);  
								users.get(i).getWriter().flush();  
							}

						}
						//启动游戏窗口
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

			//如果是从manager处传来的消息
			else if(s.equals("manager"))
				while(!isInterrupted)
				{
					//					System.out.println("回送");
					trans tr;
					try {
						tr = (trans)read.readObject();
						//						System.out.println("fu"+tr.lable);
						//						System.out.println(tr.name);
						//如果是消息，则传递消息
						if(tr.lable.equals("message"))
						{
							for(int i=0;i<users.size();i++)
							{
//								System.out.println(users.get(i).getname());
								users.get(i).getWriter().writeObject(tr);
								users.get(i).getWriter().flush();
							}
							//若包含弹幕信息，则向弹幕端发送消息
							if(hasdanmu)
							{
//								System.out.println(tr.type);
								danmur.getWriter().writeObject(tr);
							}
						}

						//敏感词汇过滤，出现异常
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

						//游戏信息传递
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

						//管理人员下线
						else if (tr.lable.equals("close"))
						{
							read.close();
							write.close();
							socket.close(); 
							manager.interrupt();// 停止这条服务线程   
							manager=null;
//							System.out.println("goodbyecenter");
							for (int i = users.size() - 1; i >= 0; i--) {  
								trans tr3=new trans("goodbye","管理员掉线了");
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

			//弹幕线程
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
							this.interrupt();// 停止这条服务线程    
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
