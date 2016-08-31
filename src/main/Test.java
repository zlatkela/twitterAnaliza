package main;

import java.util.ArrayList;
import java.util.List;

import classifiers.J48Classifier;
import classifiers.NaiveBayesClassifier;
import classifiers.SVMClassifier;
import classifiers.TweetClassifier;
import data.DataCollectorStreaming;
import data.DataProcessor;
import twitter4j.Status;

public class Test {

	public static void main(String[] args) {
		DataCollectorStreaming dc = new DataCollectorStreaming();
		List<Status> data = dc.collectData("MD2MiNxQf5SMMa9P7ScM29jjN",
				"IIP7OGruQ0Yb1brFM3KQ4TLPO2Igji6MexvI3ZheWiR3buTzI3",
				"852506131-S2GNLWG2nB5pLchneMLUre9HwKaKJEgBoSBnrDdG",
				"3SOqR2WTyPMAg4kvS8641ivYGsJhe6HVT84RR8718Yw9P", 
				new String[] { ":)", ":(", ":-)", ":-(", ":D", ":-D" }, "sr", 0.2);
		
		dc.writeDataToFile(data, "rawData.txt");

		 DataProcessor dp = new DataProcessor();
		 ArrayList<String> trainingData = dp.processData("rawData.txt");
		 dp.writeDataToFile(trainingData, "processedData.arff");
		 
		 ArrayList<String> allData = dp.extractData("AllData.txt");
		 dp.writeDataToFile(allData, "selectedData.arff");
		 
		 NaiveBayesClassifier nbc = new NaiveBayesClassifier();
		 J48Classifier j48 = new J48Classifier();
		 SVMClassifier svm	= new SVMClassifier();
		 
		 try {
			nbc.buildClassifier(nbc.prepareTrainingData("selectedData.arff"));
			j48.buildClassifier(j48.prepareTrainingData("selectedData.arff"));
			svm.buildClassifier(svm.prepareTrainingData("selectedData.arff"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
