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
	
	
	//��ʼ��
	public WordCheck(){
		sensitiveword=new Wordinit().initKeyWord();
	}
	
	
	public Set<String> getSensitiveWord(String txt ){
		Set<String> sensitiveWordList =new HashSet<String>();
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i);    //�ж��Ƿ���������ַ�
			if(length > 0){    //����,����list��
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //��1��ԭ������Ϊfor������
			}
		}
		return sensitiveWordList;
	}
	
	
	
	public String replaceSensitiveWord(String txt,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt);     //��ȡ���е����д�
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
			int length = CheckSensitiveWord(txt, i);    //�ж��Ƿ���������ַ�
			if(length > 0){    //����,����list��
				test=false;   //��1��ԭ������Ϊfor������
				break;
			}
		}
		
		return test;
		
	}
	
	
	public int CheckSensitiveWord(String txt,int beginIndex){
		boolean  flag = false;    //���дʽ�����ʶλ���������д�ֻ��1λ�����
		int matchFlag = 0;     //ƥ���ʶ��Ĭ��Ϊ0
		char word = 0;
		Map nowMap = sensitiveword;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);     //��ȡָ��key
			if(nowMap != null){     //���ڣ����ж��Ƿ�Ϊ���һ��
				matchFlag++;     //�ҵ���Ӧkey��ƥ���ʶ+1 
				if("1".equals(nowMap.get("end"))){       //���Ϊ���һ��ƥ�����,����ѭ��������ƥ���ʶ��
					flag = true;       //������־λΪtrue   
					break;
				}
			}
			else
			{     
				//�����ڣ�ֱ�ӷ���
				break;
			}
		}
		if(!flag){        //���ȱ�����ڵ���1��Ϊ�� 
			matchFlag = 0;
		}
		return matchFlag;
	}
	
}
