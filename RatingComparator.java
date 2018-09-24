import java.util.Comparator;

public class RatingComparator implements Comparator<Review> {
	
	@Override
	public int compare(Review r1, Review r2) {
		if (r1.getRating() > r2.getRating()) return -1;
		if (r1.getRating() < r2.getRating()) return 1;
		return 0;
	}
	
}
