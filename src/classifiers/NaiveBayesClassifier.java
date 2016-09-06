package classifiers;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.filters.Filter;

public class NaiveBayesClassifier extends TweetClassifier {

	@Override
	protected void buildFilteredClassifier(Filter filter) throws Exception {
		
		NaiveBayes classifier = new NaiveBayes();

		filteredClassifier = new FilteredClassifier();
		filteredClassifier.setClassifier(classifier);
		filteredClassifier.setFilter(filter);
		filteredClassifier.buildClassifier(trainingData);
		
	}

}