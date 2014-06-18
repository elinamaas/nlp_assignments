package task1;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Split {
	
	
	
	public static int numberOfFiles (String folderName){
		File folder = new File(folderName);
		int size = 0;
		for (File document : folder.listFiles()){
			size++;
		}
		return size;
	}
	
	
	public static Set<Integer> randomizedTrain (int max, int min){
		Set<Integer> randomInt = new HashSet<Integer>();
		int maxSize = (int) (max * 0.9);
		while (randomInt.size()<= maxSize){
			Random rand = new Random();
			int randomNum = rand.nextInt((max - min) + 1) + min;
			randomInt.add(randomNum);
		}
		return randomInt;
	}
	
	public static Set<Integer> randomizedTest (Set<Integer> randomizedTrain, int size){
		Set<Integer> randomIntTest = new HashSet<Integer>();
		for (int i=0; i<=size; i++){
			if (!randomizedTrain.contains(i)){
				randomIntTest.add(i);
			}
		}
		return randomIntTest;
	}
	
	public static ArrayList <File> splitData (String folderName, Set<Integer> listofFiles){
		File folder = new File(folderName);
		ArrayList<File> dataFiles = new ArrayList<>();
		for (int i : listofFiles){
			String [] xmlChecher = folder.listFiles()[i].getName().split("\\.");
			if (xmlChecher.length >1 && xmlChecher[1].equals("xml")){
				dataFiles.add(folder.listFiles()[i]);
			}
		}
		
		return dataFiles;
	}
		
}
