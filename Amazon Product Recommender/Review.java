public class Review {
	private String text;
	private double rating;
	private String asin;
	private String filename;
	public Review (String whole) {
		int textIndexStart = whole.indexOf("reviewText") + "reviewText".length() + 3;
		int textIndexEnd = whole.indexOf("overall") - 3;
		int ratingIndexStart = whole.indexOf("overall") + "overall".length() + 3;
		int ratingIndexEnd = whole.indexOf("summary") - 3;
		int asinIndexStart = whole.indexOf("asin") + "asin".length() + 4;
		int asinIndexEnd = whole.indexOf("reviewerName") - 4;
		rating = Double.parseDouble(whole.substring(ratingIndexStart, ratingIndexEnd));
		text = whole.substring(textIndexStart, textIndexEnd);
		asin = whole.substring(asinIndexStart, asinIndexEnd);
	}
		
	// getters
	public String getText() {
		return text;
	}
	public double getRating() {
		return rating;
	}
	
	public String getASIN() {
		return asin;
	}
	
	//setter and getter to keep track of docs 
	public void setFilename(String fname) {
		filename = fname;
	}
	
	public String getFilename() {
		return filename;
	}
	public int compareTo(Review other) {
		if (getRating() < other.getRating()) return -1;
		if (getRating() > other.getRating()) return 1;
		return 0;
	}
	
}
