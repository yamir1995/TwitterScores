

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class represents a corpus of documents.
 * It will create an inverted index for these documents.
 * @author swapneel
 *
 */
public class Corpus {
	
	/**
	 * An arraylist of all documents in the corpus.
	 */
	private ArrayList<MyDocument> documents;
	
	/**
	 * The inverted index. 
	 * It will map a term to a set of documents that contain that term.
	 */
	private HashMap<String, Set<MyDocument>> invertedIndex;
	
	/**
	 * The constructor - it takes in an arraylist of documents.
	 * It will generate the inverted index based on the documents.
	 * @param result the list of documents
	 */
	public Corpus(ArrayList<MyDocument> result) {
		this.documents = result;
		invertedIndex = new HashMap<String, Set<MyDocument>>();
		
		createInvertedIndex();
	}
	
	
	
	/**
	 * This method will create an inverted index.
	 */
	private void createInvertedIndex() {
		for (MyDocument MyDocument : documents) {
			Set<String> terms = MyDocument.getTermList();
			
			for (String term : terms) {
				if (invertedIndex.containsKey(term)) {
					Set<MyDocument> list = invertedIndex.get(term);
					list.add(MyDocument);
				} else {
					Set<MyDocument> list = new TreeSet<MyDocument>();
					list.add(MyDocument);
					invertedIndex.put(term, list);
				}
			}
		}
	}
	
	/**
	 * This method returns the idf for a given term.
	 * @param term a term in a MyDocument
	 * @return the idf for the term
	 */
	public double getInverseDocumentFrequency(String term) {
		if (invertedIndex.containsKey(term)) {
			double size = documents.size();
			Set<MyDocument> list = invertedIndex.get(term);
			double documentFrequency = list.size();
			
			return Math.log10(size / documentFrequency);
		} else {
			return 0;
		}
	}

	/**
	 * @return the documents
	 */
	public ArrayList<MyDocument> getDocuments() {
		return documents;
	}

	/**
	 * @return the invertedIndex
	 */
	public HashMap<String, Set<MyDocument>> getInvertedIndex() {
		return invertedIndex;
	}
}