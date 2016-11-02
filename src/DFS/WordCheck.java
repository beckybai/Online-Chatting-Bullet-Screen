package DFS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WordCheck {
	
	Map sensitiveword=null;
	
	
	//初始化
	public WordCheck(){
		sensitiveword=new Wordinit().initKeyWord();
	}
	
	
	public Set<String> getSensitiveWord(String txt ){
		Set<String> sensitiveWordList =new HashSet<String>();
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}
		return sensitiveWordList;
	}
	
	
	
	public String replaceSensitiveWord(String txt,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt);     //获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			String x=(getReplaceChars(word));
			resultTxt = resultTxt.replaceAll(word, x);

		}
		
		return resultTxt;
	}
	
	public String getReplaceChars(String x)
	{
		StringBuffer a=new StringBuffer();
		for(int i=0;i<x.length();i++)
			a.append("*");
		return a.toString();
	}
	
	
	public boolean Boolcheck(String txt)
	{
		boolean test=true;
		Set<String> sensitiveWordList =new HashSet<String>();
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				test=false;   //减1的原因，是因为for会自增
				break;
			}
		}
		
		return test;
		
	}
	
	
	public int CheckSensitiveWord(String txt,int beginIndex){
		boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0;     //匹配标识数默认为0
		char word = 0;
		Map nowMap = sensitiveword;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);     //获取指定key
			if(nowMap != null){     //存在，则判断是否为最后一个
				matchFlag++;     //找到相应key，匹配标识+1 
				if("1".equals(nowMap.get("end"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true;       //结束标志位为true   
					break;
				}
			}
			else
			{     
				//不存在，直接返回
				break;
			}
		}
		if(!flag){        //长度必须大于等于1，为词 
			matchFlag = 0;
		}
		return matchFlag;
	}
	
}
