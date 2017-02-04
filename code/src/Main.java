import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


public class Main 
{
	private static Country countries = new Country();
	
	// Variable to keep track of the documents
	private static Set<MyDocument> articles;
	
	//Initializes and reads all the articles and creates their documents
	private static void articlesInitializer()
	{
		Scanner s;
		try {
			s = new Scanner(new FileInputStream("articles.txt"));
			articles = new HashSet<MyDocument>();
			while (s.hasNextLine())
			{
			String titleLine = s.nextLine();
			String title = titleLine.split(":", 2)[1];
			String dateLine = s.nextLine();
			String date = dateLine.split(":", 2)[1];
			String contentLine = s.nextLine();
			String content = contentLine.split(":", 2)[1];
			MyDocument a = new MyDocument(title, date, content);
			articles.add(a);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		
		articlesInitializer();
		twitter.main(null);
		
		// Keeps track of the link between the country and its set of tweets
		Map<String, MyDocument> countryTweets = new TreeMap<String, MyDocument>();
		
		/**
		 * For each country and their cities' geocodes get the tweets and accumulate them into a single 
		 * document. The accumulation of them into a single document is explained in the readme.
		 */ 
		for (String country : countries.getCountries())
			{
				String accumulator = "";
				Set<double[]> cities = countries.getCities(country);
				for (double[] coordinates : cities)
				{
					
					String basicUrl = "https://api.twitter.com/1.1/search/"
							+ "tweets.json?"
							+ "geocode="+coordinates[0]+"%2C"+coordinates[1]+"%2C100mi"
							+ "&result-type=recent"
							+ "&q=football%20OR%20soccer"
							+ "&count=100"
							+ "&lang=en&";
					accumulator = accumulator + twitter.fetchTimelineTweet(basicUrl).trim();
				}
				// Create the tweet document for the country
				countryTweets.put(country, new MyDocument(country,"2015-05-04", accumulator));
			}
		
		
		// Keeps track of the the average Cosine Similarities of the contries
		Map<String, Double> averages = new TreeMap<String, Double>();
		
		// Initializes the value of each country's average to 0
		for(String countryName : countries.getCountries())
		{
			averages.put(countryName, (double) 0);
		}
		
		// For each article compute the Similarities between the article and each country's tweet document
		for(MyDocument a : articles)
		{
			ArrayList<MyDocument> l = new ArrayList<MyDocument>();
			for(String countryName : countryTweets.keySet())
				l.add(countryTweets.get(countryName));
			l.add(a);
			Corpus c = new Corpus(l);
			VectorSpaceModel v = new VectorSpaceModel(c);
			for(String countryName : countryTweets.keySet())
			{
				averages.put(countryName, averages.get(countryName) + v.cosineSimilarity(a, countryTweets.get(countryName)));
			}
		
		}
		
		// Average and printout each countries average similarity
		for(String countryName : averages.keySet())
		{
			System.out.println(countryName + ": " + (averages.get(countryName).doubleValue() / 50) );
		}
	}
	
	
		
	
}
