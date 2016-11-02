//用于服务器管理在线人员
package Client;

public class People {
	
	
	String name;
	String IP;
	public People(String name,String IP){
		this.name=name;
		this.IP=IP;
	}
	
	public String getName(){
		return name;
	}
	public String getIP(){
		return IP;
	}

}
