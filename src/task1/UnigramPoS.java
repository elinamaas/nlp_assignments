package task1;

import java.util.HashMap;

public class UnigramPoS {
	
	private HashMap<String, Integer> unigramPoSHashMap;

	
	// constructor
	public UnigramPoS() {
		unigramPoSHashMap = new HashMap<String, Integer>();
	}
	
	public void add(String term, String pos) {
		String unigram = term + " " + pos;
		if (containsUnigram(unigram)){
			int freq = getFreq(unigram);
			unigramPoSHashMap.put(unigram, freq+1);
		}
		else
			unigramPoSHashMap.put(unigram, 1);
	}
	
	public boolean containsUnigram (String unigram){
		return unigramPoSHashMap.containsKey(unigram);
	}
	
	public int getFreq (String unigram){
		if (containsUnigram(unigram))
			return unigramPoSHashMap.get(unigram);
		else
			return 0;
	}

}
