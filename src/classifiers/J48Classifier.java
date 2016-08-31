package classifiers;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.Filter;

public class J48Classifier extends TweetClassifier {

	@Override
	protected void buildFilteredClassifier(Instances trainingData, Filter filter) throws Exception {
		
		J48 classifier = new J48();

		filteredClassifier = new FilteredClassifier();
		filteredClassifier.setClassifier(classifier);
		filteredClassifier.setFilter(filter);
		filteredClassifier.buildClassifier(trainingData);

		Evaluation eval = new Evaluation(trainingData);
		eval.evaluateModel(filteredClassifier, trainingData);
		
		printResultsToFile("J48Results.txt", collectResults(eval));
		
	}

}