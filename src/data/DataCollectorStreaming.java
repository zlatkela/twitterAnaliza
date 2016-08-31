package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class DataCollectorStreaming {
	ArrayList<Status> tweets = new ArrayList<Status>();

	public List<Status> collectData(String consumerKey, String consumerSecret, String aToken, String aTokenSecret, String[] keywords, String lang, double hours) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(aToken)
				.setOAuthAccessTokenSecret(aTokenSecret);

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		StatusListener listener = new StatusListener() {

			@Override
			public void onStatus(Status status) {
				/**
				 * Initializes every time it receives a tweet
				 */
				tweets.add(status);

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}

			@Override
			public void onTrackLimitationNotice(int i) {
			}

			@Override
			public void onScrubGeo(long l, long l2) {
			}

			@Override
			public void onStallWarning(StallWarning stallWarning) {
			}

			@Override
			public void onException(Exception e) {
			}

		};

		FilterQuery fq = new FilterQuery();
		fq.track(keywords);
		fq.language(lang);
		twitterStream.addListener(listener);
		twitterStream.filter(fq);

		try {
			Thread.sleep((long)(hours*60*60*1000));
			System.out.println("Zavrsio sa prikupljanjem podataka");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		twitterStream.cleanUp();
		twitterStream.shutdown();
		return tweets;

	}

	public void writeDataToFile(List<Status> data, String fileName) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			for (int i = 0; i < data.size(); i++) {
				if (!data.get(i).isRetweet()) {
					String text = data.get(i).getText().replace('\n', ' ');
					writer.println(text);
				}
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
