

import java.util.HashMap;
import java.util.Set;

/**
 * This class implements the Vector-Space model.
 * It takes a corpus and creates the tf-idf vectors for each MyDocument.
 * @author swapneel
 *
 */
public class VectorSpaceModel {
	
	/**
	 * The corpus of documents.
	 */
	private Corpus corpus;
	
	/**
	 * The tf-idf weight vectors.
	 * The hashmap maps a MyDocument to another hashmap.
	 * The second hashmap maps a term to its tf-idf weight for this MyDocument.
	 */
	private HashMap<MyDocument, HashMap<String, Double>> tfIdfWeights;
	
	/**
	 * The constructor.
	 * It will take a corpus of documents.
	 * Using the corpus, it will generate tf-idf vectors for each MyDocument.
	 * @param corpus the corpus of documents
	 */
	public VectorSpaceModel(Corpus corpus) {
		this.corpus = corpus;
		tfIdfWeights = new HashMap<MyDocument, HashMap<String, Double>>();
		
		createTfIdfWeights();
	}

	/**
	 * This creates the tf-idf vectors.
	 */
	private void createTfIdfWeights() {
		//.out.println("Creating the tf-idf weight vectors");
		Set<String> terms = corpus.getInvertedIndex().keySet();
		
		for (MyDocument d : corpus.getDocuments()) {
			HashMap<String, Double> weights = new HashMap<String, Double>();
			
			for (String term : terms) {
				double tf = d.getTermFrequency(term);
				double idf = corpus.getInverseDocumentFrequency(term);
				
				double weight = tf * idf;
				
				weights.put(term, weight);
			}
			tfIdfWeights.put(d, weights);
		}
	}
	
	/**
	 * This method will return the magnitude of a vector.
	 * @param MyDocument the MyDocument whose magnitude is calculated.
	 * @return the magnitude
	 */
	private double getMagnitude(MyDocument MyDocument) {
		double magnitude = 0;
		HashMap<String, Double> weights = tfIdfWeights.get(MyDocument);
		
		for (double weight : weights.values()) {
			magnitude += weight * weight;
		}
		
		return Math.sqrt(magnitude);
	}
	
	/**
	 * This will take two documents and return the dot product.
	 * @param d1 MyDocument 1
	 * @param d2 MyDocument 2
	 * @return the dot product of the documents
	 */
	public double getDotProduct(MyDocument d1, MyDocument d2) {
		double product = 0;
		HashMap<String, Double> weights1 = tfIdfWeights.get(d1);
		HashMap<String, Double> weights2 = tfIdfWeights.get(d2);
		for (String term : weights1.keySet()) {
			product += weights1.get(term) * weights2.get(term);
		}
		
		return product;
	}
	
	/**
	 * This will return the cosine similarity of two documents.
	 * This will range from 0 (not similar) to 1 (very similar).
	 * @param d1 MyDocument 1
	 * @param d2 MyDocument 2
	 * @return the cosine similarity
	 */
	public double cosineSimilarity(MyDocument d1, MyDocument d2) {
		return 1000 * (getDotProduct(d1, d2) / (getMagnitude(d1) * getMagnitude(d2)));
	}
}