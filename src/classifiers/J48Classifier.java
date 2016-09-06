package classifiers;

import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.filters.Filter;

public class J48Classifier extends TweetClassifier {

	@Override
	protected void buildFilteredClassifier(Filter filter) throws Exception {
		J48 classifier = new J48();
		
		filteredClassifier = new FilteredClassifier();
		filteredClassifier.setClassifier(classifier);
		filteredClassifier.setFilter(filter);
		filteredClassifier.buildClassifier(trainingData);
		
	}

}