package task1;

import java.util.HashMap;

public class BGramPoS {
	
	private HashMap<String, Integer> bGramPoSHashMap;
	
	public BGramPoS() {
		bGramPoSHashMap = new HashMap<String, Integer>();
	}
	
	public void add(String previousPos, String currentPos) {
		String bigram = previousPos + " " + currentPos; 
		if (containsBigram(bigram)){
			int freq = getFreq(bigram);
			bGramPoSHashMap.put(bigram, freq+1);
		}else
			bGramPoSHashMap.put(bigram, 1);
	}
	
	public boolean containsBigram (String bgram){
		return bGramPoSHashMap.containsKey(bgram);
	}
	
	public int getFreq (String bgram){
		if (containsBigram(bgram))
			return bGramPoSHashMap.get(bgram);
		else
			return 0;
	}

}
