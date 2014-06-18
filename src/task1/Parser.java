package task1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
	
	public static void parseTrainXML(String folderName, ArrayList<File> fileList, Unigram unigramTok,
			BGram bgramTok, UnigramPoS unigramPoSList, BGramPoS bGramPoSList, HashMap<String, Integer> posList) {

		try {
			for (File file : fileList) {

				File fXmlFile = new File(folderName + "/" + file.getName());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				String previousTok = "";
				String currentTok = "";
				String currentPoS = "";
				String previousPoS = "";

	
				NodeList nodes = doc.getElementsByTagName("sentence");
	
				for (int i = 0; i < nodes.getLength(); i++) {	
					Node node = nodes.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) node;
						NodeList tokList = element.getElementsByTagName("tok");
	
						for (int j = 0; j < tokList.getLength(); j++) {
							Node tokNode = tokList.item(j);
	
							if (tokNode.getNodeType() == Node.ELEMENT_NODE) {
								Element tokElement = (Element) tokNode;
								currentTok = tokElement.getTextContent();
								currentPoS = tokElement.getAttribute("cat");
								
								if (j == 0) {
									
									String startString = "<S>";
									unigramTok.add(startString);
									previousPoS = "<S>";
									previousTok = "<S>";
									unigramPoSList.add(startString, startString);
									if (posList.containsKey(startString)){
										posList.put(startString, posList.get(startString)+1);
									}else
										posList.put(startString, 1);
									

								}
								if (posList.containsKey(currentPoS))
									posList.put(currentPoS, posList.get(currentPoS)+1);
								else
									posList.put(currentPoS, 1);
								unigramTok.add(currentTok);
								bgramTok.add(previousTok, currentTok);
								
								unigramPoSList.add(currentTok, currentPoS);
								bGramPoSList.add(previousPoS, currentPoS);
								
								previousTok = currentTok;
								previousPoS = currentPoS;
							}
						}
					}
				}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
