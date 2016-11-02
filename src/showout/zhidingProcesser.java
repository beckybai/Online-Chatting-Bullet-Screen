package showout;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author fei
 */
import java.util.*;

public class zhidingProcesser{
    public Vector<zhidingdanmu> temp;
    public void add(String s){
        temp.add(new zhidingdanmu(s));
    }
    public void add(zhidingdanmu s){
        temp.add(s);
    }
    public void remove(int i){
        temp.remove(i);
    }
    public zhidingProcesser(){

        temp=new Vector<zhidingdanmu>();
    }
    public int size(){
        return temp.size();
    }
    public zhidingdanmu get(int i){
        return temp.get(i);
    }
}
