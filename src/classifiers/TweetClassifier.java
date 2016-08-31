package classifiers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

abstract public class TweetClassifier {

	protected FilteredClassifier filteredClassifier;

	public Instances prepareTrainingData(String arffFileName) throws Exception {

		DataSource loader = new DataSource(arffFileName);
		Instances loadedData = loader.getDataSet();
		loadedData.setClassIndex(loadedData.numAttributes() - 1);
		return loadedData;

	}

	public void buildClassifier(Instances trainingData) throws Exception {
		StringToWordVector filter = buildTextToWordVectorFilter(trainingData);
		buildFilteredClassifier(trainingData, filter);
	}

	abstract protected void buildFilteredClassifier(Instances trainingData, Filter filter) throws Exception;

	private StringToWordVector buildTextToWordVectorFilter(Instances dataset) throws Exception {
		StringToWordVector textToWordFilter = new StringToWordVector();

		textToWordFilter.setAttributeIndices("first");
		textToWordFilter.setTokenizer(new WordTokenizer());
		textToWordFilter.setWordsToKeep(100000);
		textToWordFilter.setDoNotOperateOnPerClassBasis(true);

		return textToWordFilter;
	}

	protected void printResultsToFile(String file, String results) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.println(results);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	protected String collectResults(Evaluation eval) {
		try {
			return eval.toSummaryString() + '\n' + '\n' + eval.toClassDetailsString()
			 + '\n' + '\n' + eval.toMatrixString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
