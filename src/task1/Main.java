package task1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
	
	public static String folderName = "/Users/snownettle/Documents/HPI/4.Semester/NLP/assigment/GENIA_treebank_v1";
	public static int min =0;
	
	public static void main(String[] args) {
		int max = Split.numberOfFiles (folderName)-1;
		Set<Integer> train = Split.randomizedTrain (max, min);
		Set<Integer> test = Split.randomizedTest (train,max);
				
		ArrayList<File> trainDataFiles = Split.splitData(folderName, train);
		ArrayList<File> testDataFiles = Split.splitData(folderName, test);

		
		System.out.println("Splitting is done");
		System.out.println("Calculation of bigrams and unigrams");
		
		Unigram unigramTok = new Unigram();
		BGram bgramTok = new BGram();
		HashMap <String, Integer> posList = new HashMap <String, Integer>();
		UnigramPoS unigramPos = new UnigramPoS();
		BGramPoS bgramPos = new BGramPoS();
				
		Parser.parseTrainXML(folderName, trainDataFiles, unigramTok, bgramTok, unigramPos, bgramPos, posList);
		
		Perplexity.parseTestData(folderName, testDataFiles, unigramTok, bgramTok);
		
		Precision.parseTestData (folderName, testDataFiles,unigramPos,bgramPos, posList);
	}

}
