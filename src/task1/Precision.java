package task1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Precision {

	public static double calculatePrecision(double truePositive, double falsePositive) {
		double precision = truePositive / (truePositive + falsePositive);
		return precision;
	}

	public static String calculateProbability(HashMap<String, Integer> posListOriginal,
			String previousPoS, String term, UnigramPoS unigramPoSList,
			BGramPoS bgramPoSList) {

		HashMap<String, Integer> posListCopy = new HashMap<String, Integer> ();
		posListCopy=(HashMap<String, Integer>) posListOriginal.clone();
		double currentUnigramProb = 0;
		double currentBigramProb = 0;
		double currentProb = 0;
		double previousProb = 0;
		String predictedPos = "";
		
		double denom = posListCopy.get(previousPoS) + posListCopy.size();
		
		//copy list maybe?
				
		Iterator<Entry<String, Integer>> it = posListCopy.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			String unigram = term + " " + pairs.getKey();
			String bigram = previousPoS + " " + pairs.getKey();
			if (unigramPoSList.containsUnigram(unigram))
				currentUnigramProb = unigramPoSList.getFreq(unigram)+1;
			else
				currentUnigramProb = 1;
			if (bgramPoSList.containsBigram(bigram))
				currentBigramProb = bgramPoSList.getFreq(bigram) +1;
			else
				currentBigramProb = 1;
			int uniSize = posListCopy.get(pairs.getKey()) + posListCopy.size();
			
			double unigramProbability = currentUnigramProb / uniSize;
			double bigramProbability = currentBigramProb / denom;
			currentProb = unigramProbability * bigramProbability;
			if (currentProb > previousProb){
				
				predictedPos = (String) pairs.getKey();
				previousProb = currentProb;
			}

			it.remove();
		}
		
		return predictedPos;
	}

	public static void parseTestData(String folderName,
			ArrayList<File> fileList, UnigramPoS unigramPosList,
			BGramPoS bgramPosList, HashMap<String, Integer> posList) {

		String prevTerm = "";
		try {
			double truePositive = 0;
			double falsePositive = 0;
			for (File file : fileList) {

				File fXmlFile = new File(folderName + "/" + file.getName());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);

				doc.getDocumentElement().normalize();
				NodeList sentenseList = doc.getElementsByTagName("sentence");

				
				
				String previousPoS = "<S>";
				String currentElement = "";
				String currentPoS = "";
				for (int i = 0; i < sentenseList.getLength(); i++) {
					previousPoS = "<S>";
					Node nNode = sentenseList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) nNode;
						NodeList tList = element.getElementsByTagName("tok");

						for (int j = 0; j < tList.getLength() - 1; j++) {
							Node tokNode = tList.item(j);
							if (tokNode.getNodeType() == Node.ELEMENT_NODE) {
								Element tokElement = (Element) tokNode;
								currentPoS = tokElement.getAttribute("cat");
								currentElement = tokElement.getTextContent();

								String predictedPoS = calculateProbability(posList, previousPoS, currentElement,
										unigramPosList, bgramPosList);
								
								if (currentPoS.equals(predictedPoS))
									truePositive++;
								else
									falsePositive++;

								previousPoS = predictedPoS;
								prevTerm = currentElement;

							}

						}
					}

				}
			}
			
			double precision = calculatePrecision(truePositive, falsePositive);

			System.out.println("Precision: " + precision);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
