package monash.edu.hally.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import monash.edu.hally.global.ModelVariables;
import monash.edu.hally.nlp.FilesUtil;

public class Document {
	
	public int docWords[];
	public Map<Integer, Integer> indexToCountMap=new HashMap<Integer, Integer>();
	private String tweetData;
	
	private String user;
	private String time;
	private String sentiment;
	
	public int userIndex;
	public int timeIndex;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	
	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public Document(String tweetData)
	{
		this.tweetData = tweetData;
	}
	
	/**
	 * ���ã����浱ǰ�ĵ���ʱ����û���Ϣ
	 */
	private void indexUserAndTime(ArrayList<String> tokens)
	{
		setSentiment(tokens.get(0));
		setTime(tokens.get(1));
		setUser(tokens.get(2));
		if(!ModelVariables.g_userToIndexMap.containsKey(getUser())){
			int index=ModelVariables.g_usersSet.size();
			ModelVariables.g_userToIndexMap.put(getUser(), index);
			userIndex=index;
			ModelVariables.g_usersSet.add(getUser());
			ModelVariables.g_userToCountMap.put(getUser(),1);
		}
		else{
			int count = ModelVariables.g_userToCountMap.get(getUser());
			ModelVariables.g_userToCountMap.put(getUser(), ++count);
			userIndex = ModelVariables.g_userToIndexMap.get(getUser());
		}
		if(!ModelVariables.g_timeToIndexMap.containsKey(getTime())){
			int index=ModelVariables.g_timesSet.size();
			ModelVariables.g_timeToIndexMap.put(getTime(), index);
			timeIndex=index;
			ModelVariables.g_timesSet.add(getTime());
		}
		else
			timeIndex = ModelVariables.g_timeToIndexMap.get(getTime());
		
	}
	
	/**
	 * ���ã�ͳ��token�������ĵ��еĴ���
	 */
	private void wordCount(String token)
	{
		int gIndex=ModelVariables.g_termToIndexMap.get(token);
		if(!indexToCountMap.containsKey(gIndex))
			indexToCountMap.put(gIndex, 1);
		else{
			int count=indexToCountMap.get(gIndex);
			indexToCountMap.put(gIndex, ++count);
		}
	}
			
	
	/**
	 * ���ã����ĵ��еĴʻ���ӵ��ֵ��С�
	 * �ĵ��е�ÿһ���ʻ�����n����docWords[n]����ʾ���ֵ��ж�Ӧ�Ĵ�������
	 */
	public void indexDocument()
	{
		ArrayList<String> tokens = FilesUtil.tokenize(tweetData);
		docWords=new int[tokens.size()-3]; //exclude time and user
		indexUserAndTime(tokens);
		
		for (int n = 3; n < tokens.size(); n++) {
			String token=tokens.get(n);
			if(!ModelVariables.g_termToIndexMap.keySet().contains(token))// dictionary.contains(token))
			{
				int dictionarySize=ModelVariables.g_termDictionary.size();
				ModelVariables.g_termToIndexMap.put(token, dictionarySize);
				docWords[n-3]=dictionarySize;
				ModelVariables.g_termDictionary.add(token);
			}
			else
				docWords[n-3]=ModelVariables.g_termToIndexMap.get(token);
			wordCount(token);
		}
	}

	public static void main(String[] args) {
		

	}

}
