package task1;

import java.util.HashMap;


public class BGram {
	private HashMap<String, Integer> bGramHashMap;
	
	public BGram (){
		
		bGramHashMap = new HashMap <String, Integer>();
	}
	
	public void add (String previousTok, String currentTok){
		String bgram = previousTok + " " + currentTok;
		int freq = 1;
		if (containsBgram(bgram)){
			freq = getFreq(bgram);
			bGramHashMap.put(bgram, freq+1);
		}else
			bGramHashMap.put(bgram, freq);
	}
	
	public boolean containsBgram (String bgram){
		return bGramHashMap.containsKey(bgram);
				
	}
	
	public Integer getFreq (String bgram){
		if (containsBgram(bgram)){
			int freq = bGramHashMap.get(bgram);
			return freq;
		}else
			return 0;
	}
	
}
