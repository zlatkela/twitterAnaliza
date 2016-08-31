package data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import weka.core.Utils;

public class DataProcessor {

	public ArrayList<String> processData(String file) {
		ArrayList<String> list = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			boolean end = false;
			while (!end) {
				try {
					String temp = br.readLine();
					if (temp == null) {
						end = true;
					} else {
						String sentiment = "positive";
						String[] words = temp.split(" ");
						for (int i = 0; i < words.length; i++) {

							if (words[i].equals(":)") || words[i].equals(":D") || words[i].equals(":-D")
									|| words[i].equals(":-)")) {
								words[i] = "";
								sentiment = "positive";
							}

							if (words[i].equals(":(") || words[i].equals(":-(")) {
								words[i] = "";
								sentiment = "negative";
							}

							if (words[i].startsWith("@")) {
								words[i] = "USERNAME";
							}
							if (words[i].startsWith("http")) {
								words[i] = "URL";
							}
						}

						String line = "";
						for (int i = 0; i < words.length; i++) {
							line += words[i] + " ";
						}
						line = Utils.quote(line);
						line += ", " + sentiment;
						list.add(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File doesn't exist!");
		}
		return list;
	}

	public ArrayList<String> extractData(String file) {
		ArrayList<String> list = new ArrayList<>();
		try {
			BufferedReader br;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-16"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				br = new BufferedReader(new FileReader(file));
			}
			boolean end = false;
			int positive = 0;
			int negative = 0;
			while (!end) {
				try {
					String temp = br.readLine();
					if (temp == null) {
						end = true;
					} else {
						String[] words = temp.split(" ");
						if (positive < 400 || negative < 400) {
							if (words[words.length - 1].equals("positive") && positive < 400) {
								list.add(temp);
								positive++;
							}

							if (words[words.length - 1].equals("negative") && negative < 400) {
								list.add(temp);
								negative++;
							}

						} else {
							end = true;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File doesn't exist!");
		}
		return list;
	}

	public void writeDataToFile(ArrayList<String> data, String file) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.println("@relation processedData");
			writer.println();
			writer.println("@attribute tweet STRING");
			writer.println("@attribute sentiment {positive, negative}");
			writer.println();
			writer.println("@data");
			for (int i = 0; i < data.size(); i++) {
				writer.println(data.get(i));
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Puko");
		}
	}
}
