package roll;

import java.io.Serializable;
import java.sql.Time;
import java.util.*;

import javax.xml.crypto.Data;

//用于保存用户点击button后的信息，包括编号，点击时间等

public class Clickmess implements Serializable{
	public boolean b=false;
    public int cupno=-1;
    public Date time=null;
}
