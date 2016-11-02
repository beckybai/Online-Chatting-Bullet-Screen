//用于线程之间传递的对象
//对于任意需要的对象，在此注册并写构造函数

package Client;
import roll.*;

import java.awt.Label;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;

//1代表姓名+IP

public class trans implements Serializable{
	public String name=null;
	public String lable;
	public String context;
	public String IP;
	public Mass mass;
	//不同位置的弹幕
	public int type;
	public File file;
	public FileInputStream fileinput;

	//姓名的传递
	public trans(String lable,String s1,String s2){
		if(lable.equals("nameIP")||lable.equals("managernameIP"))
		{
			System.out.println("trans将计就计将计就计将计就计将计就计将计就计");
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
	
	//输入、输出内容的传递哦
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
