package classifiers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

abstract public class TweetClassifier {
	
	protected Instances allData;
	protected Instances trainingData;
	protected Instances testData;
	protected Instances validationData;

	protected FilteredClassifier filteredClassifier;

	public void prepareDataSets(String arffFileName) throws Exception {

		DataSource loader = new DataSource(arffFileName);
		allData = loader.getDataSet();
		
		buildDatasets(allData);
		
		trainingData.setClassIndex(trainingData.numAttributes() - 1);
		testData.setClassIndex(testData.numAttributes() - 1);
		validationData.setClassIndex(validationData.numAttributes() - 1);

	}

	public void buildClassifier() throws Exception {
		StringToWordVector filter = buildTextToWordVectorFilter(trainingData);
		buildFilteredClassifier(filter);
	}
	
	public void execute(String resultsFile, String validationFile) throws Exception {
		buildClassifier();
		validateModel(validationFile);
		evaulateModel(resultsFile);
	}

	private void validateModel(String fileName) throws Exception {
		Evaluation eval = new Evaluation(validationData);
		eval.crossValidateModel(filteredClassifier, validationData, 10, new Random(1));
		
		printResultsToFile(fileName, collectResults(eval));
		
	}

	abstract protected void buildFilteredClassifier(Filter filter) throws Exception;

	private StringToWordVector buildTextToWordVectorFilter(Instances dataset) throws Exception {
		StringToWordVector textToWordFilter = new StringToWordVector();

		textToWordFilter.setAttributeIndices("first");
		textToWordFilter.setTokenizer(new WordTokenizer());
		textToWordFilter.setWordsToKeep(100000);
		textToWordFilter.setDoNotOperateOnPerClassBasis(true);

		return textToWordFilter;
	}
	
	private void evaulateModel(String fileName) throws Exception {
		Evaluation eval = new Evaluation(testData);
		eval.evaluateModel(filteredClassifier, testData);
		
		printResultsToFile(fileName, collectResults(eval));
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
	
	private void buildDatasets(Instances instances) {
		instances.randomize(new Random(0));
		int trainSize = (int) Math.round(instances.numInstances() * 60 / 100);
		int testSize = (int) Math.round(instances.numInstances() * 20 / 100);
		int validationSize = instances.numInstances() - trainSize - testSize;
		trainingData = new Instances(instances, 0, trainSize);
		testData = new Instances(instances, trainSize, testSize);
		validationData = new Instances(instances, testSize, validationSize);
	}

}
