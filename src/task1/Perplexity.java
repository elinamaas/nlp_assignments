package task1;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.Maps;

public class Perplexity {
	
	public static void parseTestData (String folderName, ArrayList<File> fileList, Unigram unigramTokList,
			BGram bgramTokList){
		try {
			double v = unigramTokList.getUnigramSize();
			ArrayList <Double> perplexitySentence = new ArrayList<Double>();
			
			for (File file : fileList) {

				File fXmlFile = new File(folderName + "/" + file.getName());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);

				doc.getDocumentElement().normalize();
				NodeList sentenseList = doc.getElementsByTagName("sentence");

				String previousElement = "<S> ";
				String currentElement ="";
				for (int i = 0; i < sentenseList.getLength(); i++) {
					int sentenceSize = 1;
					double product=1;
					Node nNode = sentenseList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) nNode;
						NodeList tList = element.getElementsByTagName("tok");
						for (int j = 0; j< tList.getLength()-1; j++){
							Node tokNode = tList.item(j);
							if (tokNode.getNodeType() == Node.ELEMENT_NODE){
								Element tokElement = (Element) tokNode;
								if (j==0)		
									previousElement = "<S>";

								currentElement =tokElement.getTextContent();
								String bgram = previousElement + " " + currentElement;
								
								double numerator = bgramTokList.getFreq(bgram) +1 ;
								double denominator = unigramTokList.getFreq(previousElement) + v;
								double temp = numerator/denominator;
								product *= temp;
								previousElement = currentElement;
								sentenceSize++;
								
							}							
						}
					}
					double perplexity = Math.pow(1.0/product, 1.0/sentenceSize);
					perplexitySentence.add(perplexity);

				}
}
			double perplexity=calculatePerplexity(perplexitySentence);

			System.out.println("Perplexity: " + perplexity);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double calculatePerplexity (ArrayList <Double> perplexitySentence){
		double sum = 0;
		
		    for (double value : perplexitySentence) {
		    	if (!Double.isInfinite(value) && ! Double.isNaN(value))
			    	sum+= value;
		    }

		double perplexity = sum/perplexitySentence.size();
		return perplexity;
		  
	}

}
