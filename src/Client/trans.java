//�����߳�֮�䴫�ݵĶ���
//����������Ҫ�Ķ����ڴ�ע�Ტд���캯��

package Client;
import roll.*;

import java.awt.Label;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;

//1��������+IP

public class trans implements Serializable{
	public String name=null;
	public String lable;
	public String context;
	public String IP;
	public Mass mass;
	//��ͬλ�õĵ�Ļ
	public int type;
	public File file;
	public FileInputStream fileinput;

	//�����Ĵ���
	public trans(String lable,String s1,String s2){
		if(lable.equals("nameIP")||lable.equals("managernameIP"))
		{
			System.out.println("trans���ƾͼƽ��ƾͼƽ��ƾͼƽ��ƾͼƽ��ƾͼ�");
			this.lable=lable;
			this.name=s1;
			this.IP=s2;
		}
	}
	
	public trans(String lable,String  s1,String s2,File file)
	{
		if(lable.equals("managernameIP"))
		{
			System.out.println("mad");
			this.lable=lable;
			this.name=s1;
			this.IP=s2;
			this.file=file;
		}
	}
	public FileInputStream getinputstream()
	{
		return fileinput;
	}
	
	public trans(String lable,Mass mass)
	{
		if(lable.equals("Mass"))
		{	this.lable=lable;
			this.mass=mass;
		}
	}
	
	//���롢������ݵĴ���Ŷ
	public trans(String lable,String s1)
	{
		if(lable.equals("message")||lable.equals("error")||lable.equals("goodbye"))
		{
			this.lable=lable;
			this.context=s1;
		}
	}

	public trans(String lable ,String s1,int i)
	{
		if(lable.equals("usermessage"))
		{
			this.lable=lable;
			this.context=s1;
			type=i;
		}
	}

	public trans(String lable)
	{
		if(lable.equals("DanMu")||lable.equals("error")||lable.equals("danmustart")||lable.equals("danmuclose")||lable.equals("close"))
			this.lable=lable;
	}
	public String getcontext(){
		return context;
	}
	
	public String getlable(){
		return lable;
	}
	public String getname(){
		return name;
	}

	public void setname(String name){
		this.name=name;
	}

}
