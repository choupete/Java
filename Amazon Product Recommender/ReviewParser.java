import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

public class ReviewParser {
	private static Document comparingDoc = new Document("files/query.txt");
	
	public static ArrayList<Review> setList(String filename) throws Exception {
		ArrayList<Review> revs = new ArrayList<Review>();
		ArrayList<String> products = new ArrayList<String>();
		FileReader in = new FileReader(filename);
		BufferedReader readRevs = new BufferedReader(in);
		
		while (readRevs.ready()) {
			String wanted = readRevs.readLine();
			try {
				Review newRev = new Review(wanted);
				if (!products.contains(newRev.getASIN())) { // makes sure only one review per product
					revs.add(new Review(wanted));
					products.add(newRev.getASIN());
				}
			} catch (Exception e) {
			
			}
		}
		return revs;
	}
	public static HashMap<Document, Review> reviewsToDocs(ArrayList<Review> catg){
		ArrayList <Document> docs = new ArrayList<Document>();
		HashMap<Document, Review> docNames = new HashMap<Document, Review>();
		int counter = 0;
		for (Review elm : catg) {
			counter++;
			FileWriter tempWriter;
			try {
				tempWriter = new FileWriter("files/temp.txt", false); // false to overwrite.
				tempWriter.write(elm.getText());
				tempWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}      
			Document d = new Document("files/temp.txt");
			d.setFileName("file #"+ counter);
			elm.setFilename("file #"+ counter);
			docs.add(d);
			docNames.put(d, elm);
		}
		return docNames;
	}
	public static LinkedList<Review> top200(ArrayList<Review> revs){
		HashMap<Document, Review> docMap = reviewsToDocs(revs);
		ArrayList<Document> dox = new ArrayList<Document>();
		for (Document x: docMap.keySet()) {
			dox.add(x);
		}
		dox.add(comparingDoc);

		
		Corpus corp = new Corpus(dox);
		LinkedList<Review> pullHigh = new LinkedList<Review>();
		VectorSpaceModel vecSpace = new VectorSpaceModel(corp);
	
		
		while (pullHigh.size() < 50) {
			double maxCos = Integer.MIN_VALUE;
			Document maxDoc = null;
			for (Document doc : dox) {
				double cos = vecSpace.cosineSimilarity(doc, comparingDoc);
				if (doc.equals(comparingDoc)) continue;
				else if (maxCos < cos) {
					maxCos = cos;
					maxDoc = doc;
				}
			}

			if (maxDoc!= null) {
				pullHigh.add(docMap.get(maxDoc));
				dox.remove(maxDoc);
			}
		}
		return pullHigh;
	}
}
