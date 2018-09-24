import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Product implements Comparable<Product> {
	
	// fields
	private String asin;
	private double price;
	
	public Product(String asin) throws IOException {
		this.asin = asin;
		
		// get product page document
		try {
		final Document document = Jsoup.connect("https://www.amazon.com/dp/" + asin).get();
		
		// check if product is unavailable
				String unavailable = "";
				for (Element row : document.select(".a-size-medium.a-color-price")) {
					
					unavailable = row.select("*").text();
				}
				
				// set price to max if unavailable
				if (unavailable.contains("unavailable")) {
					price = Double.MAX_VALUE;
				} else {
					// check if product has sold-by-Amazon price
					String amazon = "";
					for (Element row : document.select("#priceblock_ourprice")) {
						
						amazon = row.select("*").text();
					}
					if (amazon.contains("$")) {
						String[] split = amazon.split("\\$");
						String dollars = split[1].trim();
						dollars = dollars.replace(" ", ".");
						price = Double.parseDouble(dollars);
					} else {
						// check if product has third-party price
						String thirdP = "";
						for (Element row : document.select(".a-color-price.price3P")) {
							
							thirdP = row.select("*").text();
						}
						if (thirdP.contains("$")) {
							String[] split = thirdP.split("\\$");
							String dollars = split[1].trim();
							price = Double.parseDouble(dollars);
						} else {
							// check if price is in alternate format
							String alternate = "";
							for (Element row : document.select(".a-size-medium.a-color-price.offer-price.a-text-normal")) {
								
								alternate = row.select("*").text();
							}
							if (alternate.contains("$")) {
								String[] split = alternate.split("\\$");
								String dollars = split[1].trim();
								price = Double.parseDouble(dollars);
							} else {
								// if no pattern matches, then set price to max
								price = Double.MAX_VALUE;
							}
						}
					}
				}
			} catch (Exception e) {
				this.asin = "invalid";
				this.price = Double.MAX_VALUE;
			}
		
		
		
		
	}
	
	public String getProductASIN() {
		return asin;
	}
	
	public double getProductPrice() {
		return price;
	}
	
	// override compareTo method
	@Override
	public int compareTo(Product other) {
		if (price < other.getProductPrice()) return -1;
		if (price > other.getProductPrice()) return 1;
		return 0;
	}
	
	// override equals method
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Product other = (Product) obj; // we know this won't fail
		if (!asin.equals(other.getProductASIN())) { // each organization has unique name
			return false;
		}
		return true;
	}
}
