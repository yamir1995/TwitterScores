

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

 


/**
 * This class represents one MyDocument.
 * It will keep track of the term frequencies.
 * @author swapneel
 * 
 * EXTENDED and modified to represent articles and a tweet
 */
public class MyDocument implements Comparable<MyDocument> {
	
	/**
	 * A hashmap for term frequencies.
	 * Maps a term to the number of times this terms appears in this MyDocument. 
	 */
	private HashMap<String, Integer> termFrequency;
	
	/**
	 * The name of the file to read.
	 */
	private String title;
	
	
	private String source;

	private String date;
	
	/**
	 * The constructor.
	 * It takes in the name of a file to read.
	 * It will read the file and pre-process it.
	 * @param filename the name of the file
	 */
	public MyDocument(String title, String date, String content) 
	{
		this.title = title;
		this.date = date;
		this.source = content;
		termFrequency = new HashMap<String, Integer>();
		
		readStringAndPreProcess();
	}
	
	
	/**
	 * This method will read in the string content and do some pre-processing.
	 * The following things are done in pre-processing:
	 * Every word is converted to lower case.
	 * Every character that is not a letter or a digit is removed.
	 * We don't do any stemming.
	 * Once the pre-processing is done, we create and update the 
	 */
	public void readStringAndPreProcess() {
		try {
			Scanner in = new Scanner(source);
			while (in.hasNext()) {
				String nextWord = in.next();
				
				String filteredWord = nextWord.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
				
				if (!(filteredWord.equalsIgnoreCase(""))) {
					if (termFrequency.containsKey(filteredWord)) {
						int oldCount = termFrequency.get(filteredWord);
						termFrequency.put(filteredWord, ++oldCount);
					} else {
						termFrequency.put(filteredWord, 1);
					}
				}
			}
			in.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will return the term frequency for a given word.
	 * If this MyDocument doesn't contain the word, it will return 0
	 * @param word The word to look for
	 * @return the term frequency for this word in this MyDocument
	 */
	public double getTermFrequency(String word) {
		if (termFrequency.containsKey(word)) {
			return termFrequency.get(word);
		} else {
			return 0;
		}
	}
	
	/**
	 * This method will return a set of all the terms which occur in this MyDocument.
	 * @return a set of all terms in this MyDocument
	 */
	public Set<String> getTermList() {
		return termFrequency.keySet();
	}

	@Override
	/**
	 * The overriden method from the Comparable interface.
	 */
	public int compareTo(MyDocument other) {
		return title.compareTo(other.getTitle());
	}

	/**
	 * @return the filename
	 */
	public String getTitle() 
	{
		return title;
	}
	
	public String getContent() 
	{
		return source;
	}
	
	public String getDate() 
	{
		return date;
	}
	
	/**
	 * This method is used for pretty-printing a MyDocument object.
	 * @return the filename
	 */
	public String toString() 
	{
		return source;
	}
}