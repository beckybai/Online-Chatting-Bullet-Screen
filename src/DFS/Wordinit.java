package DFS;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;


public class Wordinit {
		HashSet<String>hs=new HashSet<String>();
		private String ENCODING = "GBK";    //字符编码
		@SuppressWarnings("rawtypes")
		public HashMap hm;
		private HashSet<String> hset;
		public Wordinit() {
			// TODO Auto-generated constructor stub
		}
		
		
		public Map initKeyWord(){
			try {
				//读取敏感词库
				readwfile();
				//将敏感词库加入到HashMap中
				addtomap(hset);
				//spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("233");
			return (Map) hm;
		}
		
	public void readwfile()
	{
		System.out.println("233");
		hset=new HashSet<String>();
		String filepath="D:\\Java过滤\\敏感词库\\敏感词";
		File file=new File(filepath);
		String[] filelist;
		try {
			if(file.isDirectory())
			{
				filelist= file.list();
				for(int i=0;i<filelist.length;i++){
					String path=filepath + "\\" + filelist[i];
					BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(path),ENCODING));
					String line;
					while((line=bf.readLine())!=null)
					{
						System.out.println(line);
						hset.add(line);
					}
					bf.close();
				
				}
}
}	
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void addtomap(HashSet<String> hs)
	{
		Map nowm=null;
		hm=new HashMap(hs.size());
		Iterator<String>ite=hs.iterator();
		while((ite.hasNext()))
		{
			
			nowm=hm;
			String s=ite.next();
			int i;
			for(i=0;i<s.length();i++)
			{
				char word =(char)s.charAt(i);
				Object chara=hm.get(word);
				if(chara!=null)
				{
					nowm=(Map)chara;
				}
				else {
					HashMap<String , String> newma=new HashMap<String,String>();
					newma.put("end", "0");
					nowm.put(word, newma);
					nowm=newma;
				}
				if(i==s.length()-1)
				{
					nowm.put("end", "1");
					
				}

			}

		}

	}
}
