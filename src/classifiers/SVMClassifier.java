package classifiers;

import weka.classifiers.functions.SMO;
import weka.classifiers.meta.FilteredClassifier;
import weka.filters.Filter;

public class SVMClassifier extends TweetClassifier {

	@Override
	protected void buildFilteredClassifier(Filter filter) throws Exception {
		
		SMO classifier = new SMO();

		filteredClassifier = new FilteredClassifier();
		filteredClassifier.setClassifier(classifier);
		filteredClassifier.setFilter(filter);
		filteredClassifier.buildClassifier(trainingData);
		
	}

}