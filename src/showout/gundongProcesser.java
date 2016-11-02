/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package showout;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author fei
 */
import java.util.*;
public class gundongProcesser{
    public Vector<gundongdanmu> temp;
    public void add(String s){
        temp.add(new gundongdanmu(s));
    }
    public void add(gundongdanmu s){
        temp.add(s);
    }
    public void remove(int i){
        temp.remove(i);
    }
    public gundongProcesser(){

        temp=new Vector<gundongdanmu>();
    }
	public int size(){
        return temp.size();
    }
    public gundongdanmu get(int i){
        return temp.get(i);
    }
}
