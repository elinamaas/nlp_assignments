package task1;
import java.util.HashMap;

public class Unigram {
	private HashMap<String, Integer> unigramHashMap;

	// constructor
	public Unigram() {
		unigramHashMap = new HashMap<String, Integer>();
	}

	public void add(String unigram) {
		if (containsTok(unigram)) {
			int tempInt = unigramHashMap.get(unigram);
			unigramHashMap.put(unigram, tempInt+1);
		} else {
			unigramHashMap.put(unigram, 1);
		}
	}
	public boolean containsTok (String tok){
		return unigramHashMap.containsKey(tok);
	}
	
	public String printUnigramHashMap() {
		return unigramHashMap.toString();
	}

	public int getFreq(String string) {

		if (unigramHashMap.containsKey(string)) {
			return unigramHashMap.get(string);
		} else {
			return 0;
		}

	}

	public int getUnigramSize() {
		return unigramHashMap.size();
	}

}