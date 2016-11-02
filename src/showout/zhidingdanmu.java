/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package showout;

/**
 *
 * @author fei
 */
public class zhidingdanmu extends danmu{
    public static int TIME=300;
    public zhidingdanmu(String s){
        super(s);
        time=TIME;
        position=0;
    }
    public int position;
    public int time;
}