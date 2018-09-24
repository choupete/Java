import java.util.*;
import java.io.*;


// the program should be run through this class
public class AmazonSupplyFinder {

	public static void main(String[] args) throws IOException {
		
		// get upper and lower price bounds
		double lower = Double.parseDouble(args[0]);
		double upper = Double.parseDouble(args[1]);
		
		try {
		ArrayList <Review> supplies = ReviewParser.setList("files/supplies.txt");
		LinkedList<Review> top = ReviewParser.top200(supplies);
		
		Collections.sort(top, new RatingComparator());
		
		List<Product> fourPlus = new LinkedList<Product>();
		
		for (Review r : top) {
			if (r.getRating() > 3.99) {
				Product added = new Product(r.getASIN());
				if (!added.getProductASIN().contains("invalid")) {
					fourPlus.add(added);
				}
			} else {
				break;
			}
		}
		
		Collections.sort(fourPlus);
		
		int counter = 1;
		
		System.out.println("Listing college school supply recommendations within price range of $"
		+ lower + " to $" + upper + "...");
		
		for (Product p : fourPlus) {
			if (p.getProductPrice() >= lower) {
				if (p.getProductPrice() > upper) break;
				System.out.println(counter + ") " + "Product URL: https://www.amazon.com/dp/" 
				+ p.getProductASIN());
				System.out.println("Product Price: $" + p.getProductPrice());
				
				System.out.println();
				
				counter++;
			}
		}
		
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
