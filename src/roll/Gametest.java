package roll;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

//gameµÄ²âÊÔº¯Êý


public class Gametest {
    public static void main(String args[]){
    	Mass m=new Mass();
    	try{ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("D:\\game.txt"));
    	Game ga=new Game("name", oos);
    	new Thread(ga).start();  
    	
    	try{Thread.sleep(6000);}catch(Exception e){}
    	ga.m=new Mass(2,0,0);System.out.println("2");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(0,0,0);System.out.println("0");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(0,4,0);System.out.println("0");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(0,8,0);System.out.println("0");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(0,2,0);System.out.println("0");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(0,14,0);System.out.println("0");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(0,13,0);System.out.println("0");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(0,0,0);System.out.println("0");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(3,0,2);System.out.println("3");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(2,0,0);System.out.println("2");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(0,0,0);System.out.println("0");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(3,4,0);System.out.println("0");
    	try{Thread.sleep(1000);}catch(Exception e){}
    	ga.m=new Mass(3,8,0);System.out.println("0");}catch(Exception e){} 
    }
}
