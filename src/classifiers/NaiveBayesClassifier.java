package classifiers;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.Filter;

public class NaiveBayesClassifier extends TweetClassifier {

	@Override
	protected void buildFilteredClassifier(Instances trainingData, Filter filter) throws Exception {
		
		NaiveBayes classifier = new NaiveBayes();

		filteredClassifier = new FilteredClassifier();
		filteredClassifier.setClassifier(classifier);
		filteredClassifier.setFilter(filter);
		filteredClassifier.buildClassifier(trainingData);

		Evaluation eval = new Evaluation(trainingData);
		eval.evaluateModel(filteredClassifier, trainingData);
		
		printResultsToFile("BayesResults.txt", collectResults(eval));
		
	}

}